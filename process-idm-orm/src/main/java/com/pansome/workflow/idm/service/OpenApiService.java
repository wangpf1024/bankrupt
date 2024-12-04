/**
 * @filename:OpenApiService 2024年10月21日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.idm.service;

import com.pansome.workflow.idm.entity.OpenApi;
import com.baomidou.mybatisplus.extension.service.IService;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程模型服务层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
public interface OpenApiService extends IService<OpenApi> {

    /**
     * 重置租户 Revision
     * @param uuid
     * @return
     */
    Integer restTenantRevision(String uuid);

    /**
     * 重置分组 Revision
     * @param uuid
     * @return
     */
    Integer restGroupRevision(String uuid);


    /**
     * 重置用户 Revision
     * @param uuid
     * @return
     */
    Integer restUserRevision(String uuid);

    /**
     * 重置租户 Revision
     * @param uuid
     * @return
     */
    Integer getTenantRevision(String uuid);

    /**
     * 重置分组 Revision
     * @param uuid
     * @return
     */
    Integer getGroupRevision(String uuid);


    /**
     * 重置用户 Revision
     * @param uuid
     * @return
     */
    Integer getUserRevision(String uuid);

}