package com.pansome.workflow.center.msg;


import cn.hutool.json.JSONUtil;
import com.pansome.workflow.cache.ProcessRedisCache;
import com.pansome.workflow.msg.WorkFlowMessageHandler;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import com.pansome.workflow.model.service.WorkFlowModelCategoryService;
import com.pansome.workflow.model.service.WorkFlowModelInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAssignmentHandlerImpl implements WorkFlowMessageHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WorkFlowModelInfoService workFlowModelInfoService;

    @Autowired
    WorkFlowModelCategoryService workFlowModelCategoryService;

    @Autowired
    ProcessRedisCache processRedisCache;

    @Override
    public String getEventCode(){
        return  ProcessWorkFlowBaseEventEnum.user_assignment.getCode();
    }

    @Override
    public void execute(TaskMsgDataDto dto) {
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessage(ProcessWorkFlowBaseEventEnum.quanguocheng_channel.getCode(), msg);
        logger.info("UserAssignmentHandlerImpl quanguocheng date created: " + msg);
    }
}
