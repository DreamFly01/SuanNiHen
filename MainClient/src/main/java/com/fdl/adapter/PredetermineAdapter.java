package com.fdl.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.OrdersData;
import com.fdl.bean.SubscribeApplyInfoBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/7/1<p>
 * <p>changeTime：2019/7/1<p>
 * <p>version：1<p>
 */
public class PredetermineAdapter extends BaseQuickAdapter<OrdersData, BaseViewHolder> {
    public PredetermineAdapter(int layoutResId, @Nullable List<OrdersData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrdersData item) {
        LinearLayout llItem = helper.getView(R.id.ll_item);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) helper.getView(R.id.ll_item).getLayoutParams();

        ImageUtils.loadUrlImage(mContext, item.Image, helper.getView(R.id.iv_product_logo1));
        helper.setText(R.id.tv_name, item.GoodsName);
        helper.setText(R.id.tv_money, StrUtils.moenyToDH(item.SalesPrice + ""));
        if ((item.GoodsNum + item.GoodsNum2) <= 0) {
            layoutParams.height = 0;
            layoutParams.width = 0;
            llItem.setVisibility(View.GONE);
        } else {
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            llItem.setVisibility(View.VISIBLE);
        }
        if (item.GoodsNum2 > 0) {
            helper.getView(R.id.iv_delete).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_state).setVisibility(View.GONE);
            helper.setText(R.id.tv_goodsNum, (item.GoodsNum + item.GoodsNum2) + "");
        } else {
            helper.getView(R.id.iv_delete).setVisibility(View.GONE);
            helper.getView(R.id.tv_state).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_goodsNum, item.GoodsNum + "");
        }
        helper.addOnClickListener(R.id.iv_add)
                .addOnClickListener(R.id.iv_delete);

    }
}
