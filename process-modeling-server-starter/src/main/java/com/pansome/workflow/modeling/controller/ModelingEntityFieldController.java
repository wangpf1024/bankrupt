/**
 * @filename:ModelingEntityFieldController 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.modeling.controller;

import com.pansome.workflow.web.AbstractController;
import com.pansome.workflow.modeling.entity.ModelingEntityField;
import com.pansome.workflow.modeling.service.ModelingEntityFieldService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 实体与字段关系API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月2日
 *
 */
@Api(description = "实体与字段关系",value="实体与字段关系" )
@RestController
@RequestMapping("/process-modeling-server/modelingEntityField")
public class ModelingEntityFieldController extends AbstractController<ModelingEntityFieldService,ModelingEntityField>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}