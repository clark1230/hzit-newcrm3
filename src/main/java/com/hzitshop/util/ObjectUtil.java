package com.hzitshop.util;

import java.util.Collection;

/**
 * Created by xianyaoji on 2016/12/12.
 */
public class ObjectUtil {
    public static <T> boolean isNotNull(Collection<T> t){
            if(t != null && t.size() >0){
                return true;
            }else{
                return false;
            }
    }
}
