package cn.bankrupt.workflow.center.msg;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.msg.WorkFlowMessageHandler;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import cn.bankrupt.workflow.model.entity.WorkFlowModelCategory;
import cn.bankrupt.workflow.model.entity.WorkFlowModelInfo;
import cn.bankrupt.workflow.model.enums.DeploymentStatus;
import cn.bankrupt.workflow.model.service.WorkFlowModelCategoryService;
import cn.bankrupt.workflow.model.service.WorkFlowModelInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserAssignmentHandlerImpl implements WorkFlowMessageHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WorkFlowModelInfoService workFlowModelInfoService;

    @Autowired
    WorkFlowModelCategoryService workFlowModelCategoryService;

    @Autowired
    ProcessRedisCache processRedisCache;

    @Override
    public String getEventCode(){
        return  ProcessWorkFlowBaseEventEnum.user_assignment.getCode();
    }

    @Override
    public void execute(TaskMsgDataDto dto) {
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessage(ProcessWorkFlowBaseEventEnum.quanguocheng_channel.getCode() + dto.getEventCode(), msg);
        logger.info("UserAssignmentHandlerImpl quanguocheng date created: " + msg);
    }
}
