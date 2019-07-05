package com.fdl.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.ChoseBean;
import com.sg.cj.snh.R;


import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/16<p>
 * <p>changeTime：2019/4/16<p>
 * <p>version：1<p>
 */
public class ChoseAdapter extends BaseQuickAdapter<ChoseBean,BaseViewHolder> {
    public ChoseAdapter(int layoutResId, @Nullable List<ChoseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChoseBean item) {
        TextView content = helper.getView(R.id.tv_content);
        if(item.isChose){
            helper.getView(R.id.iv_chose_bg).setVisibility(View.VISIBLE);
            content.setTextColor(Color.parseColor("#FC7A06"));
        }else {
            helper.getView(R.id.iv_chose_bg).setVisibility(View.GONE);
            content.setTextColor(Color.parseColor("#1e1e1e"));
        }
        helper.setText(R.id.tv_content,item.itemContent);
        Glide.with(mContext).load(item.imgRes).into((ImageView) helper.getView(R.id.iv_state_bg));
    }
}
