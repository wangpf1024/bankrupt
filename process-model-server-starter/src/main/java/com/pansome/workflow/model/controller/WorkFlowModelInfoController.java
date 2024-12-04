/**
 * @filename:WorkFlowModelInfoController 2024年6月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.model.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pansome.workflow.ResultBean;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowDeployStatusEnum;
import com.pansome.workflow.model.entity.WorkFlowService;
import com.pansome.workflow.model.enums.DeploymentStatus;
import com.pansome.workflow.model.service.WorkFlowModelService;
import com.pansome.workflow.model.service.WorkFlowServiceService;
import com.pansome.workflow.web.AbstractController;
import com.pansome.workflow.model.entity.WorkFlowModelInfo;
import com.pansome.workflow.model.service.WorkFlowModelInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.List;





/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程信息API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年6月27日
 *
 */
@Api(description = "流程信息",value="流程信息" )
@RestController
@RequestMapping("/process-model-server/workFlowModelInfo")
public class WorkFlowModelInfoController extends AbstractController<WorkFlowModelInfoService,WorkFlowModelInfo>{

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WorkFlowServiceService workFlowServiceService;

	@Autowired
	WorkFlowModelService workFlowModelService;


	/**
	 * @explain 运行实例  <swagger POST请求>
	 * @param   @RequestBody：ModelingEntityDef
	 * @return  Void
	 * @author  王鹏飞
	 * @time    2024年7月10日
	 */
	@GetMapping("/deployed/{modelKey}")
	@ApiOperation(value = "执行流程实例查询", notes = "作者：王鹏飞")
	public ResultBean<WorkFlowModelInfo> deployedModelInfo(@PathVariable("modelKey")String modelKey,
														   @RequestParam("projectId")String projectId) {

		//匹配到合适 service
		List<WorkFlowService> list = workFlowServiceService.list(Wrappers.<WorkFlowService>lambdaQuery()
				.eq(WorkFlowService::getServiceCode, modelKey)
				.eq(WorkFlowService::getStatus,DeploymentStatus.DEPLOYED.getCode())
				.eq(WorkFlowService::getApplyProjectId,projectId)
				.orderByDesc(WorkFlowService::getId));

		if(CollectionUtils.isEmpty(list)){
			return ResultBean.ofError("无可执行流程实例");
		}

		WorkFlowService service = list.get(0);

		WorkFlowModelInfo modelInfo = baseService.getById(service.getModelInfoId());

		return ResultBean.ofSuccess(modelInfo);
	}


	/**
	 * 分页查询所有数据
	 *
	 * @param pageIndex     页码
	 * @param pageSize      页长
	 * @param t 查询实体
	 * @return 所有数据
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：王鹏飞")
	@GetMapping("/getPages")
	@Override
	public ResultBean<List<WorkFlowModelInfo>> getPages(WorkFlowModelInfo t,@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
										@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

		boolean filterTime = false;
		Calendar endTime = Calendar.getInstance();
		if(t.getCreateTime() != null){
			endTime.setTime(t.getCreateTime());
			endTime.add(Calendar.DAY_OF_YEAR,1);
			filterTime = true;
		}

		PageHelper.startPage(pageIndex, pageSize);

		List<WorkFlowModelInfo> list = baseService.list(Wrappers.<WorkFlowModelInfo>lambdaQuery()
				.like(StrUtil.isNotEmpty(t.getName()),WorkFlowModelInfo::getName, t.getName())
				.eq(t.getModelType() != null,WorkFlowModelInfo::getModelType, t.getModelType())
				.eq(t.getCategoryId() != null,WorkFlowModelInfo::getCategoryId, t.getCategoryId())
				.eq(StrUtil.isNotEmpty(t.getApplyProjectId()),WorkFlowModelInfo::getApplyProjectId, t.getApplyProjectId())
				.eq(StrUtil.isNotEmpty(t.getApplyCompanyId()),WorkFlowModelInfo::getApplyCompanyId, t.getApplyCompanyId())
				.eq(StrUtil.isNotEmpty(t.getFunctionRange()),WorkFlowModelInfo::getFunctionRange, t.getFunctionRange())
				.like(StrUtil.isNotEmpty(t.getCreateBy()),WorkFlowModelInfo::getCreateBy, t.getCreateBy())
				.gt(filterTime,WorkFlowModelInfo::getCreateTime,t.getCreateTime())
				.lt(filterTime,WorkFlowModelInfo::getCreateTime,endTime.getTime())
				.orderByDesc(WorkFlowModelInfo::getCreateTime));

		PageInfo<WorkFlowModelInfo> pageInfo = new PageInfo<>(list);

		return ResultBean.ofSuccess(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
	}




	/**
	 * @explain 修改
	 * @param   @RequestBody ：T
	 * @return  Boolean
	 * @author  王鹏飞
	 * @time    2024年6月24日
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "作者：王鹏飞")
	public ResultBean<WorkFlowModelInfo> update(@RequestBody WorkFlowModelInfo entity){
		if (null!=entity) {
			boolean rsg = baseService.updateById(entity);
			if (rsg) {
				WorkFlowModelInfo modelInfoDb = baseService.getById(entity.getId());
				//取消部署
				if(ProcessWorkFlowDeployStatusEnum.undeploy.getStatus() == modelInfoDb.getStatus()){
                    //todo nothings
				}
				if(ProcessWorkFlowDeployStatusEnum.deployed.getStatus() == modelInfoDb.getStatus()){
					WorkFlowService service = getFlowService(modelInfoDb);
					workFlowServiceService.save(service);
				}
				return ResultBean.ofSuccess(entity,"修改成功");
			}else {
				return ResultBean.ofError("修改失败！");
			}
		}
		return ResultBean.ofError("请传入正确参数！");
	}

	private WorkFlowService getFlowService(WorkFlowModelInfo modelInfoDb) {
		WorkFlowService service = new WorkFlowService();
		service.setCreateBy(modelInfoDb.getCreateBy());
		service.setCreateTime(new Date());
		service.setServiceCode(modelInfoDb.getModelKey());
		service.setServiceName(modelInfoDb.getName());
		service.setStatus(ProcessWorkFlowDeployStatusEnum.deployed.getStatus());
		service.setTenantId(modelInfoDb.getTenantId());
		service.setDefinitionId(modelInfoDb.getDefinitionId());
		service.setDefinitionKey(modelInfoDb.getDefinitionKey());
		service.setDeploymentId(modelInfoDb.getDeploymentId());
		service.setApplyCompanyId(modelInfoDb.getApplyCompanyId());
		service.setApplyProjectId(modelInfoDb.getApplyProjectId());
		service.setModelInfoId(modelInfoDb.getId());
		service.setDefinitionName(modelInfoDb.getName());
		return service;
	}


}