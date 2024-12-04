package com.pansome.workflow.listener;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowStatusEnum;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;

import java.util.Date;

public class CommonDelegate {

    protected TaskMsgDataDto prams(DelegateTask delegateTask){
        TaskMsgDataDto dto = new TaskMsgDataDto();
        dto.setEventName(delegateTask.getEventName());
        dto.setProcessInstanceId(delegateTask.getProcessInstanceId());
        dto.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
        dto.setProcessDefinitionKey(((TaskEntity) delegateTask).getProcessDefinition().getKey());
        dto.setAssignee(delegateTask.getAssignee());
        dto.setTaskDefinitionKey(delegateTask.getTaskDefinitionKey());
        dto.setBusinessKey(((TaskEntity) delegateTask).getProcessInstance().getBusinessKey());
        dto.setCurrentTaskId(delegateTask.getId());
        dto.setCurrentTaskUser(delegateTask.getAssignee());
        //是否存在驳回动作
        Boolean hasVar = delegateTask.hasVariable(ProcessWorkFlowBaseEventEnum.variable_reject_times.getCode());
        if(hasVar){
            dto.setProcessStatus(ProcessWorkFlowStatusEnum.BH.name());
        }
        dto.setOwner((String) delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_owner.getCode()));
        dto.setStartBy((String) delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_starter.getCode()));
        dto.setSerialNumber((String) delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_serialNumber.getCode()));
        dto.setEventSource((String) delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_event_source.getCode()));
        dto.setEventTime(DateUtil.formatDateTime(new Date()));
        return dto;
    }

    protected TaskMsgDataDto prams(DelegateExecution execution, ProcessWorkFlowBaseEventEnum eventCode){
        TaskMsgDataDto dto = new TaskMsgDataDto();
        dto.setEventCode(eventCode.getCode());
        dto.setEventName(execution.getEventName());
        dto.setProcessInstanceId(execution.getProcessInstanceId());
        dto.setProcessDefinitionId(execution.getProcessDefinitionId());
        dto.setBusinessKey(execution.getBusinessKey());
        dto.setCurrentTaskId(execution.getId());
        dto.setEventTime(DateUtil.formatDateTime(new Date()));
        if(ProcessWorkFlowBaseEventEnum.process_end == eventCode
                || ProcessWorkFlowBaseEventEnum.user_reject == eventCode){

            dto.setOwner((String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_owner.getCode()));
            dto.setStartBy((String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_starter.getCode()));
            dto.setAssignee(dto.getStartBy());
            dto.setSerialNumber((String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_serialNumber.getCode()));
        }
        return dto;
    }


}
