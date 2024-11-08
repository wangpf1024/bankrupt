package cn.bankrupt.workflow.enums;


public enum ProcessWorkFlowBaseEventEnum {

    user_create("user_create", "用户创建事件"),
    user_complete("user_complete", "用户完成事件"),
    user_assignment("user_assignment", "用户签署事件"),
    user_reject("user_reject", "用户拒绝"),
    user_next_task("user_next_task", "用户下个任务"),

    process_start("process_start", "流程开始"),
    process_end("process_end", "流程结束"),


    quanguocheng_channel("quanguocheng_sync_dequeue_", "源海系统透传通道");

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
