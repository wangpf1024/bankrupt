package com.pansome.workflow.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * open api 登录对象
 * 
 * @author ruoyi
 */
@Data
public class OpenApiBody
{
    @ApiModelProperty(name = "accessName" , value = "系统标示")
    private String accessName;

    @ApiModelProperty(name = "accessKey" , value = "密钥")
    private String accessKey;
}
