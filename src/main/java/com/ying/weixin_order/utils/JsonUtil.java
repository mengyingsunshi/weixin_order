package com.ying.weixin_order.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author sunmengying
 * @Date 2019/1/14 17:53
 */
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
