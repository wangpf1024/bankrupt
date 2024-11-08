package cn.bankrupt.workflow.event;

import cn.bankrupt.workflow.dto.TaskMsgDataDto;

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
