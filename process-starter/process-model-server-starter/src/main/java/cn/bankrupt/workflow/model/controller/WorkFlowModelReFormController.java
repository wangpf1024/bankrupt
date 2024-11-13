/**
 * @filename:WorkFlowModelReFormController 2024年7月1日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.controller;

import cn.bankrupt.workflow.model.entity.WorkFlowModelReForm;
import cn.bankrupt.workflow.model.service.WorkFlowModelReFormService;
import cn.bankrupt.workflow.web.AbstractController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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