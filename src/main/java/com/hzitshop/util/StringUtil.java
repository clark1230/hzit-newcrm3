package com.hzitshop.util;

/**
 * 字符串工具类
 * Created by clark1230 on 2016/7/21.
 */
public class StringUtil {
    /**
     * 判断是否是空字符串 null和"" 返回boolean类型数据
     *
     * @author Robin Chang
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        boolean isFlag = false;
        if (null != s && !"".equals(s.trim())) {
            isFlag = true;
        }else{
            isFlag = false;
        }
        return isFlag;
    }
}
