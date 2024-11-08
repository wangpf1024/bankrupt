/**
 * @filename:OpenApiController 2024年10月21日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.idm.controller;

import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.domain.LoginBody;
import cn.bankrupt.workflow.domain.OpenApiBody;
import cn.bankrupt.workflow.idm.service.LoginService;
import cn.bankrupt.workflow.web.AbstractController;
import cn.bankrupt.workflow.idm.entity.OpenApi;
import cn.bankrupt.workflow.idm.service.OpenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程模型API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年10月21日
 *
 */
@Api(description = "AccessToken",value="AccessToken" )
@RestController
public class OpenApiTokenController extends AbstractController<OpenApiService,OpenApi>{

	Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	LoginService loginService;

	/**
	 * API_ACCESS_TOKEN
	 *
	 * @param openApiBody 登录信息
	 * @return 结果
	 */
	@PostMapping("/access_token")
	public ResultBean<String> login(@RequestBody OpenApiBody openApiBody)
	{
		// 生成令牌
		String token = loginService.accessToken(openApiBody);
		return ResultBean.ofSuccess(token);
	}


	/**
	 * 前台页面登录方法
	 *
	 * @param loginBody 登录信息
	 * @return 结果
	 */
	@PostMapping("/login")
	public ResultBean<String> login(@RequestBody LoginBody loginBody)
	{
		// 生成令牌
		String token = loginService.login(loginBody);
		return ResultBean.ofSuccess(token);
	}

}