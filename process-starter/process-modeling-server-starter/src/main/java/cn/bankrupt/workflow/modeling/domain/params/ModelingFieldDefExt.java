/**
 * @filename:ModelingFieldDef 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.modeling.domain.params;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class ModelingFieldDefExt {

	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;

	@ApiModelProperty(name = "mKey" , value = "标识")
	@NotNull(message = "mKey 不能为空")
	@JsonProperty("mKey")
	private String mKey;

	@ApiModelProperty(name = "name" , value = "数据库字段/表单字段")
	private String name;

	@ApiModelProperty(name = "label" , value = "字段名/表单字段名")
	private String label;

	@ApiModelProperty(name = "remark" , value = "字段说明")
	private String remark;

	@ApiModelProperty(name = "width" , value = "字段宽度")
	private Integer width;

	@ApiModelProperty(name = "type" , value = "字段组件类型")
	private String type;

	@ApiModelProperty(name = "scheme" , value = "字段定义")
	private String scheme;

	@ApiModelProperty(name = "scope" , value = "字段范围")
	private String scope;

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
