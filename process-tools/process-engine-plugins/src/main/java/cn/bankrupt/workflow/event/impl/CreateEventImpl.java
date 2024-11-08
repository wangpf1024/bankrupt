package cn.bankrupt.workflow.event.impl;

import cn.bankrupt.workflow.event.UserTaskEventInterface;
import cn.hutool.json.JSONUtil;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import jakarta.ws.rs.core.Response;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("createEventImpl")
public class CreateEventImpl implements UserTaskEventInterface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    final String  eventCode = ProcessWorkFlowBaseEventEnum.user_create.getCode();

    @Autowired
    ProcessRedisCache processRedisCache;

    @Autowired
    ProcessEngine processEngine;

    protected ProcessEngine getProcessEngine() {
        if (processEngine == null) {
            throw new InvalidRequestException(Response.Status.BAD_REQUEST, "No process engine available");
        }
        return processEngine;
    }

    @Override
    public void handleEvent(TaskMsgDataDto dto) {
        dto.setEventCode(eventCode);
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessageWithSchema(eventCode, msg);
        logger.info("event : "+dto.getEventCode() + " task push msg " + msg);
    }
}
