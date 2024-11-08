package cn.bankrupt.workflow.center.msg;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.msg.WorkFlowMessageHandler;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import cn.bankrupt.workflow.model.entity.WorkFlowFormModelInfo;
import cn.bankrupt.workflow.model.entity.WorkFlowModelCategory;
import cn.bankrupt.workflow.model.entity.WorkFlowModelInfo;
import cn.bankrupt.workflow.model.enums.DeploymentStatus;
import cn.bankrupt.workflow.model.service.WorkFlowFormModelInfoService;
import cn.bankrupt.workflow.model.service.WorkFlowModelCategoryService;
import cn.bankrupt.workflow.model.service.WorkFlowModelInfoService;
import jakarta.ws.rs.core.Response;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public  class UserCreateHandlerImpl implements WorkFlowMessageHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WorkFlowModelInfoService workFlowModelInfoService;


    @Autowired
    WorkFlowModelCategoryService workFlowModelCategoryService;

    @Autowired
    WorkFlowFormModelInfoService workFlowFormModelInfoService;


    @Autowired
    ProcessEngine processEngine;

    @Autowired
    ProcessRedisCache processRedisCache;


    protected ProcessEngine getProcessEngine() {
        if (processEngine == null) {
            throw new InvalidRequestException(Response.Status.BAD_REQUEST, "No process engine available");
        }
        return processEngine;
    }


    @Override
    public void execute() {

        String objStr = processRedisCache.rightPopWithSchema(ProcessWorkFlowBaseEventEnum.user_create.getCode());
        if(StringUtils.isEmpty(objStr))return;

        TaskMsgDataDto dto = JSON.parseObject(objStr,TaskMsgDataDto.class);
        if(dto != null){

            WorkFlowModelInfo modelInfo = workFlowModelInfoService.getOne(Wrappers.<WorkFlowModelInfo>lambdaQuery()
                    .eq(WorkFlowModelInfo::getDefinitionId,dto.getProcessDefinitionId())
                    .eq(WorkFlowModelInfo::getStatus, DeploymentStatus.DEPLOYED.getCode()),false);

            if(modelInfo != null){
                WorkFlowModelCategory workFlowModelCategory = workFlowModelCategoryService.getById(modelInfo.getCategoryId());
                dto.setModelKey(modelInfo.getModelKey());
                dto.setModelName(modelInfo.getName());
                dto.setCategoryName(workFlowModelCategory.getCategoryName());
                dto.setCategoryCode(workFlowModelCategory.getCode());
                dto.setCategoryId(modelInfo.getCategoryId());
                dto.setTenantId(modelInfo.getTenantId());
                //查询其他信息
                List<WorkFlowFormModelInfo> formModelInfos = workFlowFormModelInfoService.list(Wrappers.<WorkFlowFormModelInfo>lambdaQuery()
                        .eq(WorkFlowFormModelInfo::getModelId, modelInfo.getModelId()).orderByDesc(WorkFlowFormModelInfo::getVersion));
                if (!CollectionUtils.isEmpty(formModelInfos)) {
                    WorkFlowFormModelInfo modelInfo1 = formModelInfos.get(0);
                    dto.setFormKey(modelInfo1.getFormKey());
                }
            }
            String msg = JSONUtil.toJsonStr(dto);
            //创建用户执行任务
            Long id = processRedisCache.enqueueMessage(ProcessWorkFlowBaseEventEnum.quanguocheng_channel.getCode() + dto.getEventCode(), msg);
            logger.info("UserCreateHandlerImpl quanguocheng date created: " + msg);
        }

    }
}
