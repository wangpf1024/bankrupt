package cn.bankrupt.workflow.utils;

import cn.bankrupt.workflow.constants.CacheConstants;

public class TokenUtils {


    public static String getTokenKey(String uuid)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

}
