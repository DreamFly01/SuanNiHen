package com.fdl.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.fdl.bean.ImgDelagateBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/3<p>
 * <p>changeTime：2019/1/3<p>
 * <p>version：1<p>
 */
public class MyMultiItemAdapter extends MultiItemTypeAdapter<ImgDelagateBean> {
    public MyMultiItemAdapter(Context context, List<ImgDelagateBean> datas) {
        super(context, datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(null, viewType);
    }

    public void upData(List<ImgDelagateBean> data){
        mDatas = data;
        this.notifyDataSetChanged();
    }
}
