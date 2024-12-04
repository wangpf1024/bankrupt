package com.pansome.workflow.service.impl;

import cn.hutool.core.util.StrUtil;
import com.pansome.workflow.cache.ProcessRedisCache;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import com.pansome.workflow.service.ExtendCommonService;
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
        String name = processRedisCache.getCacheMapValue(key,ProcessWorkFlowBaseEventEnum.model_name.getCode());
        if(StrUtil.isNotEmpty(name)){
            dto.setModelName(name);
        }
        String categoryName = processRedisCache.getCacheMapValue(key,ProcessWorkFlowBaseEventEnum.category_name.getCode());
        if(StrUtil.isNotEmpty(categoryName)){
            dto.setEventSource(categoryName);
        }
        return dto;
    }
}
