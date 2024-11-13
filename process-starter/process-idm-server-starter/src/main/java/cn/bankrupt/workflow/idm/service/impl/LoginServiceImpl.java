package cn.bankrupt.workflow.idm.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.constants.Constants;
import cn.bankrupt.workflow.domain.LoginBody;
import cn.bankrupt.workflow.domain.OpenApiBody;
import cn.bankrupt.workflow.domain.AccessVo;
import cn.bankrupt.workflow.idm.service.LoginService;
import cn.bankrupt.workflow.utils.JwtUtil;
import cn.bankrupt.workflow.utils.TokenUtils;
import cn.bankrupt.workflow.web.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ProcessRedisCache processRedisCache;


    @Override
    public String login(LoginBody loginBody) {
        return getAccessToken(loginBody.getUsername(),loginBody.getPassword());
    }

    @Override
    public String accessToken(OpenApiBody openApiBody) {
        return getAccessToken(openApiBody.getAccessName(),openApiBody.getAccessKey());
    }


    /**
     * 获取token
     * @param name
     * @param password
     * @return
     */
    private String getAccessToken(String name,String password){

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(name, password);

        AuthenticationContextHolder.setContext(authenticationToken);

        // 该方法会去调用 UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        AccessVo loginUser = (AccessVo) authentication.getPrincipal();

        loginUser.setToken(IdUtil.fastSimpleUUID());

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, loginUser.getToken());

        String token  = jwtUtil.generateToken(loginUser.getUsername()
                ,claims);

        String userKey = TokenUtils.getTokenKey(loginUser.getToken());

        processRedisCache.setCacheObject(userKey, loginUser, 3000, TimeUnit.MINUTES);

        return token;
    }


}
