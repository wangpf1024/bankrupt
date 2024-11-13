/**
 * @filename:WorkFlowModelCategoryController 2024年10月21日
 * @project workflow  1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.controller;

import cn.bankrupt.workflow.model.domain.ModelInfoVo;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.enums.BaseExceptionEnum;
import cn.bankrupt.workflow.model.entity.*;
import cn.bankrupt.workflow.model.enums.DeploymentStatus;
import cn.bankrupt.workflow.model.enums.ModelType;
import cn.bankrupt.workflow.model.service.*;
import cn.bankrupt.workflow.web.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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

		//复制新的表单模型关联信息
		List<WorkFlowFormModelInfo> formModelInfos = workFlowFormModelInfoService.list(Wrappers.<WorkFlowFormModelInfo>lambdaQuery()
				.eq(WorkFlowFormModelInfo::getModelId, modelInfo.getModelId())
				.eq(WorkFlowFormModelInfo::getCategoryId, modelInfoVo.getCategoryId())
				.orderByDesc(WorkFlowFormModelInfo::getVersion));

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
		String newName = category.getCategoryName() +":"+model.getModelName()+":version:"+copy.getVersion();
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
				String value = modelKey+"_"+ DateUtil.today()+"_"+copy.getVersion();
				process.setAttribute("id",value);
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