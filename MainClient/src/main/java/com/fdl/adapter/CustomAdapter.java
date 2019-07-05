package com.fdl.adapter;

import android.content.Context;
import android.widget.TextView;

import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/9<p>
 * <p>changeTime：2018/10/9<p>
 * <p>version：1<p>
 */
public class CustomAdapter extends CommonAdapter<String> {
    public CustomAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        TextView textView = holder.getView(R.id.tv_custom);
        textView.setText(s);
    }
}
