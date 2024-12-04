/**
 * @filename:WorkFlowCopy 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.center.entity;
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
 * <p>说明： 流程抄送实体类</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkFlowCopy extends Model<WorkFlowCopy> {

	private static final long serialVersionUID = 1719885428103L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "抄送主键")
	private Long id;
    
	@ApiModelProperty(name = "title" , value = "抄送标题")
	private String title;
    
	@ApiModelProperty(name = "processId" , value = "流程主键")
	private String processId;
    
	@ApiModelProperty(name = "processName" , value = "流程名称")
	private String processName;
    
	@ApiModelProperty(name = "categoryId" , value = "流程分类主键")
	private String categoryId;
    
	@ApiModelProperty(name = "deploymentId" , value = "部署主键")
	private String deploymentId;
    
	@ApiModelProperty(name = "instanceId" , value = "流程实例主键")
	private String instanceId;
    
	@ApiModelProperty(name = "taskId" , value = "任务主键")
	private String taskId;
    
	@ApiModelProperty(name = "userId" , value = "用户主键")
	private Long userId;
    
	@ApiModelProperty(name = "originatorId" , value = "发起人主键")
	private Long originatorId;
    
	@ApiModelProperty(name = "originatorName" , value = "发起人名称")
	private String originatorName;
    
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
