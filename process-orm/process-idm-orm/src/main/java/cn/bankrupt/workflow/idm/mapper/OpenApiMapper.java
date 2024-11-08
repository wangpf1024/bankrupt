/**
 * @filename:OpenApiMapper 2024年10月21日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.idm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.bankrupt.workflow.idm.entity.OpenApi;
import org.apache.ibatis.annotations.Param;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程模型数据访问层</p>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Mapper
public interface OpenApiMapper extends BaseMapper<OpenApi> {

    Integer restTenantRevision(@Param("uuid") String uuid);

    Integer restGroupRevision(@Param("uuid") String uuid);

    Integer restUserRevision(@Param("uuid") String uuid);

    Integer getTenantRevision(@Param("uuid") String uuid);

    Integer getGroupRevision(@Param("uuid") String uuid);

    Integer getUserRevision(@Param("uuid") String uuid);
}
