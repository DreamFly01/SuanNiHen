package com.fdl.activity.main.redPacket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fdl.activity.main.redPacket.bean.GoodsListBean;
import com.fdl.activity.main.redPacket.bean.RedPackedCouponsConstants;
import com.fdl.activity.supermarket.StoreDetailsActivity;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.GlideRoundTransform;
import com.sg.cj.snh.R;
import com.sg.cj.snh.constants.Constants;

import java.util.List;

/**
 * @author 陈自强
 * @date 2019/7/29
 */
public class RedPackRVAdapter extends RecyclerView.Adapter<RedPackRVAdapter.RedViewHolder> {

    private List<GoodsListBean> beans;
    private RedPacketActivity context;
    private Context mContext;
    private int couponId;

    public RedPackRVAdapter(int couponId,List<GoodsListBean> beans, Context mContext) {
        this.couponId = couponId;
        this.beans = beans;
        this.mContext = mContext;
    }

    public RedPackRVAdapter(int couponId,List<GoodsListBean> beans, RedPacketActivity context) {
        this.beans = beans;
        this.context = context;
        this.couponId = couponId;
    }

    @NonNull
    @Override
    public RedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = View.inflate(parent.getContext(), R.layout.red_packet_shop_item, null);
        RedViewHolder viewHolder = new RedViewHolder(convertView);
        viewHolder.mRedPacketGoodsIcon = (ImageView) convertView.findViewById(R.id.red_packet_goods_icon);
        viewHolder.mRedPacketGoodsName = (TextView) convertView.findViewById(R.id.red_packet_goods_name);
        viewHolder.mRedPacketGoodsPrice = (TextView) convertView.findViewById(R.id.red_packet_goods_price);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RedViewHolder holder, int position) {

        if (context != null) {
            Glide.with(context).load(beans.get(position)
                    .getImages())
                    .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context)))
                    .into(holder.mRedPacketGoodsIcon);
            holder.mRedPacketGoodsPrice.setText(StrUtils.fromResources(context, R.string.price_yuan, beans.get(position).getSalesPrice()));
        }else {
            Glide.with(mContext).load(beans.get(position)
                    .getImages())
                    .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context)))
                    .into(holder.mRedPacketGoodsIcon);
            holder.mRedPacketGoodsPrice.setText(StrUtils.fromResources(mContext, R.string.price_yuan, beans.get(position).getSalesPrice()));

        }
        holder.mRedPacketGoodsName.setText(beans.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(RedPackedCouponsConstants.COUPON_ID, couponId);
                bundle.putString(RedPackedCouponsConstants.GOODS_NAME, beans.get(position).getName());

                if (context != null) {
                    Intent intent = new Intent(context, CouponGoodsActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    context.close();
                }else {
                    Intent intent = new Intent(mContext, CouponGoodsActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public static class RedViewHolder extends RecyclerView.ViewHolder {
        ImageView mRedPacketGoodsIcon;
        TextView mRedPacketGoodsName;
        TextView mRedPacketGoodsPrice;
        View itemView;

        public RedViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
