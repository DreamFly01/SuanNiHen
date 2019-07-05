package com.fdl.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/11/6<p>
 * <p>changeTime：2018/11/6<p>
 * <p>version：1<p>
 */
public class CapitalAdapter extends CommonAdapter<String> {

    public CapitalAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {

    }
}
