/**
 * @filename:WorkFlowHiCommentController 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.center.controller;

import com.pansome.workflow.web.AbstractController;
import com.pansome.workflow.center.entity.WorkFlowHiComment;
import com.pansome.workflow.center.service.WorkFlowHiCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 审批意见API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月2日
 *
 */
@Api(description = "审批意见",value="审批意见" )
@RestController
@RequestMapping("/process-center-server/workFlowHiComment")
public class WorkFlowHiCommentController extends AbstractController<WorkFlowHiCommentService,WorkFlowHiComment>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}