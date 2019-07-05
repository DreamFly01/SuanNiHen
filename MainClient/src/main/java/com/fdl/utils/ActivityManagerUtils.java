package com.fdl.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/3/1<p>
 * <p>changeTime：2019/3/1<p>
 * <p>version：1<p>
 */
public class ActivityManagerUtils {
    private static ActivityManagerUtils sInstance = new ActivityManagerUtils();
    private WeakReference<Activity> sCurrentActivityWeakRef;


    private ActivityManagerUtils() {

    }

    public static ActivityManagerUtils getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

}
