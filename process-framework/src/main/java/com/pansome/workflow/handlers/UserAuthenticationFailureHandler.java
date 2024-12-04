
package com.pansome.workflow.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 认证失败处理器
 *
 * @author duliang
 */
@Slf4j
@Component
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

}
