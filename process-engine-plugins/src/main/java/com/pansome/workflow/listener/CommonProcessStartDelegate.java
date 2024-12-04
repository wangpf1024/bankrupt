package com.pansome.workflow.listener;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.pansome.workflow.cache.ProcessRedisCache;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import com.pansome.workflow.service.ExtendCommonService;
import com.pansome.workflow.service.UserTaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
public class CommonProcessStartDelegate extends CommonDelegate implements JavaDelegate {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    ProcessRedisCache processRedisCache;

    @Autowired
    List<ExtendCommonService> extendCommonServices;

    @Autowired
    UserTaskService userTaskService;


    @Override
    public void execute(DelegateExecution execution) throws Exception {

        //重新初始化参数
        TaskMsgDataDto dto = super.prams(execution,ProcessWorkFlowBaseEventEnum.process_start);

        for (int i = 0; i < extendCommonServices.size(); i++) {
            extendCommonServices.get(i).initProcessValues();
        }
        //获取设置参数
        for (int i = 0; i < extendCommonServices.size(); i++) {
            dto = extendCommonServices.get(i).getWorkFlowModelInfo(dto);
        }

        //处理 功能范围(1 跳过已通过)
        boolean skip = (StrUtil.isNotEmpty(dto.getFunctionRange())
                && ProcessWorkFlowBaseEventEnum.function_range_skip_pass.getCode()
                .equals(dto.getFunctionRange())) ? true : false;
        execution.setVariable(ProcessWorkFlowBaseEventEnum.function_range_skip_pass.name(), skip);

        //设置发起人
        dto.setStartBy((String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_apply.getCode()));

        execution.setVariable(ProcessWorkFlowBaseEventEnum.variable_starter.getCode(),dto.getStartBy());

        //处理任务所有者-已设置
        if(execution.hasVariable(ProcessWorkFlowBaseEventEnum.variable_owner.getCode())){
            String owner = (String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_owner.getCode());
            if(StrUtil.isNotEmpty(owner)){
                //默认完成任务的人员
                dto.setOwner(owner);
            }
        }

        //处理任务所有者-未设置，默认申请人
        if(StrUtil.isEmpty(dto.getOwner())){
            dto.setOwner((String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_apply.getCode()));
            execution.setVariable(ProcessWorkFlowBaseEventEnum.variable_owner.getCode(),dto.getOwner());
        }
        //处理人
        dto.setAssignee(dto.getStartBy());

        //设置业务序列号
        StringBuilder serialNumber = new StringBuilder("SX");
        serialNumber.append(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        dto.setSerialNumber(serialNumber.toString());
        execution.setVariable(ProcessWorkFlowBaseEventEnum.variable_serialNumber.getCode(),dto.getSerialNumber());
        String businessDesc = "流程发起时请增加 businessDesc 环境变量，用来描述事项名称";
        if(execution.hasVariable(ProcessWorkFlowBaseEventEnum.variable_businessDesc.getCode())){
            String desc = (String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_businessDesc.getCode());
            if(StrUtil.isNotEmpty(desc)){
                businessDesc = desc;
            }
        }
        dto.setBusinessDesc(businessDesc);
        //事件处理地址
        if(execution.hasVariable(ProcessWorkFlowBaseEventEnum.variable_event_handler_address.getCode())){
            String desc = (String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_event_handler_address.getCode());
            if(StrUtil.isNotEmpty(desc)){
                dto.setEventHandler(desc);
            }
        }
        //事件来源
        execution.setVariable(ProcessWorkFlowBaseEventEnum.variable_event_source.getCode(),dto.getEventSource());
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        logger.info("event : "+ dto.getEventCode()  + "  dto   " + msg);
        processRedisCache.enqueueMessageWithSchema(ProcessWorkFlowBaseEventEnum.process_event_step_by_step.getCode(), msg);
    }
}
