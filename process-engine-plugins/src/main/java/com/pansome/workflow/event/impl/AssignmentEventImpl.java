package com.pansome.workflow.event.impl;

import cn.hutool.json.JSONUtil;
import com.pansome.workflow.event.UserTaskEventInterface;
import com.pansome.workflow.cache.ProcessRedisCache;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("assignmentEventImpl")
public class AssignmentEventImpl implements UserTaskEventInterface {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    final String eventCode = ProcessWorkFlowBaseEventEnum.user_assignment.getCode();


    @Autowired
    ProcessRedisCache processRedisCache;


    @Override
    public void handleEvent(TaskMsgDataDto dto) {
        dto.setEventCode(eventCode);
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessageWithSchema(ProcessWorkFlowBaseEventEnum.process_event_step_by_step.getCode(), msg);
    }
}
