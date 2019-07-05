package com.fdl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.CouponsBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/9<p>
 * <p>changeTime：2019/5/9<p>
 * <p>version：1<p>
 */
public class CouponsAdapter extends BaseMultiItemQuickAdapter<CouponsBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CouponsAdapter(List<CouponsBean> data) {
        super(data);
        addItemType(CouponsBean.PRODUCT, R.layout.item_product_layout);
        addItemType(CouponsBean.COUPONS, R.layout.item_coupons_layout);
        addItemType(CouponsBean.COUPONS_TITLE, R.layout.item_coupons_title_layout);
        addItemType(CouponsBean.PRODUCT_TITLE, R.layout.item_product_title_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponsBean item) {
        switch (helper.getItemViewType()) {
            case CouponsBean.PRODUCT:
                ImageUtils.loadUrlImage(mContext, item.ShowImg, (ImageView) helper.getView(R.id.iv_logo));
                break;
            case CouponsBean.COUPONS:
                helper.setText(R.id.tv_money, item.ConditionValue + "");
                helper.setText(R.id.tv_money1, item.CouponValue + "");
                helper.setText(R.id.tv_time, item.EndDate + "到期");
                if (item.CouponWay==1) {
                    helper.getView(R.id.ll_coupons).setBackgroundResource(R.drawable.store_coupons_bg);
                }else if(item.CouponWay==2){
                    helper.getView(R.id.ll_coupons).setBackgroundResource(R.drawable.product_coupons_bg);
                }
                break;
            case CouponsBean.PRODUCT_TITLE:

                break;
            case CouponsBean.COUPONS_TITLE:
                break;
        }
        helper.addOnClickListener(R.id.ll_item1);
        helper.addOnClickListener(R.id.ll_item2);
        helper.addOnClickListener(R.id.ll_item3);
        helper.addOnClickListener(R.id.ll_item4);
    }
}
