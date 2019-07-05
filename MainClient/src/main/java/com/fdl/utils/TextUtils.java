package com.fdl.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;



/**
 * <p>desc：textview工具类<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/9/29<p>
 * <p>changeTime：2018/9/29<p>
 * <p>version：1<p>
 */
public class TextUtils {

    /**
     * 为textview设置不同的样式
     * @param context
     * @param view
     * @param string
     * @param index1
     * @param length
     */
    public static void setText(Context context, TextView view, String string, int index1, int length){
        SpannableString styledText = new SpannableString(string);
//        styledText.setSpan(new TextAppearanceSpan(context, R.style.style0), 0, index1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        styledText.setSpan(new TextAppearanceSpan(context, R.style.style1), index1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        view.setText(styledText, TextView.BufferType.SPANNABLE);
    }

}
