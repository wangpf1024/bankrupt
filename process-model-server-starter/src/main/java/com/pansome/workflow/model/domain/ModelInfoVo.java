/**
 * @filename:WorkFlowFormModelInfo 2024年6月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.model.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 实体类</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
public class ModelInfoVo{

	private static final long serialVersionUID = 1719473728245L;

	@ApiModelProperty(name = "name" , value = "模型名称")
	private String name;
    
	@ApiModelProperty(name = "modelInfoId" , value = "模型id")
	private String modelInfoId;
    
	@ApiModelProperty(name = "categoryId" , value = "表单分类ID")
	private Long categoryId;

	@ApiModelProperty(name = "tenantId" , value = "统标识-租户ID")
	private String tenantId;

	@ApiModelProperty(name = "applyCompanyId" , value = "适用租户")
	private String applyCompanyId;

	@ApiModelProperty(name = "applyProjectId" , value = "适用项目")
	private String applyProjectId;


	@ApiModelProperty(name = "createBy" , value = "创建者")
	private String createBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间")
	private Date createTime;

}
