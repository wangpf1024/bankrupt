package com.pansome.workflow.model.enums;

public enum DeploymentStatus {


    INIT(0, "未发布"),
    DEPLOYED(1, "已发布");

    private final Integer code;
    private final String info;

    DeploymentStatus(Integer code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public Integer getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }

}
