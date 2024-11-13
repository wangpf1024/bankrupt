package cn.bankrupt.workflow.center.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.enums.BaseExceptionEnum;
import cn.bankrupt.workflow.idm.entity.WorkFlowTenant;
import cn.bankrupt.workflow.idm.service.*;
import cn.bankrupt.workflow.openapi.domain.vo.GroupReqExt;
import cn.bankrupt.workflow.openapi.domain.vo.TenantReqExt;
import cn.bankrupt.workflow.openapi.domain.vo.UserRelationReqExt;
import cn.bankrupt.workflow.openapi.domain.vo.UserReqExt;
import jakarta.ws.rs.core.Response;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TenantEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OpenApiUserServiceImpl implements OpenApiUserService {

    @Value("camunda.user.password:80T0hAefOvD")
    String defaultPassWord;

    /**
     * 检查字符串是否是有效的MD5哈希
     * @param input 输入字符串
     * @return 如果是有效的MD5哈希则返回true，否则返回false
     */
    public static boolean isValidMD5(String input) {
        if (input == null || input.length() != 32) {
            return false;
        }
        // 使用正则表达式检查是否只包含16进制字符
        return input.matches("^[a-fA-F0-9]{32}$");
    }

    @Autowired
    WorkFlowTenantService workFlowTenantService;

    @Autowired
    OpenApiService openApiService;

    @Autowired
    ProcessEngine processEngine;

    protected ProcessEngine getProcessEngine() {
        if (processEngine == null) {
            throw new InvalidRequestException(Response.Status.BAD_REQUEST, "No process engine available");
        }
        return processEngine;
    }

    @Override
    public ResultBean<String> addTenant(TenantReqExt param) {
        if(StrUtil.isEmpty(param.getUuid())){
            return ResultBean.ofSuccess("UUID不可为空");
        }
        if(!isValidMD5(param.getUuid())){
            return ResultBean.ofSuccess("UUID格式错误,必须是MD5字符串");
        }
        WorkFlowTenant workFlowTenant = new WorkFlowTenant();
        workFlowTenant.setTenantId(param.getUuid());
        workFlowTenant.setTenantName(param.getName());
        TenantEntity entity = new TenantEntity();
        entity.setId(workFlowTenant.getTenantId());
        entity.setName(workFlowTenant.getTenantName());
        IdentityService identityService = getProcessEngine().getIdentityService();
        WorkFlowTenant tenant = workFlowTenantService.getOne(
                Wrappers.<WorkFlowTenant>lambdaQuery().eq(WorkFlowTenant::getTenantId, param.getUuid()));
        if(tenant != null){
            entity.setRevision(openApiService.getTenantRevision(param.getUuid()));
            workFlowTenantService.update(workFlowTenant,
                    Wrappers.<WorkFlowTenant>lambdaQuery().eq(WorkFlowTenant::getTenantId, param.getUuid()));
        }else{
            workFlowTenantService.save(workFlowTenant);
        }
        identityService.saveTenant(entity);
        return ResultBean.ofSuccess(entity.getId());
    }

    @Override
    public ResultBean<String> addUser(UserReqExt param) {
        if(StrUtil.isEmpty(param.getUuid())){
            return ResultBean.ofSuccess("UUID不可为空");
        }
        if(!isValidMD5(param.getUuid())){
            return ResultBean.ofSuccess("UUID格式错误");
        }
        WorkFlowTenant tenant = workFlowTenantService.getOne(
                Wrappers.<WorkFlowTenant>lambdaQuery().eq(WorkFlowTenant::getTenantId, param.getTenantId()));
        if(tenant == null){
            return ResultBean.ofSuccess("租户/项目",BaseExceptionEnum.EC404);
        }
        IdentityService identityService = getProcessEngine().getIdentityService();
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(param,entity);
        entity.setId(param.getUuid());
        entity.setPassword(defaultPassWord);
        entity.setFirstName(param.getFirstName());
        entity.setLastName(param.getLastName());
        entity.setEmail(param.getEmail());
        User user = identityService.createUserQuery()
                .userId(param.getUuid())
                .singleResult();
        if(user != null){
            entity.setRevision(openApiService.getUserRevision(param.getUuid()));
        }
        identityService.saveUser(entity);
        //分组不为空
        if(param.getGroupId() != null){
            Tenant tenantGroup = identityService.createTenantQuery()
                    .tenantId(param.getTenantId())
                    .groupMember(param.getGroupId()).singleResult();
            if(tenantGroup == null){
                identityService.createTenantGroupMembership(param.getTenantId(),param.getGroupId());
            }
            Group group = identityService.createGroupQuery()
                    .groupId(param.getGroupId())
                    .groupMember(param.getUuid()).singleResult();
            if(group == null){
                identityService.createMembership(param.getUuid(),param.getGroupId());
            }
        }
        Tenant tenantUser = identityService.createTenantQuery()
                .tenantId(param.getTenantId())
                .userMember(param.getUuid()).singleResult();
        if(tenantUser == null){
            identityService.createTenantUserMembership(param.getTenantId(),param.getUuid());
        }
        openApiService.restUserRevision(param.getUuid());
        return ResultBean.ofSuccess(entity.getId());
    }

    @Override
    public ResultBean<String> addGroup(GroupReqExt param) {
        if(StrUtil.isEmpty(param.getUuid())){
            return ResultBean.ofSuccess("UUID不可为空");
        }

        if(!isValidMD5(param.getUuid())){
            return ResultBean.ofSuccess("UUID格式错误");
        }

        WorkFlowTenant workFlowTenant = workFlowTenantService.getOne(Wrappers.<WorkFlowTenant>lambdaQuery().eq(WorkFlowTenant::getTenantId,
                param.getTenantId()));
        if(workFlowTenant == null){
            return ResultBean.ofSuccess("租户/项目",BaseExceptionEnum.EC404);
        }

        GroupEntity entity = new GroupEntity();
        entity.setId(param.getUuid());
        entity.setName(param.getName());
        entity.setType(param.getType());
        IdentityService identityService = getProcessEngine().getIdentityService();
        Group groupDB = identityService.createGroupQuery()
                .groupId(param.getUuid())
                .singleResult();
        if(groupDB != null){
            entity.setRevision(openApiService.getGroupRevision(param.getUuid()));
        }
        identityService.saveGroup(entity);
        Tenant tenantGroup = identityService.createTenantQuery()
                .tenantId(param.getTenantId())
                .groupMember(param.getUuid()).singleResult();
        if(tenantGroup == null){
            identityService.createTenantGroupMembership(param.getTenantId(),param.getUuid());
        }
        return ResultBean.ofSuccess(entity.getId());
    }

    @Override
    public ResultBean<String> addUserGroup(UserRelationReqExt param) {
        WorkFlowTenant tenant = workFlowTenantService.getOne(
                Wrappers.<WorkFlowTenant>lambdaQuery().eq(WorkFlowTenant::getTenantId, param.getTenantId()));
        if(tenant == null){
            return ResultBean.ofSuccess("租户/项目",BaseExceptionEnum.EC404);
        }
        IdentityService identityService = getProcessEngine().getIdentityService();
        Tenant tenantGroup = identityService.createTenantQuery()
                .tenantId(param.getTenantId())
                .groupMember(param.getGroupId()).singleResult();
        if(tenantGroup == null){
            identityService.createTenantGroupMembership(param.getTenantId(),param.getGroupId());
        }
        Group group = identityService.createGroupQuery()
                .groupId(param.getGroupId())
                .groupMember(param.getUserId()).singleResult();
        if(group == null){
            identityService.createMembership(param.getUserId(),param.getGroupId());
        }
        Tenant tenantUser = identityService.createTenantQuery()
                .tenantId(param.getTenantId())
                .userMember(param.getUserId()).singleResult();
        if(tenantUser == null){
            identityService.createTenantUserMembership(param.getTenantId(),param.getUserId());
        }
        return ResultBean.ofSuccess();
    }

    @Override
    public ResultBean<String> addBatchUserGroup(List<UserRelationReqExt> param) {
        param.stream().forEach( i->{
            WorkFlowTenant tenant = workFlowTenantService.getOne(
                    Wrappers.<WorkFlowTenant>lambdaQuery().eq(WorkFlowTenant::getTenantId, i.getTenantId()));
            if(tenant != null){
                IdentityService identityService = getProcessEngine().getIdentityService();
                Tenant tenantGroup = identityService.createTenantQuery()
                        .tenantId(i.getTenantId())
                        .groupMember(i.getGroupId()).singleResult();
                if(tenantGroup == null){
                    identityService.createTenantGroupMembership(i.getTenantId(),i.getGroupId());
                }
                Group group = identityService.createGroupQuery()
                        .groupId(i.getGroupId())
                        .groupMember(i.getUserId()).singleResult();
                if(group == null){
                    identityService.createMembership(i.getUserId(),i.getGroupId());
                }
                Tenant tenantUser = identityService.createTenantQuery()
                        .tenantId(i.getTenantId())
                        .userMember(i.getUserId()).singleResult();
                if(tenantUser == null){
                    identityService.createTenantUserMembership(i.getTenantId(),i.getUserId());
                }
            }
        });
        return ResultBean.ofSuccess();
    }


}
