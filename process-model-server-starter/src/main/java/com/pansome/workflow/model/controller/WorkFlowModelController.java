/**
 * @filename:WorkFlowModelController 2024年7月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.model.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pansome.workflow.ResultBean;
import com.pansome.workflow.enums.BaseExceptionEnum;
import com.pansome.workflow.model.entity.*;
import com.pansome.workflow.model.service.WorkFlowModelCategoryService;
import com.pansome.workflow.model.service.WorkFlowModelDeploymentService;
import com.pansome.workflow.model.service.WorkFlowModelInfoService;
import com.pansome.workflow.web.AbstractController;
import com.pansome.workflow.model.service.WorkFlowModelService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程模型API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月27日
 *
 */
@Api(description = "流程模型",value="流程模型" )
@RestController
@RequestMapping("/process-model-server/workFlowModel")
public class WorkFlowModelController extends AbstractController<WorkFlowModelService,WorkFlowModel>{

	Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	WorkFlowModelInfoService workFlowModelInfoService;
	@Autowired
	WorkFlowModelCategoryService workFlowModelCategoryService;
	@Autowired
	WorkFlowModelDeploymentService workFlowModelDeploymentService;


	/**
	 * @explain 查询对象  <swagger GET请求>，并且返回一个新的
	 * @param   @PathVariable：id
	 * @return  ResultBean
	 * @author  王鹏飞
	 * @time    2024年6月24日
	 */
	@GetMapping("/deployedBpmn/{id}")
	@ApiOperation(value = "获取对象", notes = "作者：王鹏飞")
	@ApiImplicitParam(paramType="path", name = "id", value = "对象id", required = true, dataType = "Long")
	public ResultBean<WorkFlowModel> deployedBpmn(@PathVariable("id")Long id){
		WorkFlowModel obj = baseService.getById(id);
		if (null!=obj ) {
			Long count = workFlowModelDeploymentService.count(Wrappers.<WorkFlowModelDeployment>lambdaQuery()
					.eq(WorkFlowModelDeployment::getModelId, id));
			++ count;
			String newName = obj.getModelName()+":"+count;
			WorkFlowModelDeployment deployment = new WorkFlowModelDeployment();
			deployment.setModelName(newName);
			deployment.setModelKey(obj.getModelKey());
			deployment.setModelId(String.valueOf(id));
			// 创建 SAXBuilder 实例
			SAXBuilder saxBuilder = new SAXBuilder();
			// 解析 XML 字符串
			try {
				Document document = saxBuilder.build(new StringReader(obj.getModelXml()));
				Element rootElement = document.getRootElement();
				// 查找 <bpmn:process> 元素
				Element process = rootElement.getChild("process", rootElement.getNamespace("bpmn"));
				if (process != null) {
					String value = obj.getModelKey()+"_"+count;
					if(StrUtil.isNotEmpty(obj.getFileName())){
						value = obj.getFileName()+"_"+count;
					}
					deployment.setFileName(value);
					process.setAttribute("id",value);
					process.setAttribute("name",newName);
				}
				// Convert the modified document back to a string
				StringWriter stringWriter = new StringWriter();
				XMLOutputter xmlOutputter = new XMLOutputter();
				xmlOutputter.setFormat(Format.getPrettyFormat());
				xmlOutputter.output(document, stringWriter);
				deployment.setModelXml(stringWriter.toString());
				deployment.setCreateTime(new Date());
			} catch (JDOMException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			workFlowModelDeploymentService.save(deployment);
			obj.setModelName(deployment.getModelName());
			obj.setModelXml(deployment.getModelXml());
			return ResultBean.ofSuccess(obj);
		}
		return ResultBean.ofError("查询对象不存在！");
	}


}