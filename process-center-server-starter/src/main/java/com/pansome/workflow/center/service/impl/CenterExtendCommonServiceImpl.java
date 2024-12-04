package com.pansome.workflow.center.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pansome.workflow.cache.ProcessRedisCache;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import com.pansome.workflow.model.entity.WorkFlowModelCategory;
import com.pansome.workflow.model.entity.WorkFlowModelInfo;
import com.pansome.workflow.model.enums.DeploymentStatus;
import com.pansome.workflow.model.service.WorkFlowModelCategoryService;
import com.pansome.workflow.model.service.WorkFlowModelInfoService;
import com.pansome.workflow.service.ExtendCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CenterExtendCommonServiceImpl implements ExtendCommonService, InitializingBean {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WorkFlowModelInfoService workFlowModelInfoService;

    @Autowired
    WorkFlowModelCategoryService workFlowModelCategoryService;


    @Autowired
    ProcessRedisCache processRedisCache;


    //初始化基础数据
    @Override
    public void initProcessValues(){
        List<WorkFlowModelInfo> modelInfo = workFlowModelInfoService.list(Wrappers.<WorkFlowModelInfo>lambdaQuery()
                .eq(WorkFlowModelInfo::getStatus, DeploymentStatus.DEPLOYED.getCode()));
        //设置缓存
        modelInfo.forEach( i-> {
            String key = String.format(ProcessWorkFlowBaseEventEnum.process_deployed_values.getCode(),i.getDefinitionId());
            processRedisCache.setCacheMapValue(key,ProcessWorkFlowBaseEventEnum.function_range_skip_pass.name(),i.getFunctionRange());
            processRedisCache.setCacheMapValue(key,ProcessWorkFlowBaseEventEnum.model_name.getCode(),i.getName());
            if(i.getCategoryId() != null){
                WorkFlowModelCategory category = workFlowModelCategoryService.getById(i.getCategoryId());
                processRedisCache.setCacheMapValue(key,ProcessWorkFlowBaseEventEnum.category_name.getCode(),category.getCategoryName());
            }
        });
    }

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

    @Override
    public void afterPropertiesSet() throws Exception {
       initProcessValues();
    }

}
