package com.sg.cj.common.base.utils;

import android.os.Debug;
import android.util.Log;

/**
 * author : ${CHENJIE}
 * created at  16/10/25 10:04
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgLog {

    private static String format = "yyyy-MM-dd HH:mm:ss";
    public static boolean D = true;
    public static String TAG = "shaguachupin";

    public static void v(String msg) {
        v(getTag(), msg);
    }

    public static void d(String msg) {
        d(getTag(), msg);
    }

    public static void i(String msg) {
        i(getTag(), msg);
    }

    public static void w(String msg) {
        w(getTag(), msg);
    }

    public static void e(String msg) {
        e(getTag(), msg);
    }

    public static void wtf(String msg) {
        wtf(getTag(), msg);
    }

    public static void v(String TAG, String msg) {
        if (D)
            Log.v(TAG, getHead() + msg + " time: " + SgDateUtil.date2String(System.currentTimeMillis(), format));
    }

    public static void d(String TAG, String msg) {
        if (D)
            Log.d(TAG, getHead() + msg + " time: " + SgDateUtil.date2String(System.currentTimeMillis(), format));
    }

    public static void i(String TAG, String msg) {
        if (D)
            Log.i(TAG, getHead() + msg + " time: " + SgDateUtil.date2String(System.currentTimeMillis(), format));
    }

    public static void w(String TAG, String msg) {
        if (D)
            Log.w(TAG, getHead() + msg + " time: " + SgDateUtil.date2String(System.currentTimeMillis(), format));
    }

    public static void e(String TAG, String msg) {
        if (D)
            Log.e(TAG, getHead() + msg + " time: " + SgDateUtil.date2String(System.currentTimeMillis(), format));
    }

    public static void wtf(String TAG, String msg) {
        if (D)
            Log.wtf(TAG, getHead() + msg + " time: " + SgDateUtil.date2String(System.currentTimeMillis(), format));
    }

    private static String getHead() {
        //return "LogUtil: \n";
        return ((new Exception().getStackTrace()[3].getClassName())
                + " -> "
                + new Exception().getStackTrace()[3].getMethodName() + "\n");
    }

    private static String getTag() {
        return TAG;
//        return ((new Exception().getStackTrace()[2].getClassName())
//                + " -> "
//                + new Exception().getStackTrace()[2].getMethodName());
    }
}
