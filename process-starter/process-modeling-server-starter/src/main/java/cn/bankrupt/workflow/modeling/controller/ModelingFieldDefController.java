/**
 * @filename:ModelingFieldDefController 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.modeling.controller;

import cn.bankrupt.workflow.modeling.entity.ModelingFieldDef;
import cn.bankrupt.workflow.modeling.service.ModelingFieldDefService;
import cn.bankrupt.workflow.web.AbstractController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 字段定义API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月2日
 *
 */
@Api(description = "字段定义",value="字段定义" )
@RestController
@RequestMapping("/process-modeling-server/modelingFieldDef")
public class ModelingFieldDefController extends AbstractController<ModelingFieldDefService,ModelingFieldDef>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}