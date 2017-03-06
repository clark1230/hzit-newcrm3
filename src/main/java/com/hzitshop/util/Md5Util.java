package com.hzitshop.util;

import java.security.MessageDigest;

/**
 * MD5加密
 * @author clark1230
 * @version 1.0
 */
public class Md5Util {
    public static String getMD5(String str) throws Exception{
        MessageDigest mDigest = MessageDigest.getInstance("MD5");
        mDigest.update(str.getBytes("UTF-8"));
        byte[] by = mDigest.digest();
        StringBuilder sBuilder = new StringBuilder();
        for(byte b : by){
            sBuilder.append(Character.forDigit(b>>4&0xf,16));
            sBuilder.append(Character.forDigit(b&0xf,16));
        }
        return sBuilder.toString();
    }
}
