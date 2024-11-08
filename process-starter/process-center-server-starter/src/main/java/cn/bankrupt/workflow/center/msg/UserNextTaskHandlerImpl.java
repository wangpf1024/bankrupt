package cn.bankrupt.workflow.center.msg;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.msg.WorkFlowMessageHandler;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public  class UserNextTaskHandlerImpl implements WorkFlowMessageHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessRedisCache processRedisCache;

    @Override
    public void execute() {
        String objStr = processRedisCache.rightPopWithSchema(ProcessWorkFlowBaseEventEnum.user_next_task.getCode());
        if(StringUtils.isEmpty(objStr))return;
        TaskMsgDataDto dto = JSON.parseObject(objStr,TaskMsgDataDto.class);
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        Long id = processRedisCache.enqueueMessage(ProcessWorkFlowBaseEventEnum.quanguocheng_channel.getCode() + dto.getEventCode(), msg);
        logger.info("UserNextTaskHandlerImpl quanguocheng date created: " + msg);
    }
}
