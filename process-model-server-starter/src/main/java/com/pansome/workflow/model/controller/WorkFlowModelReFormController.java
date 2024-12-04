/**
 * @filename:WorkFlowModelReFormController 2024年7月1日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.model.controller;

import com.pansome.workflow.web.AbstractController;
import com.pansome.workflow.model.entity.WorkFlowModelReForm;
import com.pansome.workflow.model.service.WorkFlowModelReFormService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程关联表单API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月1日
 *
 */
@Api(description = "流程关联表单",value="流程关联表单" )
@RestController
@RequestMapping("/process-model-server/workFlowModelReForm")
public class WorkFlowModelReFormController extends AbstractController<WorkFlowModelReFormService,WorkFlowModelReForm>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}