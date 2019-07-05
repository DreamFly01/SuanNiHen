package com.fdl.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/11<p>
 * <p>changeTime：2019/1/11<p>
 * <p>version：1<p>
 */
public class UrlUtils {
    public static Map<String, String> getParameters(String url) {
        Map<String, String> params = new HashMap<String, String>();
        if (url == null || "".equals(url.trim())) {
            return params;
        }
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String[] split = url.split("[?]");
        if (split.length == 2 && !"".equals(split[1].trim())) {
            String[] parameters = split[1].split("&");
            if (parameters != null && parameters.length != 0) {
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i] != null
                            && parameters[i].trim().contains("=")) {
                        String[] split2 = parameters[i].split("=");
//split2可能为1，可能为2
                        if (split2.length == 1) {
//有这个参数但是是空的
                            params.put(split2[0], "");
                        } else if (split2.length == 2) {
                            if (!"".equals(split2[0].trim())) {
                                params.put(split2[0], split2[1]);
                            }
                        }
                    }
                }
            }
        }
        return params;
    }
    public static Map<String, String> getShopParameters(String url) {
        Map<String, String> params = new HashMap<String, String>();
        if (url == null || "".equals(url.trim())) {
            return params;
        }
        String[] split = url.split("[//]");
       String [] params1 = StringUtils.split(split[2],"|");
        for (int i = 0; i < params1.length; i++) {
            params.put(params1[i].substring(0,params1[i].indexOf("=")),params1[i].substring(params1[i].indexOf("=")+1,params1[i].length()));
        }
        return params;
    }
}
