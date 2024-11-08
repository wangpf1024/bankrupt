/**
 * @filename:WorkFlowModelCategory 2024年10月21日
 * @project workflow  1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.entity;
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
 * @version: 1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkFlowModelCategory extends Model<WorkFlowModelCategory> {

	private static final long serialVersionUID = 1730169516425L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "分类id")
	private Long id;
    
	@ApiModelProperty(name = "categoryName" , value = "分类名称")
	private String categoryName;
    
	@ApiModelProperty(name = "code" , value = "分类编码")
	private String code;
    
	@ApiModelProperty(name = "orderNum" , value = "排序")
	private Integer orderNum;
    
	@ApiModelProperty(name = "parentId" , value = "上级分类id")
	private Long parentId;
    
	@ApiModelProperty(name = "remark" , value = "备注")
	private String remark;
    
	@ApiModelProperty(name = "openApiCode" , value = "open_api.access_name")
	private String openApiCode;
    
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
