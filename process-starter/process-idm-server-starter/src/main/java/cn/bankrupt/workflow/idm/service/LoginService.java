package cn.bankrupt.workflow.idm.service;

import cn.bankrupt.workflow.domain.LoginBody;
import cn.bankrupt.workflow.domain.OpenApiBody;

public interface LoginService {

    /**
     * 获取TOKEN
     * @param loginBody
     * @return
     */
    String login(LoginBody loginBody);

    /**
     * 获取TOKEN
     * @param loginBody
     * @return
     */
    String accessToken(OpenApiBody loginBody);

}
