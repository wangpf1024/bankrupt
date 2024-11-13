package cn.bankrupt.workflow.idm.service;


import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.openapi.domain.vo.GroupReqExt;
import cn.bankrupt.workflow.openapi.domain.vo.TenantReqExt;
import cn.bankrupt.workflow.openapi.domain.vo.UserRelationReqExt;
import cn.bankrupt.workflow.openapi.domain.vo.UserReqExt;

import java.util.List;

public interface OpenApiUserService {

    /**
     * 新增租户
     * @param param
     * @return
     */
    ResultBean<String> addTenant(TenantReqExt param);

    /**
     * 新增用户
     * @param param
     * @return
     */
    ResultBean<String> addUser(UserReqExt param);

    /**
     * 新增分组
     * @param param
     * @return
     */
    ResultBean<String> addGroup(GroupReqExt param);

    /**
     * 分组用户新增
     * @param param
     * @return
     */
    ResultBean<String> addUserGroup(UserRelationReqExt param);

    /**
     * 批量分组用户新增
     * @param param
     * @return
     */
    ResultBean<String> addBatchUserGroup(List<UserRelationReqExt> param);
}
