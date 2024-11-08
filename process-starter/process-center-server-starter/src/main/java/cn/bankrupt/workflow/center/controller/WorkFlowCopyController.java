/**
 * @filename:WorkFlowCopyController 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.center.controller;

import cn.bankrupt.workflow.web.AbstractController;
import cn.bankrupt.workflow.center.entity.WorkFlowCopy;
import cn.bankrupt.workflow.center.service.WorkFlowCopyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程抄送API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月2日
 *
 */
@Api(description = "流程抄送",value="流程抄送" )
@RestController
@RequestMapping("/process-center-server/workFlowCopy")
public class WorkFlowCopyController extends AbstractController<WorkFlowCopyService,WorkFlowCopy>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}