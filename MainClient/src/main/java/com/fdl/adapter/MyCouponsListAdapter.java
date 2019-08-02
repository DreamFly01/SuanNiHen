package com.fdl.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.activity.main.redPacket.RedPackRVAdapter;
import com.fdl.bean.MyCouponsBean;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.GlideRoundTransform;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * @author 陈自强
 * @date 2019/8/1
 */
public class MyCouponsListAdapter extends BaseAdapter {
    private List<MyCouponsBean.ListBean> datas;
    private Context context;
    private int type;

    public MyCouponsListAdapter(List<MyCouponsBean.ListBean> datas, Context context, int type) {
        this.datas = datas;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public MyCouponsBean.ListBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_mycoupon_layout, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mCouponsBgLl = (LinearLayout) convertView.findViewById(R.id.coupons_bg_ll);
        viewHolder.mCouponShopIcon = (ImageView) convertView.findViewById(R.id.coupon_shop_icon);
        viewHolder.mCouponShopName = (TextView) convertView.findViewById(R.id.coupon_shop_name);
        viewHolder.mCouponPrice = (TextView) convertView.findViewById(R.id.coupon_price);
        viewHolder.mCouponMsgTxt = (TextView) convertView.findViewById(R.id.coupon_msg_txt);
        viewHolder.mCouponsDate = (TextView) convertView.findViewById(R.id.coupons_date);
        viewHolder.mCouponsMsg = (TextView) convertView.findViewById(R.id.coupons_msg);
        viewHolder.mCouponsTypeMsg = (TextView) convertView.findViewById(R.id.coupons_type_msg);
        viewHolder.mCouponsGoodsMsg = (TextView) convertView.findViewById(R.id.coupons_goods_msg);
        viewHolder.mCouponShopShow = (ImageButton) convertView.findViewById(R.id.coupon_shop_show);
        viewHolder.mCouponShopRv = (RecyclerView) convertView.findViewById(R.id.coupon_shop_rv);
        viewHolder.redPacket = convertView.findViewById(R.id.coupon_new_red_packet);
        showData(viewHolder, getItem(position));

        return convertView;
    }


    private class ViewHolder {
        LinearLayout mCouponsBgLl;
        ImageView mCouponShopIcon;
        TextView mCouponShopName;
        TextView mCouponPrice;
        TextView mCouponMsgTxt;
        TextView mCouponsDate;
        TextView mCouponsMsg;
        TextView mCouponsTypeMsg;
        TextView mCouponsGoodsMsg;
        ImageButton mCouponShopShow;
        RecyclerView mCouponShopRv;
        TextView redPacket;

        public ViewHolder() {

        }
    }

    private void showData(ViewHolder viewHolder, MyCouponsBean.ListBean item) {

        switch (type) {
            case 0:
                viewHolder.mCouponsBgLl.setBackgroundResource(R.drawable.coupons_white_bg);
                viewHolder.mCouponsTypeMsg.setVisibility(View.GONE);
                break;
            case 1:
                viewHolder.mCouponsBgLl.setBackgroundResource(R.drawable.coupons_gray_bg);
                viewHolder.mCouponsTypeMsg.setVisibility(View.VISIBLE);
                viewHolder.mCouponsTypeMsg.setText("已使用");
                break;
            case 2:
                viewHolder.mCouponsBgLl.setBackgroundResource(R.drawable.coupons_gray_bg);
                viewHolder.mCouponsTypeMsg.setVisibility(View.VISIBLE);
                viewHolder.mCouponsTypeMsg.setText("已过期");
                break;
        }

        setText(viewHolder.mCouponShopName, item.ShopName);
        setText(viewHolder.mCouponPrice, item.CouponValue);
        setText(viewHolder.mCouponMsgTxt, item.CouponName);
        setText(viewHolder.mCouponsDate, StrUtils.fromResources(context, R.string.coupons_date, item.BeginDate, item.EndDate));
        setText(viewHolder.mCouponsMsg, StrUtils.fromResources(context, R.string.coupons_goods_msg, item.ConditionValue, item.CouponValue));
        if (item.GoodsList.size() > 0) {
            setText(viewHolder.mCouponsGoodsMsg, "指定商品优惠券");
        } else {
            setText(viewHolder.mCouponsGoodsMsg, "商铺优惠券");
        }

        Glide.with(context)
                .load(item.Logo)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context)))
                .into(viewHolder.mCouponShopIcon);

        if (item.GoodsList.size() > 0 && type == 0) {
            viewHolder.mCouponShopRv.setVisibility(View.VISIBLE);
            RedPackRVAdapter adapter = new RedPackRVAdapter(item.CouponId, item.GoodsList, context);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.mCouponShopRv.setLayoutManager(manager);
            viewHolder.mCouponShopRv.setAdapter(adapter);
        } else {
            viewHolder.mCouponShopRv.setVisibility(View.GONE);
        }
        if (item.IsNewPeopleReceive == 1) {
            viewHolder.redPacket.setVisibility(View.VISIBLE);
        } else {
            viewHolder.redPacket.setVisibility(View.GONE);
        }

    }

    private void setText(TextView text, double db) {
        setText(text, db + "");
    }

    private void setText(TextView text, String string) {
        text.setText(string);
    }
}
