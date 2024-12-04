/**
 * @filename:ModelingFieldDefMapper 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.modeling.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pansome.workflow.modeling.entity.ModelingEntityField;
import com.pansome.workflow.modeling.entity.ModelingFieldDef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**   
 * <p>字段定义扩展：</p>
 * 
 * <p>说明： 字段定义数据访问层</p>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Mapper
public interface ModelingEntityFieldExtMapper extends BaseMapper{

    List<ModelingFieldDef> findModelFields(@Param("mKey") String mKey);

}
