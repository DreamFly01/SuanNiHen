package com.fdl.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.WinningLogUserBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/10<p>
 * <p>changeTime：2019/5/10<p>
 * <p>version：1<p>
 */
public class MyLuckyAdapter extends BaseQuickAdapter<WinningLogUserBean,BaseViewHolder> {
    public MyLuckyAdapter(int layoutResId, @Nullable List<WinningLogUserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WinningLogUserBean item) {
        helper.setText(R.id.tv_level,item.PrizeLevelName);
        helper.setText(R.id.tv_title,item.PrizeName);
        helper.setText(R.id.tv_num,"兑奖码："+item.PrizeCode);
        helper.setText(R.id.tv_time,item.WinningTime);
        ImageUtils.loadUrlImage(mContext,item.GoodsImg,helper.getView(R.id.iv_logo));
        ImageView ivState = helper.getView(R.id.iv_state);
        switch (item.ExchangeStatus)
        {
            case 0:
                ivState.setVisibility(View.GONE);
                break;
            case 1:
                Glide.with(mContext).load(R.drawable.lucky_state1_bg).into(ivState);
                break;
            case 2:
                Glide.with(mContext).load(R.drawable.lucky_state2_bg).into(ivState);
                break;
        }
    }
}
