/**
 * @filename:ModelingFieldDef 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.modeling.domain.params;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelingEntityExt implements Serializable {

	@ApiModelProperty(name = "mKey" , value = "标识")
	@NotNull(message = "mKey 不能为空")
	@JsonProperty("mKey")
	private String mKey;

	@ApiModelProperty(name = "name" , value = "业务实体名称")
	private String name;

	@ApiModelProperty(name = "remark" , value = "实体说明")
	private String remark;

	@ApiModelProperty(name = "status" , value = "状态")
	private Integer status;

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
}
