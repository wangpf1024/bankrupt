/**
 * @filename:WorkFlowHiComment 2024年7月2日
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
 * <p>说明： 审批意见实体类</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkFlowHiComment extends Model<WorkFlowHiComment> {

	private static final long serialVersionUID = 1719885588978L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键")
	private Integer id;
    
	@ApiModelProperty(name = "type" , value = "类型")
	private String type;
    
	@ApiModelProperty(name = "userId" , value = "员工工号")
	private String userId;
    
	@ApiModelProperty(name = "userName" , value = "员工姓名")
	private String userName;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "actionTime" , value = "审批时间")
	private Date actionTime;
    
	@ApiModelProperty(name = "taskId" , value = "任务ID")
	private String taskId;
    
	@ApiModelProperty(name = "taskName" , value = "任务名称")
	private String taskName;
    
	@ApiModelProperty(name = "activityId" , value = "活动节点ID")
	private String activityId;
    
	@ApiModelProperty(name = "activityName" , value = "活动节点名称")
	private String activityName;
    
	@ApiModelProperty(name = "deployId" , value = "流程实例id")
	private String deployId;
    
	@ApiModelProperty(name = "actionCode" , value = "动作")
	private String actionCode;
    
	@ApiModelProperty(name = "message" , value = "审批意见")
	private String message;
    
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
