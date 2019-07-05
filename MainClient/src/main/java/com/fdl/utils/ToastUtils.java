package com.fdl.utils;

import android.widget.Toast;

import com.sg.cj.snh.PartyApp;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/6/10<p>
 * <p>changeTime：2019/6/10<p>
 * <p>version：1<p>
 */
public class ToastUtils {
    /**
     * 显示toast
     *
     * @param content  内容
     * @param duration 持续时间
     */
    public static void toast(String content, int duration) {
        if (content == null) {
            return;
        } else {
            PartyApp.ToastMgr.builder.display(content, duration);
        }
    }

    /**
     * 显示默认持续时间为short的Toast
     *
     * @param content 内容
     */
    public static void toast(String content) {
        if (content == null) {
            return;
        } else {
            PartyApp.ToastMgr.builder.display(content, Toast.LENGTH_SHORT);
        }
    }
}
