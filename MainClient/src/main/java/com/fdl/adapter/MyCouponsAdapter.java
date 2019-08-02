package com.fdl.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.activity.main.redPacket.RedPackRVAdapter;
import com.fdl.bean.CouponsBean;
import com.fdl.bean.MyCouponsBean;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.GlideRoundTransform;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/10<p>
 * <p>changeTime：2019/5/10<p>
 * <p>version：1<p>
 */
public class MyCouponsAdapter extends BaseQuickAdapter<MyCouponsBean.ListBean, BaseViewHolder> {
    private int type;
    private Context context;

    public MyCouponsAdapter(int layoutResId, @Nullable List<MyCouponsBean.ListBean> data, int type, Context context) {
        super(layoutResId, data);
        this.type = type;
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, MyCouponsBean.ListBean item) {
        showData(helper, item);

    }

    private int from;

    public void setFrom(int from) {
        this.from = from;
    }

    private void showData(BaseViewHolder helper, MyCouponsBean.ListBean item) {
        LinearLayout mCouponsBgLl = helper.getView(R.id.coupons_bg_ll);
        ImageView mCouponShopIcon = helper.getView(R.id.coupon_shop_icon);
        TextView mCouponShopName = helper.getView(R.id.coupon_shop_name);
        TextView mCouponPrice = helper.getView(R.id.coupon_price);
        TextView mCouponMsgTxt = helper.getView(R.id.coupon_msg_txt);
        TextView mCouponsGoodsMsg = helper.getView(R.id.coupons_goods_msg);
        TextView mCouponsTypeMsg = helper.getView(R.id.coupons_type_msg);
        ImageButton mCouponShopShow = helper.getView(R.id.coupon_shop_show);
        RecyclerView mCouponShopRv = helper.getView(R.id.coupon_shop_rv);
        TextView mCouponsDate = helper.getView(R.id.coupons_date);
        TextView mCouponsMsg = helper.getView(R.id.coupons_msg);

        switch (type) {
            case 0:
                mCouponsBgLl.setBackgroundResource(R.drawable.coupons_white_bg);
                mCouponsTypeMsg.setVisibility(View.GONE);
                break;
            case 1:
                mCouponsBgLl.setBackgroundResource(R.drawable.coupons_gray_bg);
                mCouponsTypeMsg.setVisibility(View.VISIBLE);
                mCouponsTypeMsg.setText("已使用");
                break;
            case 2:
                mCouponsBgLl.setBackgroundResource(R.drawable.coupons_gray_bg);
                mCouponsTypeMsg.setVisibility(View.VISIBLE);
                mCouponsTypeMsg.setText("已过期");
                break;
        }

        setText(mCouponShopName, item.ShopName);
        setText(mCouponPrice, item.CouponValue);
        setText(mCouponMsgTxt, item.CouponName);
        setText(mCouponsDate, StrUtils.fromResources(context, R.string.coupons_date, item.BeginDate, item.EndDate));
        setText(mCouponsMsg, StrUtils.fromResources(context, R.string.coupons_goods_msg, item.ConditionValue, item.CouponValue));
        if (item.GoodsList.size() > 0) {
            setText(mCouponsGoodsMsg, "指定商品优惠券");
        } else {
            setText(mCouponsGoodsMsg, "商铺优惠券");
        }

        Glide.with(context)
                .load(item.Logo)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context)))
                .into(mCouponShopIcon);

        if (item.GoodsList.size() > 0) {
            mCouponShopRv.setVisibility(View.VISIBLE);
            RedPackRVAdapter adapter = new RedPackRVAdapter(item.CouponId,item.GoodsList, context);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mCouponShopRv.setLayoutManager(manager);
            mCouponShopRv.setAdapter(adapter);
        }else {
            mCouponShopRv.setVisibility(View.GONE);
        }

    }

    private void setText(TextView text, double db) {
        setText(text, db + "");
    }

    private void setText(TextView text, String string) {
        text.setText(string);
    }


}
