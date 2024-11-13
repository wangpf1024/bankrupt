/**
 * @filename:WorkFlowModelInfoController 2024年6月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.model.entity.WorkFlowModelCategory;
import cn.bankrupt.workflow.model.entity.WorkFlowModelInfo;
import cn.bankrupt.workflow.model.enums.DeploymentStatus;
import cn.bankrupt.workflow.model.service.WorkFlowModelCategoryService;
import cn.bankrupt.workflow.model.service.WorkFlowModelInfoService;
import cn.bankrupt.workflow.web.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程信息API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年6月27日
 *
 */
@Api(description = "流程信息",value="流程信息" )
@RestController
@RequestMapping("/process-model-server/workFlowModelInfo")
public class WorkFlowModelInfoController extends AbstractController<WorkFlowModelInfoService,WorkFlowModelInfo>{

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WorkFlowModelCategoryService workFlowModelCategoryService;

	/**
	 * @explain 运行实例  <swagger POST请求>
	 * @param   @RequestBody：ModelingEntityDef
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月10日
	 */
	@GetMapping("/deployed/{modelKey}")
	@ApiOperation(value = "执行流程实例查询", notes = "作者：王鹏飞")
	public ResultBean<WorkFlowModelInfo> deployedModelInfo(@PathVariable("modelKey")String modelKey) {

		List<WorkFlowModelInfo> list = baseService.list(Wrappers.<WorkFlowModelInfo>lambdaQuery()
				.eq(WorkFlowModelInfo::getModelKey, modelKey)
				.eq(WorkFlowModelInfo::getStatus,DeploymentStatus.DEPLOYED.getCode())
				.orderByDesc(WorkFlowModelInfo::getOrderNum));

		if(CollectionUtils.isEmpty(list)){
			return ResultBean.ofError("无可执行流程实例");
		}

		if(list.size() > 1){
			return ResultBean.ofError("可执行流程实例大于一个,请检查系统配置");
		}

		WorkFlowModelInfo modelInfo = list.get(0);

		WorkFlowModelCategory category = workFlowModelCategoryService.getById(modelInfo.getCategoryId());

		if(!modelInfo.getModelKey().startsWith(category.getCode())){
			return ResultBean.ofError("执行流程与业务功能不匹配");
		}

		return ResultBean.ofSuccess(modelInfo);
	}

}