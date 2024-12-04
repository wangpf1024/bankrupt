package com.pansome.workflow.enums.workflow;


public enum ProcessWorkFlowBaseEventEnum {

    /**
     * 用户事件部分
     */
    user_create("user_create", "用户创建事件"),
    user_complete("user_complete", "用户完成事件"),
    user_assignment("user_assignment", "用户签署事件"),
    user_reject("user_reject", "用户拒绝"),
    user_next_task("user_next_task", "用户下个任务"),
    process_start("process_start", "流程开始"),
    process_end("process_end", "流程结束"),


    /**
     * 流程变量部分
     */
    variable_last_completed_by("lastCompletedBy", "最后一次完成任务的用户"),
    variable_apply("apply", "任务申请人"),
    variable_owner("owner", "任务所有人"),
    variable_starter("startBy", "任务开始人"),
    variable_serialNumber("serialNumber", "流程序列号"),
    variable_businessDesc("businessDesc", "业务描述"),
    variable_event_handler_address("businessBandlerAddress", "业务描述"),
    variable_event_source("eventSource", "事件来源"),
    variable_camunda_pass_status("camunda_passStatus", "驳回"),
    variable_reject_times("taskRejectTimes", "驳回次数"),
    variable_auto_tasks("variable_auto_tasks", "自动完成任务"),
    variable_comment("camunda_approval_comment", "事件审批意见描述"),

    /**
     * 流程功能部分
     */
    function_range_skip_pass("1", "功能范围(1 跳过已通过)"),
    model_name("modelName", "模型名称"),
    category_name("categoryName", "分类名称"),
    in_process_completed_tasks("completed_task", "(流程执行中)已通过任务列表"),
    process_deployed_values("%s", "流程变量配置"),

    /**
     * 消息通知部分
     */
    process_event_step_by_step("process_event_step_by_step", "流程执行事透传通过"),
    quanguocheng_channel("quanguocheng_process_event_step_by_step", "源海系统透传通道");

    private String code;

    private String message;

    ProcessWorkFlowBaseEventEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
