package cn.bankrupt.workflow.web;

/**
 * 多参数实体类
 */
public class ParameterContext {

    /** 当前登录的租户id */
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
