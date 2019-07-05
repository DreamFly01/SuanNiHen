package com.fdl.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.MainProcuctBean;
import com.fdl.bean.ShopBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/12<p>
 * <p>changeTime：2019/1/12<p>
 * <p>version：1<p>
 */
public class ShopSearchAdapter extends BaseQuickAdapter<MainProcuctBean,BaseViewHolder> {
    List<String> startDatas = new ArrayList<>();
    private StartAdapter startAdapter;

    public ShopSearchAdapter(int layoutResId, @Nullable List<MainProcuctBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MainProcuctBean item) {
        ImageView logo = helper.getView(R.id.iv_Logo);
        TextView name = helper.getView(R.id.tv_name);
        RecyclerView recyclerViewStart = helper.getView(R.id.recyclerView_start);
        ImageUtils.loadUrlImage(mContext,item.Logo,logo);
        for (int i = 0; i < item.StarLevel; i++) {
            startDatas.add(i + "");
        }
        name.setText(item.ShopName);
        helper.setText(R.id.tv_content,item.Introduction);
        startAdapter = new StartAdapter(R.layout.item_start_layout, startDatas);
        recyclerViewStart.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        recyclerViewStart.setNestedScrollingEnabled(false);
        recyclerViewStart.setAdapter(startAdapter);

    }
}
