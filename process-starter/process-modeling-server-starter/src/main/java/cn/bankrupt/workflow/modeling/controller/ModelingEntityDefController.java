/**
 * @filename:ModelingEntityDefController 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.modeling.controller;

import cn.bankrupt.workflow.modeling.entity.ModelingEntityDef;
import cn.bankrupt.workflow.modeling.service.ModelingEntityDefService;
import cn.bankrupt.workflow.web.AbstractController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 实体定义API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月2日
 *
 */
@Api(description = "实体定义",value="实体定义" )
@RestController
@RequestMapping("/process-modeling-server/modelingEntityDef")
public class ModelingEntityDefController extends AbstractController<ModelingEntityDefService,ModelingEntityDef>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}