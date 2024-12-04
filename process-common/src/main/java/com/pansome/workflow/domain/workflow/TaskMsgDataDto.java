package com.pansome.workflow.domain.workflow;

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
     * 业务描述
     */
    private String businessDesc;

    /**
     * 事件名称（流程引擎）
     */
    protected String eventName;
    /**
     * 事件code（自定义）
     */
    protected String eventCode;
    /**
     * 事件来源
     */
    private String eventSource;
    /**
     * 事件描述
     */
    protected String eventComment;
    /**
     *事件处理地址
     */
    private String eventHandler;
    /**
     *事件处理时间
     */
    private String eventTime;

    /**
     * 流程执行引擎相关自动
     */
    protected String processDefinitionId;
    protected String processInstanceId;
    protected String processDefinitionKey;
    protected String taskDefinitionKey;
    protected String processStatus;

    /**
     * 开始人
     */
    protected String startBy;
    /**
     * 任务执行人
     */
    protected String assignee;
    /**
     * 任务所有人
     */
    protected String owner;

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

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 序列号
     */
    private String serialNumber;
}
