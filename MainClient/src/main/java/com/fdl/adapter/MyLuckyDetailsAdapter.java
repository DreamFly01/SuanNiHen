package com.fdl.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/10<p>
 * <p>changeTime：2019/5/10<p>
 * <p>version：1<p>
 */
public class MyLuckyDetailsAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public MyLuckyDetailsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
            TextView textView = helper.getView(R.id.tv_content);
        helper.setText(R.id.tv_content,item);
        if (from == 1) {
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(10);
        }
    }
    private int from = 0;
    public void setFrom(int from){
        this.from = from;
    }
}
