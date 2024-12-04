package com.pansome.workflow.utils;


import cn.hutool.core.util.IdUtil;

/**
 * ID生成器工具类
 *
 */
public class IDUtils
{
    /**
     * 获取随机UUID
     * 
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return IdUtil.fastSimpleUUID();
    }

    public static void main(String[] args) {
        System.out.println(IDUtils.randomUUID());
    }
}
