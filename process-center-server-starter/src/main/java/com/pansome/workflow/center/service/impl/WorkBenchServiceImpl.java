package com.pansome.workflow.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pansome.workflow.ResultBean;
import com.pansome.workflow.center.entity.WorkFlowDeployment;
import com.pansome.workflow.center.enums.ModelStatusEnum;
import com.pansome.workflow.center.service.WorkBenchService;
import com.pansome.workflow.center.service.WorkFlowDeploymentService;
import com.pansome.workflow.model.entity.WorkFlowModel;
import com.pansome.workflow.model.entity.WorkFlowModelInfo;
import com.pansome.workflow.model.service.WorkFlowModelInfoService;
import com.pansome.workflow.model.service.WorkFlowModelService;
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
