package com.ying.weixin_order.utils;

import com.ying.weixin_order.enums.CodeEnum;

/**
 * @Author sunmengying
 * @Date 2019/1/9 17:26
 */
public class EnumUtil {
                                                                     //  ENUM 泛型类的字节码对象   .class  getClass()
    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for(T each : enumClass.getEnumConstants()){
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return  null;
    }
}
