package cn.bankrupt.workflow.center.service.impl;

import cn.bankrupt.workflow.center.service.WorkBenchService;
import jakarta.ws.rs.core.Response;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
