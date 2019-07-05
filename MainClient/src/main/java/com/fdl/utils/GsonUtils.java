package com.fdl.utils;

import com.google.gson.Gson;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/23<p>
 * <p>changeTime：2018/10/23<p>
 * <p>version：1<p>
 */
public class GsonUtils
{
    public static  <T> T parseJsonWithGson(String jsonData, Class<T> type){
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }
//    public static <T> T parseArrayJson(String jsonData,Class<T> type){
//        Gson gson = new Gson();
//        JsonParser parser = new JsonParser();
//        JsonArray jsonArray = parser.parse(jsonData).getAsJsonArray();
//        for (JsonElement element :jsonArray)
//        {
//            T bean1 = gson.fromJson(element,type);
//            hospitalNameBeanList.add(bean1);
//        }
//    }
}
