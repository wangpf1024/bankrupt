package com.pansome.workflow.web;

import org.springframework.security.core.Authentication;

/**
 * 身份验证信息
 * 
 * @author ruoyi
 */
public class AuthenticationContextHolder
{
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    //多参数存储变量
    private static final ThreadLocal<ParameterContext> parameterContextHolder = ThreadLocal.withInitial(()-> new ParameterContext());

    public static Authentication getContext()
    {
        return contextHolder.get();
    }

    public static ParameterContext getParameter()
    {
        return parameterContextHolder.get();
    }

    public static void setContext(Authentication context)
    {
        contextHolder.set(context);
    }
    public static void setParameterContext(ParameterContext context)
    {
        parameterContextHolder.set(context);
    }

    public static void clearContext()
    {
        contextHolder.remove();
        parameterContextHolder.remove();
    }
}
