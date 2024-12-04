/**
 * @filename:ModelingFieldDef 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.openapi.domain.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GroupReqExt implements Serializable {

    @ApiModelProperty(name = "tenantId" , value = "租户/项目")
    private String tenantId;

    @ApiModelProperty(name = "uuid" , value = "分组/角色")
    private String uuid;

    @ApiModelProperty(name = "name" , value = "名称")
    protected String name;

    @ApiModelProperty(name = "type" , value = "分组类型（设计单位,施工单位,咨询单位,建设单位,监理单位）")
    protected String type;

}
