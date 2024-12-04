package com.pansome.workflow.utils;

import com.pansome.workflow.constants.CacheConstants;

public class TokenUtils {


    public static String getTokenKey(String uuid)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

}
