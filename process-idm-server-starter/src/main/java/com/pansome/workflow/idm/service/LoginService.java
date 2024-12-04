package com.pansome.workflow.idm.service;

import com.pansome.workflow.domain.LoginBody;
import com.pansome.workflow.domain.OpenApiBody;

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
