package com.sg.cj.snh.uitls;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.HttpParams;

import java.util.Map;

/**
 * @author 陈自强
 * @date 2019/7/30
 */
public class OkGoTools {
    public static void postRequest(String url, Map<String,Object> param, Callback callback) {
        Gson gson = new Gson();
        String json = gson.toJson(param);
        postRequest(url, json, callback);
    }
    public static void postRequest(String url, HttpParams param, Callback callback) {
        Gson gson = new Gson();
        String json = gson.toJson(param);
        postRequest(url, json, callback);
    }
    public static void postRequest(String url, String json, Callback callback) {
        OkGo.<String>post(url)
                .upJson(json)
                .execute(callback);
    }
    public static void getRequst(String url, HttpParams param, Callback callback) {
        OkGo.<String>get(url)
                .params(param)
                .execute(callback);
    }
}
