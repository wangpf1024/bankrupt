/**
 * @filename:ModelingEntityDefMapper 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.modeling.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pansome.workflow.modeling.entity.ModelingEntityDef;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**   
 * <p> 实体定义扩展：</p>
 * 
 * <p>说明： 实体定义数据访问层</p>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Mapper
public interface ModelingEntityDefExtMapper extends BaseMapper{

    void createTable(@Param("createDDL") String createDDL);

    void createFormTableColumn(@Param("alterDDL") String alterDDL);

    void modifyFormTableColumn(@Param("modifyDDL")String modifyDDL);

    void dropFormTableColumn(@Param("dropDDL")String dropDDL);

    void insertDML(@Param("insertDML")String sql);
}
