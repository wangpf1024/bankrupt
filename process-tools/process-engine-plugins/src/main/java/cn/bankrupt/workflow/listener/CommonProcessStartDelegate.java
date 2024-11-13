package cn.bankrupt.workflow.listener;

import cn.bankrupt.workflow.service.ExtendCommonService;
import cn.hutool.core.util.StrUtil;
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

import java.util.List;


@Component
public class CommonProcessStartDelegate extends CommonDelegate implements JavaDelegate {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    final String  eventCode = ProcessWorkFlowBaseEventEnum.process_start.getCode();

    @Autowired
    ProcessRedisCache processRedisCache;

    @Autowired
    List<ExtendCommonService> extendCommonServices;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //重新初始化参数
        TaskMsgDataDto dto = super.prams(execution);
        //设置发起人
        dto.setStartBy((String) execution.getVariable(ProcessWorkFlowBaseEventEnum.variable_apply.getCode()));
        for (int i = 0; i < extendCommonServices.size(); i++) {
            extendCommonServices.get(i).initProcessValues();
        }
        //获取设置参数
        for (int i = 0; i < extendCommonServices.size(); i++) {
            dto = extendCommonServices.get(i).getWorkFlowModelInfo(dto);
        }
        //处理 功能范围(1 跳过已通过)
        boolean skip = (StrUtil.isNotEmpty(dto.getFunctionRange())
                && ProcessWorkFlowBaseEventEnum.function_range_skip_pass.getCode()
                .equals(dto.getFunctionRange())) ? true : false;
        execution.setVariable(ProcessWorkFlowBaseEventEnum.function_range_skip_pass.name(), skip);

        dto.setEventCode(eventCode);
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessageWithSchema(ProcessWorkFlowBaseEventEnum.process_event_step_by_step.getCode(), msg);
        logger.info("执行流程开始ID: " + execution.getId() + " msg: " + msg);
    }
}
