package com.pansome.workflow.center.msg;


import cn.hutool.json.JSONUtil;
import com.pansome.workflow.cache.ProcessRedisCache;
import com.pansome.workflow.msg.WorkFlowMessageHandler;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public  class UserNextTaskHandlerImpl implements WorkFlowMessageHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessRedisCache processRedisCache;

    @Override
    public String getEventCode(){
        return  ProcessWorkFlowBaseEventEnum.user_next_task.getCode();
    }

    @Override
    public void execute(TaskMsgDataDto dto) {
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessage(ProcessWorkFlowBaseEventEnum.quanguocheng_channel.getCode(), msg);
        logger.info("UserNextTaskHandlerImpl quanguocheng date created: " + msg);
    }
}
