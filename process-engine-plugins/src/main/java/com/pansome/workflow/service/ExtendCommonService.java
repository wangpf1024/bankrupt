package com.pansome.workflow.service;

import com.pansome.workflow.domain.workflow.TaskMsgDataDto;

/**
 * 扩展业务类：兼容自有业务
 */
public interface ExtendCommonService {

    //初始化基础数据
    default void initProcessValues(){

    }

    /**
     * 处理扩展业务
     * @param dto
     * @return
     */
    TaskMsgDataDto getWorkFlowModelInfo(TaskMsgDataDto dto);
}
