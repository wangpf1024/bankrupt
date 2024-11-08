package cn.bankrupt.workflow.openapi;

import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.openapi.vo.GroupReqExt;
import cn.bankrupt.workflow.openapi.vo.UserRelationReqExt;
import cn.bankrupt.workflow.openapi.vo.UserReqExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class WorkFlowOpenApiClient {

    @Value("${pansome.workflow.host}")
    String BASE_URL = "";

    @Autowired
    private RestTemplate restTemplate;

    public ResultBean<String> addUser(UserReqExt param) {
        String url = BASE_URL + "/process-center-server/open-api/tenant/add/user";
        return restTemplate.postForObject(url, param, ResultBean.class);
    }

    public ResultBean<String> addGroup(GroupReqExt param) {
        String url = BASE_URL + "/process-center-server/open-api/tenant/add/group";
        return restTemplate.postForObject(url, param, ResultBean.class);
    }

    public ResultBean<Void> addUserGroup(UserRelationReqExt param) {
        String url = BASE_URL + "/process-center-server/open-api/tenant/addUser/relations";
        return restTemplate.postForObject(url, param, ResultBean.class);
    }

}
