package com.fdl.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/7/17<p>
 * <p>changeTime：2019/7/17<p>
 * <p>version：1<p>
 */
public class LanguageUtils {
    public static void setdefaultLanguage(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        config.locale = Locale.CHINESE;
        context.getResources().updateConfiguration(config, metrics);
        SPUtils.getInstance(context).saveData("language",language);
    }
}
