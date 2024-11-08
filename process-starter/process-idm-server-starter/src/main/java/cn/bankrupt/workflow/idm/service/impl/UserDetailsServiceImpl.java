package cn.bankrupt.workflow.idm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.domain.AccessVo;
import cn.bankrupt.workflow.idm.entity.OpenApi;
import cn.bankrupt.workflow.idm.service.OpenApiService;
import cn.bankrupt.workflow.utils.SecurityUtils;
import cn.bankrupt.workflow.web.AuthenticationContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    OpenApiService openApiService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        OpenApi openApi = openApiService.getOne(Wrappers.<OpenApi>lambdaQuery().eq(OpenApi::getAccessName, username));

        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();

        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        if(SecurityUtils.matchesPassword(password,openApi.getAccessKey())){

            AccessVo u = new AccessVo();

            BeanUtils.copyProperties(openApi,u);

            return u;
        };

        return null;

    }
}
