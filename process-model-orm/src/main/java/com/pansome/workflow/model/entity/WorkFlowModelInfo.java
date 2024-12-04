/**
 * @filename:WorkFlowModelInfo 2024年10月30日
 * @project workflow  1.0.0
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
 * <p>说明： 流程模型实体类</P>
 * @version: 1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkFlowModelInfo extends Model<WorkFlowModelInfo> {

	private static final long serialVersionUID = 1730272349791L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键")
	private Long id;
    
	@ApiModelProperty(name = "modelId" , value = "模型id")
	private String modelId;
    
	@ApiModelProperty(name = "name" , value = "模型名称")
	private String name;
    
	@ApiModelProperty(name = "modelKey" , value = "模型key")
	private String modelKey;
    
	@ApiModelProperty(name = "modelType" , value = "模型类型: 0 自定义流程 1 是业务流程")
	private Integer modelType;
    
	@ApiModelProperty(name = "categoryId" , value = "分类id")
	private Long categoryId;
    
	@ApiModelProperty(name = "status" , value = "流程图Model状态")
	private Integer status;
    
	@ApiModelProperty(name = "applyCompanyId" , value = "适用公司(多个公司，以逗号隔开)")
	private String applyCompanyId;
    
	@ApiModelProperty(name = "applyProjectId" , value = "适用项目(多个项目，以逗号隔开)")
	private String applyProjectId;
    
	@ApiModelProperty(name = "functionRange" , value = "功能范围( 0 默认， 1 允许跳过已通过)")
	private String functionRange;
    
	@ApiModelProperty(name = "orderNum" , value = "排序")
	private Integer orderNum;
    
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
    
	@ApiModelProperty(name = "definitionId" , value = "流程定义ID")
	private String definitionId;
    
	@ApiModelProperty(name = "definitionKey" , value = "流程KEY")
	private String definitionKey;
    
	@ApiModelProperty(name = "deploymentId" , value = "流程部署ID")
	private String deploymentId;
    

}
