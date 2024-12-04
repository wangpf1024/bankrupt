/**
 * @filename:WorkFlowFormModelController 2024年6月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.model.controller;

import com.pansome.workflow.web.AbstractController;
import com.pansome.workflow.model.entity.WorkFlowFormModel;
import com.pansome.workflow.model.service.WorkFlowFormModelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 表单模型API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年6月27日
 *
 */
@Api(description = "表单模型",value="表单模型" )
@RestController
@RequestMapping("/process-model-server/workFlowFormModel")
public class WorkFlowFormModelController extends AbstractController<WorkFlowFormModelService,WorkFlowFormModel>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}