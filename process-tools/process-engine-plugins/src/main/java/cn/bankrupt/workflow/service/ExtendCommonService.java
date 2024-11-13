package cn.bankrupt.workflow.service;

import cn.bankrupt.workflow.dto.TaskMsgDataDto;

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
