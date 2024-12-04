/**
 * @filename:WorkFlowService 2024年11月29日
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
 * <p>说明： 流程执行服务实体类</P>
 * @version: 1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkFlowService extends Model<WorkFlowService> {

	private static final long serialVersionUID = 1732863867813L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "服务id")
	private Integer id;
    
	@ApiModelProperty(name = "serviceName" , value = "服务名称")
	private String serviceName;
    
	@ApiModelProperty(name = "serviceCode" , value = "服务编码")
	private String serviceCode;
    
	@ApiModelProperty(name = "iconUrl" , value = "图标图片")
	private String iconUrl;
    
	@ApiModelProperty(name = "type" , value = "服务类型，1：内部表单服务；2，外部调用服务")
	private Integer type;
    
	@ApiModelProperty(name = "status" , value = "是否发布，0：不发布，1：发布")
	private Integer status;
    
	@ApiModelProperty(name = "openWay" , value = "打开方式,0-当前页面,1-新窗口打开")
	private Integer openWay;
    
	@ApiModelProperty(name = "orderNum" , value = "排序")
	private Integer orderNum;
    
	@ApiModelProperty(name = "remark" , value = "备注")
	private String remark;
    
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
    
	@ApiModelProperty(name = "applyCompanyId" , value = "适用租户")
	private String applyCompanyId;
    
	@ApiModelProperty(name = "applyProjectId" , value = "适用项目")
	private String applyProjectId;
    
	@ApiModelProperty(name = "modelInfoId" , value = "流程信息ID")
	private Long modelInfoId;
    
	@ApiModelProperty(name = "definitionName" , value = "流程定义名称")
	private String definitionName;
    

}
