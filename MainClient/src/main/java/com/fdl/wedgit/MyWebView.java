package com.fdl.wedgit;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.bulong.rudeness.RudenessScreenHelper;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/27<p>
 * <p>changeTime：2018/12/27<p>
 * <p>version：1<p>
 */
public class MyWebView extends WebView {
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);
        RudenessScreenHelper.resetDensity(getContext(), 750);
    }
}
