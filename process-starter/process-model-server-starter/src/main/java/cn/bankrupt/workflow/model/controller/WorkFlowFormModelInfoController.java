/**
 * @filename:WorkFlowFormModelInfoController 2024年6月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.web.AbstractController;
import cn.bankrupt.workflow.model.entity.WorkFlowFormModelInfo;
import cn.bankrupt.workflow.model.service.WorkFlowFormModelInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 表单信息API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年6月27日
 *
 */
@Api(description = "表单信息",value="表单信息" )
@RestController
@RequestMapping("/process-model-server/workFlowFormModelInfo")
public class WorkFlowFormModelInfoController extends AbstractController<WorkFlowFormModelInfoService,WorkFlowFormModelInfo>{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	WorkFlowFormModelInfoService workFlowModelInfoService;
	/**
	 * @explain 检查formKey  <swagger POST请求>
	 * @param   @RequestBody：ModelingEntityDef
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月10日
	 */
	@GetMapping("/checkMKey/{mKey}")
	@ApiOperation(value = "检查formKey", notes = "作者：王鹏飞")
	public ResultBean<String> checkMKey(@PathVariable("mKey") String mKey) {

		long i = workFlowModelInfoService.count(Wrappers.<WorkFlowFormModelInfo>lambdaQuery()
				.eq(WorkFlowFormModelInfo::getFormKey, mKey));

		if (i  > 0) {
			return ResultBean.ofError( mKey + "标识已存在");
		}
		return ResultBean.ofSuccess(mKey);
	}
	
}