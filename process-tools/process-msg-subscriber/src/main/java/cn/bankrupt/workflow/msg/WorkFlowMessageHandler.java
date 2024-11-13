package cn.bankrupt.workflow.msg;


import cn.bankrupt.workflow.dto.TaskMsgDataDto;

public interface WorkFlowMessageHandler {

    void execute(TaskMsgDataDto dto);

    default String getEventCode(){
        return null;
    }
}
