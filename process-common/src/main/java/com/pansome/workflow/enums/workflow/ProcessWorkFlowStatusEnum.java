package com.pansome.workflow.enums.workflow;

import java.io.Serializable;


public enum ProcessWorkFlowStatusEnum implements Serializable {

    SPZ("流转中"),
    BH("驳回"),
    CH("撤回流程"),
    WP("委派"),
    JQ("加签"),
    ZC("暂存"),
    ZB("转办"),
    CX("撤销"),
    BJ("办结"),
    QZJS("强制结束"),
    ZZ("中止");

    private String msg;

    ProcessWorkFlowStatusEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

	