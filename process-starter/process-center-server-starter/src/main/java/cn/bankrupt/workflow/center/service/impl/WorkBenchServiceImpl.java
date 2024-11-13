package cn.bankrupt.workflow.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.center.entity.WorkFlowDeployment;
import cn.bankrupt.workflow.center.enums.ModelStatusEnum;
import cn.bankrupt.workflow.center.service.WorkBenchService;
import cn.bankrupt.workflow.center.service.WorkFlowDeploymentService;
import cn.bankrupt.workflow.model.entity.WorkFlowModel;
import cn.bankrupt.workflow.model.entity.WorkFlowModelInfo;
import cn.bankrupt.workflow.model.service.WorkFlowModelInfoService;
import cn.bankrupt.workflow.model.service.WorkFlowModelService;
import jakarta.ws.rs.core.Response;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WorkBenchServiceImpl implements WorkBenchService {


    @Autowired
    ProcessEngine processEngine;

    protected ProcessEngine getProcessEngine() {
        if (processEngine == null) {
            throw new InvalidRequestException(Response.Status.BAD_REQUEST, "No process engine available");
        }
        return processEngine;
    }


}
