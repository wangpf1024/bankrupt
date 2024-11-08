/**
 * @filename:WorkFlowDeployment 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.center.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程部署实体类</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkFlowDeployment extends Model<WorkFlowDeployment> {

	private static final long serialVersionUID = 1719884084250L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "自增主键")
	private Long id;
    
	@ApiModelProperty(name = "processDefId" , value = "流程定义id")
	private String processDefId;
    
	@ApiModelProperty(name = "flowDeployId" , value = "流程模型部署id")
	private String flowDeployId;
    
	@ApiModelProperty(name = "flowName" , value = "流程名称")
	private String flowName;
    
	@ApiModelProperty(name = "flowKey" , value = "流程业务标识")
	private String flowKey;
    
	@ApiModelProperty(name = "status" , value = "状态(1.已部署 3.已下线)")
	private Integer status;
    
	@ApiModelProperty(name = "remark" , value = "备注")
	private String remark;
    
	@ApiModelProperty(name = "archive" , value = "归档状态(0未删除，1删除)")
	private Integer archive;
    
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
    
	@ApiModelProperty(name = "tenantId" , value = "系统标识-租户ID")
	private String tenantId;
    

}
