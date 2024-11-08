/**
 * @filename:WorkFlowModel 2024年7月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.bankrupt.workflow.utils.HutoolStringDeserializer;
import cn.bankrupt.workflow.utils.HutoolStringSerializer;
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
public class WorkFlowModel extends Model<WorkFlowModel> {

	private static final long serialVersionUID = 1722063339084L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键")
	private Long id;
    
	@ApiModelProperty(name = "processDefId" , value = "流程定义id")
	private String processDefId;
    
	@ApiModelProperty(name = "modelKey" , value = "模型Key")
	private String modelKey;
    
	@ApiModelProperty(name = "modelName" , value = "模型名称")
	private String modelName;
    
	@ApiModelProperty(name = "modelJson" , value = "流程json数据")
	@JsonDeserialize(using = HutoolStringDeserializer.class)
	@JsonSerialize(using = HutoolStringSerializer.class)
	private String modelJson;
    
	@ApiModelProperty(name = "modelXml" , value = "流程xml数据")
	private String modelXml;
    
	@ApiModelProperty(name = "fileName" , value = "文件名")
	private String fileName;
    
	@ApiModelProperty(name = "version" , value = "版本")
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
