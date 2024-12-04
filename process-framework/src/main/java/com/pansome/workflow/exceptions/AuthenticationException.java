/**
 * Copyright (c) 2020 磐晟科技 All rights reserved.
 * <p>
 * https://www.ipansheng.io
 * <p>
 * 版权所有，侵权必究！
 */
package com.pansome.workflow.exceptions;

/**
 * 认证异常类
 *
 * @author duliang
 */
public class AuthenticationException extends org.springframework.security.core.AuthenticationException {
    public AuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthenticationException(String msg) {
        super(msg);
    }
}
