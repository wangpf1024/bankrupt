package com.pansome.workflow.enums.workflow;

import java.io.Serializable;


public enum ProcessWorkFlowDeployStatusEnum implements Serializable {

    undeploy(0),
    deployed(1);

    private Integer status;

    ProcessWorkFlowDeployStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

	