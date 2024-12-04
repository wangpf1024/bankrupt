package com.pansome.workflow.listener;

import cn.hutool.json.JSONUtil;
import com.pansome.workflow.cache.ProcessRedisCache;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowStatusEnum;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import com.pansome.workflow.service.ExtendCommonService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonRejectExecutionListener  extends CommonDelegate implements ExecutionListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessRedisCache processRedisCache;
    @Autowired
    List<ExtendCommonService> extendCommonServices;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        TaskMsgDataDto dto = super.prams(execution,ProcessWorkFlowBaseEventEnum.user_reject);
        dto.setProcessStatus(ProcessWorkFlowStatusEnum.BH.name());
        //累计驳回次数
        Integer counter = Integer.valueOf(1);
        if(execution.hasVariable(ProcessWorkFlowBaseEventEnum.variable_reject_times.getCode())){
            Integer i = (Integer) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_reject_times.getCode());
            ++ i;
            counter = Integer.valueOf(i);
        }
        execution.setVariable(ProcessWorkFlowBaseEventEnum.variable_reject_times.getCode(),counter);
        //获取设置参数
        for (int i = 0; i < extendCommonServices.size(); i++) {
            dto = extendCommonServices.get(i).getWorkFlowModelInfo(dto);
        }
        String msg = JSONUtil.toJsonStr(dto);
        //清空-事件审批意见描述
        boolean hasComment = execution.hasVariable(ProcessWorkFlowBaseEventEnum.variable_comment.getCode());
        if(hasComment){
            execution.removeVariable(ProcessWorkFlowBaseEventEnum.variable_comment.getCode());
        }
        processRedisCache.enqueueMessageWithSchema(ProcessWorkFlowBaseEventEnum.process_event_step_by_step.getCode(), msg);
    }

}
