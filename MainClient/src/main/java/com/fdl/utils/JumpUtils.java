package com.fdl.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * <p>文件描述：跳转工具<p>
 * <p>作者：Administrator<p>
 * <p>创建时间：2018/9/13<p>
 * <p>更改时间：2018/9/13<p>
 * <p>版本号：1<p>
 */
public class JumpUtils {
    public static void simpJump(Activity fromActivity,Class toActivity,boolean isCloseActivity){
        Intent intent = new Intent(fromActivity,toActivity);
        fromActivity.startActivity(intent);
        if(isCloseActivity){
            fromActivity.finish();
        }
    }
    public static void dataJump(Activity fromActivity, Class toActivity, Bundle bundle,boolean isCloseActivity){
        Intent intent = new Intent(fromActivity,toActivity);
        intent.putExtras(bundle);
        fromActivity.startActivity(intent);
        if(isCloseActivity){
            fromActivity.finish();
        }
    }
}
