package com.pansome.workflow.event;

import com.pansome.workflow.domain.workflow.TaskMsgDataDto;

/**
 * 用户接口
 */
public interface UserTaskEventInterface{

    /**
     * 处理数据
     * @param dto
     * @return
     */
    void handleEvent(TaskMsgDataDto dto);

}
