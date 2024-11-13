package cn.bankrupt.workflow.service.impl;

import cn.bankrupt.workflow.service.ExtendCommonService;
import cn.hutool.core.util.StrUtil;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExtendCommonServiceImpl implements ExtendCommonService {

    @Autowired
    ProcessRedisCache processRedisCache;

    @Override
    public TaskMsgDataDto getWorkFlowModelInfo(TaskMsgDataDto dto) {
        String key = String.format(ProcessWorkFlowBaseEventEnum.process_deployed_values.getCode(),dto.getProcessDefinitionId());
        String functionRange = processRedisCache.getCacheMapValue(key,ProcessWorkFlowBaseEventEnum.function_range_skip_pass.name());
        if(StrUtil.isNotEmpty(functionRange)){
            dto.setFunctionRange(functionRange);
        }
        return dto;
    }
}
