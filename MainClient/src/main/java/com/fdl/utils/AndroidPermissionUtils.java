package com.fdl.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/24<p>
 * <p>changeTime：2018/10/24<p>
 * <p>version：1<p>
 */
public class AndroidPermissionUtils {
    /**
     * Android 6.0及以上 检测是否具有某些权限
     * */

    public static boolean hasAndroidPermission(Context context, String [] permission){
        boolean has=true;
        for(String per:permission){
            if(ContextCompat.checkSelfPermission(context,per) != PackageManager.PERMISSION_GRANTED){
                has=false;
                break;
            }
        }
        return has;
    }

    /**
     * Android 6.0及以上 申请某些权限
     * */

    public static void requestAndroidPermission(Activity activity, int code, String []permission){
        ActivityCompat.requestPermissions(activity,permission,code);
    }


}
