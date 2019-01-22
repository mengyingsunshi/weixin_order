package com.ying.weixin_order.utils;

import java.util.Random;

/**
 * @Author sunmengying
 * @Date 2019/1/9 19:08
 */
public class KeyUtil {

    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;

        return  System.currentTimeMillis() + String.valueOf(number);
    }
}
