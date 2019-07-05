package com.sg.cj.common.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;


/**
 * 
 * @Description: 
 * @author : chenjie
 * creat at 2018/10/29 16:36
 */

public class SgNoScrollListView extends ListView {

    public SgNoScrollListView(Context context) {
        super(context);
    }

    public SgNoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SgNoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
