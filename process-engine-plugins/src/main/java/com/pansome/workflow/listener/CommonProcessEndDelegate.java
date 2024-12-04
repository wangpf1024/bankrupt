package com.pansome.workflow.listener;

import cn.hutool.json.JSONUtil;
import com.pansome.workflow.cache.ProcessRedisCache;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import com.pansome.workflow.service.ExtendCommonService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CommonProcessEndDelegate extends CommonDelegate implements JavaDelegate {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessRedisCache processRedisCache;

    @Autowired
    List<ExtendCommonService> extendCommonServices;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        TaskMsgDataDto dto = super.prams(execution,ProcessWorkFlowBaseEventEnum.process_end);
        //获取设置参数
        for (int i = 0; i < extendCommonServices.size(); i++) {
            dto = extendCommonServices.get(i).getWorkFlowModelInfo(dto);
        }
        String msg = JSONUtil.toJsonStr(dto);
        logger.info("event : "+ dto.getEventCode()  + "  dto   " + msg);
        //创建用户执行任务
        processRedisCache.enqueueMessageWithSchema(ProcessWorkFlowBaseEventEnum.process_event_step_by_step.getCode(), msg);
    }
}
