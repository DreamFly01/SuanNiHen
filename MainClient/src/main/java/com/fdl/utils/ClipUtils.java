package com.fdl.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

/**
 * <p>desc：复制字符串到剪贴板管理器<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/4<p>
 * <p>changeTime：2018/12/4<p>
 * <p>version：1<p>
 */
public class ClipUtils {
    public static void copyText(Context context, String text, String toastStr) {
        //1. 复制字符串到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, text));
        if (!StrUtils.isEmpty(toastStr)) {
            Toast.makeText(context, toastStr, Toast.LENGTH_SHORT).show();
        }
    }


}
