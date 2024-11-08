package cn.bankrupt.workflow.listener;

import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;

public class CommonDelegate {


    protected TaskMsgDataDto prams(DelegateTask delegateTask){
        TaskMsgDataDto dto = new TaskMsgDataDto();
        dto.setEventName(delegateTask.getEventName());
        dto.setProcessInstanceId(delegateTask.getProcessInstanceId());
        dto.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
        dto.setProcessDefinitionKey(((TaskEntity) delegateTask).getProcessDefinition().getKey());
        dto.setAssignee(delegateTask.getAssignee());
        dto.setVariables(delegateTask.getVariables());
        dto.setTaskDefinitionKey(delegateTask.getTaskDefinitionKey());
        dto.setBusinessKey(((TaskEntity) delegateTask).getProcessInstance().getBusinessKey());
        dto.setCurrentTaskId(delegateTask.getId());
        dto.setCurrentTaskUser(delegateTask.getAssignee());
        return dto;
    }


    protected TaskMsgDataDto prams(DelegateExecution execution){
        TaskMsgDataDto dto = new TaskMsgDataDto();
        dto.setEventName(execution.getEventName());
        dto.setProcessInstanceId(execution.getProcessInstanceId());
        dto.setProcessDefinitionId(execution.getProcessDefinitionId());
        //dto.setVariables(execution.getVariables());
        dto.setBusinessKey(execution.getBusinessKey());
        dto.setCurrentTaskId(execution.getId());
        return dto;
    }


}
