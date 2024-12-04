package com.pansome.workflow.msg;


import com.pansome.workflow.domain.workflow.TaskMsgDataDto;

public interface WorkFlowMessageHandler {

    void execute(TaskMsgDataDto dto);

    default String getEventCode(){
        return null;
    }
}
