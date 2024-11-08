package cn.bankrupt.workflow.center.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class VariableVo {

    String id;

    String name;

    String procDefKey;

    String vtype;

    String vlong;

    String value;

    Date createTime;

    String seq;

    String processInstanceId;
}
