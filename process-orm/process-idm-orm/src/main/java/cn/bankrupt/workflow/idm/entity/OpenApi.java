/**
 * @filename:OpenApi 2024年10月21日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.idm.entity;
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
 * <p>说明： 流程模型实体类</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OpenApi extends Model<OpenApi> {

	private static final long serialVersionUID = 1729492970261L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键")
	private Long id;
    
	@ApiModelProperty(name = "accessName" , value = "系统标示")
	private String accessName;
    
	@ApiModelProperty(name = "accessKey" , value = "密钥")
	private String accessKey;
    
	@ApiModelProperty(name = "status" , value = "状态值(0-上线 1-下线)")
	private Integer status;
    
	@ApiModelProperty(name = "remark" , value = "系统备注")
	private String remark;
    
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
