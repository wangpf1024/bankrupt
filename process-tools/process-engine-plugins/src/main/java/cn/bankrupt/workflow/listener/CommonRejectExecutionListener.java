package cn.bankrupt.workflow.listener;

import cn.hutool.json.JSONUtil;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonRejectExecutionListener  extends CommonDelegate implements ExecutionListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    final String  eventCode = ProcessWorkFlowBaseEventEnum.user_reject.getCode();

    @Autowired
    ProcessRedisCache processRedisCache;


    @Override
    public void notify(DelegateExecution execution) throws Exception {
        TaskMsgDataDto dto = super.prams(execution);
        dto.setEventCode(eventCode);
        String msg = JSONUtil.toJsonStr(dto);
        //设置发起人
        dto.setStartBy((String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_apply.getCode()));
        processRedisCache.enqueueMessageWithSchema(ProcessWorkFlowBaseEventEnum.process_event_step_by_step.getCode(), msg);
        logger.info("event : "+dto.getEventCode() + " task push msg " + msg);
    }

}
