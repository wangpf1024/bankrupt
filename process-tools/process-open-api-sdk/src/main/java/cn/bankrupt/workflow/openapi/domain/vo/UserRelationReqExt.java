/**
 * @filename:ModelingFieldDef 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.openapi.domain.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRelationReqExt implements Serializable {

    @ApiModelProperty(name = "groupId" , value = "分组ID")
    protected String groupId;

    @ApiModelProperty(name = "userId" , value = "用户ID")
    protected String userId;

    @ApiModelProperty(name = "tenantId" , value = "租户/项目")
    private String tenantId;

}
