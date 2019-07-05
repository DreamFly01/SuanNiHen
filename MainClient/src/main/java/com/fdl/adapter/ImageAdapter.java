package com.fdl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/4<p>
 * <p>changeTime：2019/4/4<p>
 * <p>version：1<p>
 */
public class ImageAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ImageAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageUtils.loadUrlImage(mContext,item,helper.getView(R.id.iv_image));
    }
}
