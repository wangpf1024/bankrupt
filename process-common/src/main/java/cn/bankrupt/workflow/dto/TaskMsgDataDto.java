package cn.bankrupt.workflow.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TaskMsgDataDto {
    private String businessKey;
    protected String eventName;
    protected String eventCode;
    protected String processDefinitionId;
    protected String processInstanceId;
    protected String processDefinitionKey;
    protected String assignee;
    protected String taskDefinitionKey;
    protected String currentTaskId;
    protected String currentTaskUser;
    protected Map<String, Object> variables;
    private String modelKey;
    private String modelName;
    private String categoryName;
    private String categoryCode;
    private Long categoryId;
    private String formKey;
    private String tenantId;
    private List<String> nextTaskUser;
}
