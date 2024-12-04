/**
 * @filename:WorkFlowModelDeploymentController 2024年11月29日
 * @project workflow  1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.model.controller;

import com.pansome.workflow.web.AbstractController;
import com.pansome.workflow.model.entity.WorkFlowModelDeployment;
import com.pansome.workflow.model.service.WorkFlowModelDeploymentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 部署流程API接口层</P>
 * @version: 1.0.0
 * @author: 王鹏飞
 * @time    2024年11月29日
 *
 */
@Api(description = "部署流程",value="部署流程" )
@RestController
@RequestMapping("/process-model-server/workFlowModelDeployment")
public class WorkFlowModelDeploymentController extends AbstractController<WorkFlowModelDeploymentService,WorkFlowModelDeployment>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}