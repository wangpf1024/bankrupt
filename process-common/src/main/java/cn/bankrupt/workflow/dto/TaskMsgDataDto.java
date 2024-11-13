package cn.bankrupt.workflow.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskMsgDataDto {

    /**
     *功能范围(1 跳过已通过)
     */
    private String functionRange;
    /**
     * businessKey:租户id+项目id+分类id+modelKey+业务数据记录id
     */
    private String businessKey;

    /**
     * 事件名称（流程引擎）
     */
    protected String eventName;
    /**
     * 事件code（自定义）
     */
    protected String eventCode;


    /**
     * 流程执行引擎相关自动
     */
    protected String processDefinitionId;
    protected String processInstanceId;
    protected String processDefinitionKey;
    protected String taskDefinitionKey;

    /**
     * 开始人
     */
    protected String startBy;
    /**
     * 任务执行人
     */
    protected String assignee;

    /**
     * 当前执行任务ID
     */
    protected String currentTaskId;
    /**
     * 当前执行用户ID
     */
    protected String currentTaskUser;
    /**
     * 当前用户所属分组
     */
    protected List<String> currentGroupIds;
    /**
     * 下一个任务执行用户
     */
    private List<String> nextTaskUser;

    /**
     * 参数（流程变量-暂时不做透传处理）
     */
    //protected Map<String, Object> variables;
}
