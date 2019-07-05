package com.fdl.jpush;

import android.util.Log;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/14<p>
 * <p>changeTime：2019/5/14<p>
 * <p>version：1<p>
 */
public class Logger {
    //设为false关闭日志
    private static final boolean LOG_ENABLE = true;

    public static void i(String tag, String msg){
        if (LOG_ENABLE){
            Log.i(tag, msg);
        }
    }
    public static void v(String tag, String msg){
        if (LOG_ENABLE){
            Log.v(tag, msg);
        }
    }
    public static void d(String tag, String msg){
        if (LOG_ENABLE){
            Log.d(tag, msg);
        }
    }
    public static void w(String tag, String msg){
        if (LOG_ENABLE){
            Log.w(tag, msg);
        }
    }
    public static void e(String tag, String msg){
        if (LOG_ENABLE){
            Log.e(tag, msg);
        }
    }
}
