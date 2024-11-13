package cn.bankrupt.workflow.filter;

import cn.bankrupt.workflow.domain.AccessVo;
import cn.bankrupt.workflow.exceptions.AuthenticationException;
import cn.bankrupt.workflow.handlers.UserAuthenticationFailureHandler;
import cn.bankrupt.workflow.utils.JwtUtil;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.constants.Constants;
import cn.bankrupt.workflow.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ProcessRedisCache processRedisCache;

    @Autowired
    UserAuthenticationFailureHandler authenticationFailureHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Request Method: {}", request.getMethod());
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (authorizationHeader != null) {
            if(authorizationHeader.startsWith(Constants.TOKEN_PREFIX)){
                token = authorizationHeader.substring(7);
            }else{
                token =  authorizationHeader;
            }
            username = jwtUtil.extractUsername(token);
        }
        if (username != null) {
            if (jwtUtil.validateToken(token, username)) {
                Claims claims = jwtUtil.parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = TokenUtils.getTokenKey(uuid);
                Boolean hasKey = processRedisCache.hasKey(userKey);
                if(hasKey){
                    AccessVo accessVo = processRedisCache.getCacheObject(userKey);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(accessVo,
                                    null, accessVo.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else{
                    authenticationFailureHandler.onAuthenticationFailure(request, response, new AuthenticationException("会话失效"));
                }
            }
        }else{
            authenticationFailureHandler.onAuthenticationFailure(request, response, new AuthenticationException("会话失效"));
        }
        chain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Exclude the login endpoint from JWT filter
        return request.getServletPath().equals("/login")
                || request.getServletPath().equals("/access_token")
                || request.getServletPath().equals("/actuator/shutdown");
    }
}
