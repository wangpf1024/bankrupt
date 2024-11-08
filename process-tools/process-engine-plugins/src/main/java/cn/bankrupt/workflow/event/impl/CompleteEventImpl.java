package cn.bankrupt.workflow.event.impl;

import cn.bankrupt.workflow.event.UserTaskEventInterface;
import cn.hutool.json.JSONUtil;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("completeEventImpl")
public class CompleteEventImpl implements UserTaskEventInterface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    final String  eventCode = ProcessWorkFlowBaseEventEnum.user_complete.getCode();

    @Autowired
    ProcessRedisCache processRedisCache;

    @Override
    public void handleEvent(TaskMsgDataDto dto) {
        dto.setEventCode(eventCode);
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessageWithSchema(eventCode, msg);
        logger.info("event : "+dto.getEventCode() + " task push msg " + msg);
    }
}
