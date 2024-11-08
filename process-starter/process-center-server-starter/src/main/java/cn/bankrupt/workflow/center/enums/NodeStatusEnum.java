package cn.bankrupt.workflow.center.enums;

import java.io.Serializable;


public enum NodeStatusEnum implements Serializable {

    PENDING("pending", "待处理"),
    PROCESSING("processing", "处理中"),
    FINISH("finish", "已处理");

    // 类型
    private String type;
    // 描述
    private String description;

    private NodeStatusEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
