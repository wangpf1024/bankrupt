package cn.bankrupt.workflow.model.enums;

public enum ModelType {


    INSIDER(0, "自定义流程"),
    OUTER(1, "是业务流程");

    private final Integer code;
    private final String info;

    ModelType(Integer code, String info)
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
