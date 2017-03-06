package com.hzitshop.util;

import java.security.MessageDigest;

/**
 * SHA加密
 * Created by 冼耀基 on 2016/10/18.
 */
public class SHAUtil {
    /**
     * SHA加密
     * @param input
     * @return
     * @throws Exception
     */
    public static String shaEncode(String input) throws Exception{
        MessageDigest sha = null;
        try{
            sha = MessageDigest.getInstance("SHA");
        }catch(Exception e){
            return "";
        }
        byte[] byteArray = input.getBytes("UTF-8");
        byte[] shaBytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < shaBytes.length; i++) {
            int val = ((int) shaBytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
