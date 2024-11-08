/**
 * @filename:WorkFlowModelCategoryController 2024年10月21日
 * @project workflow  1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.controller;

import cn.bankrupt.workflow.model.domain.ModelInfoVo;
import cn.bankrupt.workflow.model.entity.WorkFlowFormModelInfo;
import cn.bankrupt.workflow.model.entity.WorkFlowModel;
import cn.bankrupt.workflow.model.entity.WorkFlowModelCategory;
import cn.bankrupt.workflow.model.entity.WorkFlowModelInfo;
import cn.bankrupt.workflow.model.service.WorkFlowFormModelInfoService;
import cn.bankrupt.workflow.model.service.WorkFlowModelCategoryService;
import cn.bankrupt.workflow.model.service.WorkFlowModelInfoService;
import cn.bankrupt.workflow.model.service.WorkFlowModelService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.enums.BaseExceptionEnum;
import cn.bankrupt.workflow.model.entity.*;
import cn.bankrupt.workflow.model.enums.DeploymentStatus;
import cn.bankrupt.workflow.model.enums.ModelType;
import cn.bankrupt.workflow.model.service.*;
import cn.bankrupt.workflow.web.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程模型API接口层</P>
 * @version: 1.0.0
 * @author: 王鹏飞
 * @time    2024年10月21日
 *
 */
@Api(description = "流程分类",value="流程分类" )
@RestController
@RequestMapping("/process-model-server/workFlowModelCategory")
public class WorkFlowModelCategoryController extends AbstractController<WorkFlowModelCategoryService, WorkFlowModelCategory>{

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WorkFlowModelService workFlowModelService;


	@Autowired
	WorkFlowModelInfoService workFlowModelInfoService;

	@Autowired
	WorkFlowFormModelInfoService workFlowFormModelInfoService;


	/**
	 * @explain 复制模型信息  <swagger POST请求>
	 * @param   @RequestBody：ModelingEntityDef
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月10日
	 */
	@PostMapping("/copy/modelInfo")
	@ApiOperation(value = "复制模型信息", notes = "作者：王鹏飞")
	public ResultBean<String> copyModelInfo(@RequestBody ModelInfoVo modelInfoVo) {

		WorkFlowModelCategory category = baseService.getById(modelInfoVo.getCategoryId());

		if (category ==  null) {
			return ResultBean.ofError(BaseExceptionEnum.EC404.getCode(),"流程分类");
		}

		WorkFlowModelInfo modelInfo = workFlowModelInfoService.getById(modelInfoVo.getModelInfoId());

		if (modelInfo ==  null) {
			return ResultBean.ofError(BaseExceptionEnum.EC404.getCode(),"流程信息");
		}

		WorkFlowModel model = workFlowModelService.getById(modelInfo.getModelId());

		if (model ==  null) {
			return ResultBean.ofError(BaseExceptionEnum.EC404.getCode(),"流程模型");
		}

		//复制新的表单模型关联信息
		List<WorkFlowFormModelInfo> formModelInfos = workFlowFormModelInfoService.list(Wrappers.<WorkFlowFormModelInfo>lambdaQuery()
				.eq(WorkFlowFormModelInfo::getModelId, modelInfo.getModelId()).orderByDesc(WorkFlowFormModelInfo::getVersion));

		if (CollectionUtils.isEmpty(formModelInfos)) {
			return ResultBean.ofError(BaseExceptionEnum.EC404.getCode(),"表单模型");
		}

		WorkFlowModel copy = new WorkFlowModel();
		BeanUtils.copyProperties(model,copy);
		String modelKey = category.getCode()+"_"+copy.getModelKey();
		copy.setModelKey(modelKey);

		//设置版本号
		WorkFlowModel newset = workFlowModelService.getOne(Wrappers.<WorkFlowModel>lambdaQuery()
				.eq(WorkFlowModel::getModelKey, modelKey).orderByDesc(WorkFlowModel::getVersion),false);
		copy.setId(null);
		copy.setVersion(1);
		if(newset !=null){
			copy.setVersion(newset.getVersion() + 1);
		}
		workFlowModelService.save(copy);

		WorkFlowModelInfo info = new WorkFlowModelInfo();
		info.setModelId(copy.getId().toString());
		info.setCategoryId(category.getId());
		info.setName(modelInfoVo.getName() == null? modelInfo.getName():modelInfoVo.getName());
		info.setModelType(ModelType.OUTER.getCode());
		info.setTenantId(modelInfoVo.getTenantId());
		info.setModelKey(modelKey);
		info.setStatus(DeploymentStatus.INIT.getCode());

		workFlowModelInfoService.save(info);

		formModelInfos.stream().forEach(formModelInfo ->{
			WorkFlowFormModelInfo newInfo = new WorkFlowFormModelInfo();
			BeanUtils.copyProperties(formModelInfo,newInfo);
			newInfo.setModelId(info.getModelId());
			newInfo.setVersion(1);
			newInfo.setCategoryId(info.getCategoryId());
			newInfo.setCategoryName(category.getCategoryName());
			newInfo.setId(null);
			workFlowFormModelInfoService.save(newInfo);
		});

		return ResultBean.ofSuccess(info.getId().toString());
	}

}