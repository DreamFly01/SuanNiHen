package com.fdl.requestApi;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/10<p>
 * <p>changeTime：2018/10/10<p>
 * <p>version：1<p>
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    public static String LOGKYE = "HttpLogInfo";
    @Override
    public void log(String message) {
        Log.d("HttpLogInfo", message);
    }
}