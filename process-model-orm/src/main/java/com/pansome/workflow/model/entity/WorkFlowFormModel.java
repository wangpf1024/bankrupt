/**
 * @filename:WorkFlowFormModel 2024年6月27日
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
 * <p>说明： 表单模型实体类</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkFlowFormModel extends Model<WorkFlowFormModel> {

	private static final long serialVersionUID = 1719474517263L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private Long id;
    
	@ApiModelProperty(name = "formKey" , value = "表单唯一标识Key：用于关联流程")
	private String formKey;
    
	@ApiModelProperty(name = "formJson" , value = "表单配置json")
	private String formJson;
    
	@ApiModelProperty(name = "ruleJson" , value = "表单规则配置json")
	private String ruleJson;
    
	@ApiModelProperty(name = "optionJson" , value = "表单选项配置json")
	private String optionJson;
    
	@ApiModelProperty(name = "formType" , value = "状态值-0：pc表单 1：移动端表单")
	private Integer formType;
    
	@ApiModelProperty(name = "status" , value = "状态1-未发布，2-已发布")
	private String status;
    
	@ApiModelProperty(name = "mainVersion" , value = "是否为主版本")
	private Integer mainVersion;
    
	@ApiModelProperty(name = "configJson" , value = "表单表头字段配置")
	private String configJson;
    
	@ApiModelProperty(name = "releaseNote" , value = "版本说明")
	private String releaseNote;
    
	@ApiModelProperty(name = "version" , value = "版本号")
	private Integer version;
    
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
