/**
 * @filename:WorkFlowModelCategoryController 2024年10月21日
 * @project workflow  1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.model.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pansome.workflow.ResultBean;
import com.pansome.workflow.enums.BaseExceptionEnum;
import com.pansome.workflow.model.domain.ModelInfoVo;
import com.pansome.workflow.model.entity.*;
import com.pansome.workflow.model.enums.DeploymentStatus;
import com.pansome.workflow.model.enums.ModelType;
import com.pansome.workflow.model.service.*;
import com.pansome.workflow.web.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
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
public class WorkFlowModelCategoryController extends AbstractController<WorkFlowModelCategoryService,WorkFlowModelCategory>{

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
		String no = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
		String newName = category.getCategoryName() +":"+model.getModelName()+":"+no;
		copy.setModelName(newName);

		// 创建 SAXBuilder 实例
		SAXBuilder saxBuilder = new SAXBuilder();

		// 解析 XML 字符串
        try {

			Document document = saxBuilder.build(new StringReader(copy.getModelXml()));
			Element rootElement = document.getRootElement();

			// 查找 <bpmn:process> 元素
			Element process = rootElement.getChild("process", rootElement.getNamespace("bpmn"));
			if (process != null) {
				String value = modelKey+"_"+no;
				copy.setFileName(value);
				process.setAttribute("id",value);
				process.setAttribute("name",newName);
			}

			// Convert the modified document back to a string
			StringWriter stringWriter = new StringWriter();
			XMLOutputter xmlOutputter = new XMLOutputter();
			xmlOutputter.setFormat(Format.getPrettyFormat());

			xmlOutputter.output(document, stringWriter);
			copy.setModelXml(stringWriter.toString());

        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

		workFlowModelService.save(copy);

		WorkFlowModelInfo info = new WorkFlowModelInfo();
		info.setModelId(copy.getId().toString());
		info.setCategoryId(category.getId());
		info.setName(newName);
		info.setModelType(ModelType.OUTER.getCode());
		info.setTenantId(modelInfoVo.getTenantId());
		info.setModelKey(modelKey);
		info.setStatus(DeploymentStatus.INIT.getCode());
		info.setOrderNum(copy.getVersion());
		info.setApplyCompanyId(modelInfoVo.getApplyCompanyId());
		info.setApplyProjectId(modelInfoVo.getApplyProjectId());
		info.setCreateBy(modelInfoVo.getCreateBy());
		info.setCreateTime(new Date());

		workFlowModelInfoService.save(info);

		//特殊配置的分类表单
		List<WorkFlowFormModelInfo> formModelInfos = workFlowFormModelInfoService.list(Wrappers.<WorkFlowFormModelInfo>lambdaQuery()
				.eq(WorkFlowFormModelInfo::getModelId, modelInfo.getModelId())
				.eq(WorkFlowFormModelInfo::getCategoryId, category.getId())
				.eq(WorkFlowFormModelInfo::getFormModelType, 1)
				.orderByDesc(WorkFlowFormModelInfo::getVersion));

		//默认配置表单
		if(CollectionUtils.isEmpty(formModelInfos)){
			formModelInfos = workFlowFormModelInfoService.list(Wrappers.<WorkFlowFormModelInfo>lambdaQuery()
					.eq(WorkFlowFormModelInfo::getModelId, modelInfo.getModelId())
					.isNull(WorkFlowFormModelInfo::getCategoryId)
					.eq(WorkFlowFormModelInfo::getFormModelType, 1)
					.orderByDesc(WorkFlowFormModelInfo::getVersion));
		}

		if(!CollectionUtils.isEmpty(formModelInfos)){
			formModelInfos.stream().forEach(formModelInfo ->{
				WorkFlowFormModelInfo newInfo = new WorkFlowFormModelInfo();
				BeanUtils.copyProperties(formModelInfo,newInfo);
				newInfo.setModelId(info.getModelId());
				newInfo.setVersion(1);
				newInfo.setCategoryId(info.getCategoryId());
				newInfo.setCategoryName(category.getCategoryName());
				newInfo.setId(null);
				newInfo.setFormModelType(0);
				newInfo.setCreateBy(modelInfoVo.getCreateBy());
				newInfo.setCreateTime(info.getCreateTime());
				workFlowFormModelInfoService.save(newInfo);
			});
		}


		return ResultBean.ofSuccess(info.getId().toString());
	}

}