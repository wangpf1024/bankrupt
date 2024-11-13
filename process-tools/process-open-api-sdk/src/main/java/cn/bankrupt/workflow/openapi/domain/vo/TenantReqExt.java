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
public class TenantReqExt implements Serializable {

    @ApiModelProperty(name = "uuid" , value = "uuid")
    private String uuid;

    @ApiModelProperty(name = "name" , value = "名称")
    private String name;

}
