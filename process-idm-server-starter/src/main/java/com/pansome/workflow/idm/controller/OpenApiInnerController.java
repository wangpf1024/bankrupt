/**
 * @filename:OpenApiController 2024年10月21日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.idm.controller;

import com.pansome.workflow.ResultBean;
import com.pansome.workflow.idm.service.OpenApiUserService;
import com.pansome.workflow.openapi.domain.vo.GroupReqExt;
import com.pansome.workflow.openapi.domain.vo.TenantReqExt;
import com.pansome.workflow.openapi.domain.vo.UserRelationReqExt;
import com.pansome.workflow.openapi.domain.vo.UserReqExt;
import com.pansome.workflow.openapi.service.OpenApiInnerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程模型API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年10月21日
 *
 */
@Api(description = "内部业务平台",value="内部业务平台" )
@RestController
public class OpenApiInnerController implements OpenApiInnerService {

	Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	OpenApiUserService openApiUserService;

	/**
	 * @explain 新增租户/项目  <swagger POST请求>
	 * @param   @RequestBody：TenantReqExt
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月3日
	 */
	@ApiOperation(value = "新增租户/项目", notes = "作者：王鹏飞")
	public ResultBean<String> addTenant(@Validated @RequestBody TenantReqExt param) {
		return  openApiUserService.addTenant(param);
	}

	/**
	 * @explain 新增分组  <swagger POST请求>
	 * @param   @RequestBody：ModelingFieldDefExt
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月3日
	 */
	@ApiOperation(value = "新增分组/角色", notes = "作者：王鹏飞")
	public ResultBean<String> addGroup(@Validated @RequestBody GroupReqExt param) {
		return  openApiUserService.addGroup(param);
	}


	/**
	 * @explain 新增用户  <swagger POST请求>
	 * @param   @RequestBody：ModelingFieldDefExt
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月3日
	 */
	@ApiOperation(value = "新增用户", notes = "作者：王鹏飞")
	public ResultBean<String> addUser(@Validated @RequestBody UserReqExt param) {
		return  openApiUserService.addUser(param);
	}


	/**
	 * @explain 新增用户分组，租户关系  <swagger POST请求>
	 * @param   @RequestBody：ModelingFieldDefExt
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月3日
	 */
	@ApiOperation(value = "新增分组/角色关系 ", notes = "作者：王鹏飞")
	public ResultBean<String> addUserGroup(@Validated @RequestBody UserRelationReqExt param) {
		return  openApiUserService.addUserGroup(param);
	}

	/**
	 * @explain 批量新增用户分组，租户关系  <swagger POST请求>
	 * @param   @RequestBody：ModelingFieldDefExt
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月3日
	 */
	@Override
	@ApiOperation(value = "批量新增分组/角色关系 ", notes = "作者：王鹏飞")
	public ResultBean<String> addBatchUserGroup(@RequestBody List<UserRelationReqExt> param) {
		return  openApiUserService.addBatchUserGroup(param);
	}


}