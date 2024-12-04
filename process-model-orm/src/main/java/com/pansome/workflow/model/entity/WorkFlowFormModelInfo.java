/**
 * @filename:WorkFlowFormModelInfo 2024年6月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.model.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 表单信息实体类</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkFlowFormModelInfo extends Model<WorkFlowFormModelInfo> {

	private static final long serialVersionUID = 1719473728245L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "表单模型信息主键")
	private Long id;
    
	@ApiModelProperty(name = "modelId" , value = "模型id")
	private String modelId;
    
	@ApiModelProperty(name = "categoryId" , value = "表单分类ID")
	private Long categoryId;
    
	@ApiModelProperty(name = "categoryName" , value = "表单分类名称")
	private String categoryName;
    
	@ApiModelProperty(name = "formName" , value = "表单名称")
	private String formName;
    
	@ApiModelProperty(name = "formKey" , value = "主版本formKey=》用于关联工作流")
	private String formKey;
    
	@ApiModelProperty(name = "version" , value = "主版本版本号")
	private Integer version;
    
	@ApiModelProperty(name = "formModelType" , value = "默认：0-外部表单 1-自定义表单")
	private Integer formModelType;
    
	@ApiModelProperty(name = "tenantId" , value = "统标识-租户ID")
	private String tenantId;
    
	@ApiModelProperty(name = "createBy" , value = "创建者")
	private String createBy;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间")
	private Date createTime;
    
	@ApiModelProperty(name = "updateBy" , value = "更新者")
	private String updateBy;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "更新时间")
	private Date updateTime;
    
	@ApiModelProperty(name = "isDeleted" , value = "删除标志（0代表存在 1代表删除）")
	private Integer isDeleted;
    

}
