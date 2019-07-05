package com.fdl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.FoodPredeterminelistBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/7/2<p>
 * <p>changeTime：2019/7/2<p>
 * <p>version：1<p>
 */
public class FoodDialogAdapter extends BaseQuickAdapter<FoodPredeterminelistBean,BaseViewHolder> {
    public FoodDialogAdapter(int layoutResId, @Nullable List<FoodPredeterminelistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodPredeterminelistBean item) {
        ImageUtils.loadUrlImage(mContext,item.Logo,helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_shopName,item.ShopName);
        helper.setText(R.id.tv_time,"预定时间："+item.DinnerTime);
        helper.setText(R.id.tv_personNum,"预定人数："+(item.AdultCount+item.ChildCount));
        helper.addOnClickListener(R.id.ll_item)
                .addOnClickListener(R.id.tv_delete);
    }
}
