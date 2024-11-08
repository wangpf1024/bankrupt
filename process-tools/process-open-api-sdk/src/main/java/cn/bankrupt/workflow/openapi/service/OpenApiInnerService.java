package cn.bankrupt.workflow.openapi.service;

import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.openapi.vo.GroupReqExt;
import cn.bankrupt.workflow.openapi.vo.TenantReqExt;
import cn.bankrupt.workflow.openapi.vo.UserRelationReqExt;
import cn.bankrupt.workflow.openapi.vo.UserReqExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "用户管理",value="用户管理" )
public interface OpenApiInnerService {

    /**
     * @explain 新增租户  <swagger POST请求>
     * @param   @RequestBody：TenantReqExt
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月3日
     */
    @PostMapping("/process-idm-server/open-api/tenant/add")
    @ApiOperation(value = "新增租户", notes = "作者：王鹏飞")
    ResultBean<String> addTenant(@RequestBody TenantReqExt param);
    /**
     * @explain 新增用户  <swagger POST请求>
     * @param   @RequestBody：ModelingFieldDefExt
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月3日
     */
    @PostMapping("/process-idm-server/open-api/tenant/add/user")
    @ApiOperation(value = "新增用户", notes = "作者：王鹏飞")
    ResultBean<String> addUser(@RequestBody UserReqExt param);

    /**
     * @explain 新增分组  <swagger POST请求>
     * @param   @RequestBody：ModelingFieldDefExt
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月3日
     */
    @PostMapping("/process-idm-server/open-api/tenant/add/group")
    @ApiOperation(value = "新增分组", notes = "作者：王鹏飞")
    ResultBean<String> addGroup(@RequestBody GroupReqExt param);

    /**
     * @explain 新增用户分组，租户关系  <swagger POST请求>
     * @param   @RequestBody：ModelingFieldDefExt
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月3日
     */
    @PostMapping("/process-idm-server/open-api/tenant/addUser/relations")
    @ApiOperation(value = "新增用户分组/租户关系 ", notes = "作者：王鹏飞")
    ResultBean<String> addUserGroup(@RequestBody UserRelationReqExt param);


    /**
     * @explain 批量新增用户分组，租户关系  <swagger POST请求>
     * @param   @RequestBody：ModelingFieldDefExt
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月3日
     */
    @PostMapping("/process-idm-server/open-api/tenant/batch/addUser/relations")
    @ApiOperation(value = "批量新增用户分组/租户关系 ", notes = "作者：王鹏飞")
    ResultBean<String> addBatchUserGroup(@RequestBody List<UserRelationReqExt> param);


}
