package cn.bankrupt.workflow.center.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import cn.bankrupt.workflow.model.entity.WorkFlowModelInfo;
import cn.bankrupt.workflow.model.enums.DeploymentStatus;
import cn.bankrupt.workflow.model.service.WorkFlowModelInfoService;
import cn.bankrupt.workflow.service.ExtendCommonService;
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
    ProcessRedisCache processRedisCache;


    //初始化基础数据
    @Override
    public void initProcessValues(){
        List<WorkFlowModelInfo> modelInfo = workFlowModelInfoService.list(Wrappers.<WorkFlowModelInfo>lambdaQuery()
                .eq(WorkFlowModelInfo::getStatus, DeploymentStatus.DEPLOYED.getCode()));
        //设置缓存
        modelInfo.forEach( i-> {
            String key = String.format(ProcessWorkFlowBaseEventEnum.process_deployed_values.getCode(),i.getDefinitionId());
            String hkey = ProcessWorkFlowBaseEventEnum.function_range_skip_pass.name();
            processRedisCache.setCacheMapValue(key,hkey,i.getFunctionRange());
            logger.info("init model data key:"+key);
            logger.info("init model data hkey:"+hkey);
        });
    }

    @Override
    public TaskMsgDataDto getWorkFlowModelInfo(TaskMsgDataDto dto) {
        String key = String.format(ProcessWorkFlowBaseEventEnum.process_deployed_values.getCode(),dto.getProcessDefinitionId());
        String functionRange = processRedisCache.getCacheMapValue(key,ProcessWorkFlowBaseEventEnum.function_range_skip_pass.name());
        if(StrUtil.isNotEmpty(functionRange)){
            dto.setFunctionRange(functionRange);
        }
        return dto;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       initProcessValues();
    }

}
