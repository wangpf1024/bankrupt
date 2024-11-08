/**
 * @filename:WorkFlowFormCategoryController 2024年6月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.controller;

import cn.bankrupt.workflow.web.AbstractController;
import cn.bankrupt.workflow.model.entity.WorkFlowFormCategory;
import cn.bankrupt.workflow.model.service.WorkFlowFormCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 表单分类API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年6月27日
 *
 */
@Api(description = "表单分类",value="表单分类" )
@RestController
@RequestMapping("/process-model-server/workFlowFormCategory")
public class WorkFlowFormCategoryController extends AbstractController<WorkFlowFormCategoryService,WorkFlowFormCategory>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}