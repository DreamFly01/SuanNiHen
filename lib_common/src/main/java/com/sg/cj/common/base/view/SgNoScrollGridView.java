package com.sg.cj.common.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * 
 * @Description: 
 * @author : chenjie
 * creat at 2018/10/29 16:49
 */

public class SgNoScrollGridView extends GridView {

    public SgNoScrollGridView(Context context) {
        super(context);
    }

    public SgNoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SgNoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
