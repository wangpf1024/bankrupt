/**
 * @filename:WorkFlowFormModelInfo 2024年6月27日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.model.domain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 实体类</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Data
public class ModelInfoVo{

	private static final long serialVersionUID = 1719473728245L;

	@ApiModelProperty(name = "name" , value = "模型名称")
	private String name;
    
	@ApiModelProperty(name = "modelInfoId" , value = "模型id")
	private String modelInfoId;
    
	@ApiModelProperty(name = "categoryId" , value = "表单分类ID")
	private Long categoryId;

	@ApiModelProperty(name = "tenantId" , value = "统标识-租户ID")
	private String tenantId;
}
