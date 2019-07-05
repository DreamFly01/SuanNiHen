package com.fdl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/3/20<p>
 * <p>changeTime：2019/3/20<p>
 * <p>version：1<p>
 */
public class GoodsDetailsAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public GoodsDetailsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
