/**
 * @filename:WorkFlowModelController 2024年7月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.controller;

import cn.bankrupt.workflow.model.entity.WorkFlowModel;
import cn.bankrupt.workflow.model.service.WorkFlowModelService;
import cn.bankrupt.workflow.web.AbstractController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	
}