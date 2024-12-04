/**
 * @filename:OpenApiServiceImpl 2024年10月21日
 * @project workflow  V1.0.0
 * Copyright(c) 2018 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.idm.service.impl;

import com.pansome.workflow.idm.entity.OpenApi;
import com.pansome.workflow.idm.mapper.OpenApiMapper;
import com.pansome.workflow.idm.service.OpenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p> 
 * 
 * <p>说明： 流程模型服务实现层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Service
public class OpenApiServiceImpl  extends ServiceImpl<OpenApiMapper, OpenApi> implements OpenApiService  {

    @Override
    public Integer restTenantRevision(String uuid) {
        return baseMapper.restTenantRevision(uuid);
    }

    @Override
    public Integer restGroupRevision(String uuid) {
        return baseMapper.restGroupRevision(uuid);
    }

    @Override
    public Integer restUserRevision(String uuid) {
        return baseMapper.restUserRevision(uuid);
    }

    @Override
    public Integer getTenantRevision(String uuid) {
        return baseMapper.getTenantRevision(uuid);
    }

    @Override
    public Integer getGroupRevision(String uuid) {
        return baseMapper.getGroupRevision(uuid);
    }

    @Override
    public Integer getUserRevision(String uuid) {
        return baseMapper.getUserRevision(uuid);
    }


}