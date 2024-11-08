/**
 * @filename:ModelingFieldDef 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.openapi.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserReqExt implements Serializable {

    @ApiModelProperty(name = "tenantId" , value = "租户/项目")
    private String tenantId;

    @ApiModelProperty(name = "groupId" , value = "分组/角色")
    private String groupId;

    @ApiModelProperty(name = "uuid" , value = "uuid")
    private String uuid;

    @ApiModelProperty(name = "firstName" , value = "名")
    protected String firstName;

    @ApiModelProperty(name = "lastName" , value = "姓")
    protected String lastName;

    @ApiModelProperty(name = "email" , value = "邮箱")
    protected String email;

}
