package com.fdl.activity.main.redPacket;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fdl.activity.main.redPacket.bean.RedPacketListBean;
import com.fdl.jpush.Logger;
import com.fdl.wedgit.GlideRoundTransform;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * @author 陈自强
 * @date 2019/7/29
 */
public class RedPacketListAdepter extends BaseAdapter {
    private List<RedPacketListBean> list;
    private RedPacketActivity context;

    public RedPacketListAdepter(List<RedPacketListBean> list, RedPacketActivity context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RedPacketListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.red_packet_content_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.showVIew(getItem(position));

//        holder.useBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putInt(Constants.STROE_ID, getItem(position).getID());
//                Intent intent = new Intent(context, StoreDetailsActivity.class);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//                context.close();
//            }
//        });

        holder.mRedPacketShopShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).setShowGoods(!getItem(position).isShowGoods());
                notifyDataSetChanged();
                Logger.i("test", "position=" + position);
            }
        });

        return convertView;
    }


    private class ViewHolder {
        ImageView mRedPacketShopIcon;
        TextView mRedPacketShopName;
        TextView mRedPacketPrice;
        TextView mRedPacketSize;
        ImageButton mRedPacketShopShow;
        RecyclerView mRedPacketShopRv;
        TextView useBtn;
        TextView redType;

        public ViewHolder(View convertView) {
            mRedPacketShopIcon = (ImageView) convertView.findViewById(R.id.red_packet_shop_icon);
            mRedPacketShopName = (TextView) convertView.findViewById(R.id.red_packet_shop_name);
            mRedPacketPrice = (TextView) convertView.findViewById(R.id.red_packet_price);
            mRedPacketSize = (TextView) convertView.findViewById(R.id.red_packet_size);
            mRedPacketShopShow = (ImageButton) convertView.findViewById(R.id.red_packet_shop_show);
            mRedPacketShopRv = (RecyclerView) convertView.findViewById(R.id.red_packet_shop_rv);
            useBtn = convertView.findViewById(R.id.red_packet_use_btn);
            redType = convertView.findViewById(R.id.red_packet_type);
        }

        private void showVIew(RedPacketListBean bean) {
//            Glide.with(mRedPacketShopIcon).load(bean.getSupplierID())
            mRedPacketShopName.setText(bean.getName());
            mRedPacketPrice.setText("" + bean.getPrice() + "");
            StringBuffer stringBuffer = new StringBuffer("优惠券");
            //礼包类型 int  4平台 3全国 2供应商指定商品 1本地
            switch (bean.getWay()) {
                case 1:
                    stringBuffer.insert(0, "本地");
                    break;
                case 2:
                    stringBuffer.insert(0, "指定商品");
                    break;
                case 3:
                    stringBuffer.insert(0, "全国");
                    break;
                case 4:
                    stringBuffer.insert(0, "平台");
                    break;
                default:
                    break;
            }

            mRedPacketSize.setText(stringBuffer.toString());

            GlideRoundTransform roundedCorners = new GlideRoundTransform(context);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(context).load(bean.getShopLogo()).apply(options).into(mRedPacketShopIcon);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRedPacketShopRv.setLayoutManager(manager);

            if (bean.getGoodsList().size() > 0) {
                redType.setText("可用券商品");
                mRedPacketShopShow.setVisibility(View.VISIBLE);
                if (bean.isShowGoods()) {
                    RedPackRVAdapter rvAdapter = new RedPackRVAdapter(bean.getID(),bean.getGoodsList(), context);
                    mRedPacketShopRv.setAdapter(rvAdapter);
                    mRedPacketShopRv.setVisibility(View.VISIBLE);
                    mRedPacketShopShow.setImageResource(R.drawable.pull_back_btn);
                } else {
                    mRedPacketShopRv.setVisibility(View.GONE);
                    mRedPacketShopShow.setImageResource(R.drawable.loading_more_btn);
                }
            } else {
                mRedPacketShopShow.setVisibility(View.GONE);
                mRedPacketShopRv.setVisibility(View.GONE);
                redType.setText("商铺优惠券");
            }

        }

    }

}
