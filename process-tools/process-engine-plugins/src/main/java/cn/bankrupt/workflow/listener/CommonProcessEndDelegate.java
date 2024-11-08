package cn.bankrupt.workflow.listener;

import cn.hutool.json.JSONUtil;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommonProcessEndDelegate extends CommonDelegate implements JavaDelegate {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    final String  eventCode = ProcessWorkFlowBaseEventEnum.process_end.getCode();

    @Autowired
    ProcessRedisCache processRedisCache;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        TaskMsgDataDto dto = super.prams(execution);
        dto.setEventCode(eventCode);
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessageWithSchema(eventCode, msg);
        logger.info("执行流程结束ID: " + execution.getId() + " msg: " + msg);
    }
}
