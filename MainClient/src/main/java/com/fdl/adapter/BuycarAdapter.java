package com.fdl.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.bean.OrderDataBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.JumpUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/14<p>
 * <p>changeTime：2019/1/14<p>
 * <p>version：1<p>
 */
public class BuycarAdapter extends CommonAdapter<OrderDataBean> {
    public BuycarItemAdapter adapter;
    private RefreshFragment refreshFragment;
    private TextItemOnClikListener itemOnClikListener;

    public BuycarAdapter(Context context, int layoutId, List<OrderDataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OrderDataBean bean, int position) {
        ImageView chose = holder.getView(R.id.iv_chose);
        ImageView logo = holder.getView(R.id.iv_shop_logo);
        TextView name = holder.getView(R.id.tv_shop_name);
        RecyclerView recyclerView = holder.getView(R.id.item_recyclerview);

        ImageUtils.loadUrlCircleImage(mContext, bean.SupplierLogo, logo);
        name.setText(bean.SupplierName);
        adapter = new BuycarItemAdapter(mContext, R.layout.item_shopcar_item_layout, bean.goodsList, position);
        adapter.setdata(mDatas);
        adapter.setRefreshFather(new BuycarItemAdapter.refreshFather() {
            @Override
            public void refresh(List<OrderDataBean> datas) {
                mDatas = datas;
                refreshFragment.refresh(mDatas);
                notifyDataSetChanged();
            }
        });

        //商品详情
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position1) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodsId", mDatas.get(position).goodsList.get(position1).GoodsId);
                JumpUtils.dataJump((Activity) mContext, ProductDetailsActivity.class, bundle, false);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (itemOnClikListener != null) {
                    itemOnClikListener.onItemOnClikListener(pos);
                }

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        boolean storeChoose = true;
        for (int i = 0; i < bean.goodsList.size(); i++) {
            storeChoose = storeChoose && bean.goodsList.get(i).isChose;
        }
        if (storeChoose) {
            chose.setBackgroundResource(R.drawable.pay_selete);
        } else {
            chose.setBackgroundResource(R.drawable.pay_normall);
        }
        boolean finalStoreChoose = storeChoose;
        final double[] totalMoney = {0};
        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < bean.goodsList.size(); i++) {
                    mDatas.get(position).goodsList.get(i).isChose = !finalStoreChoose;
                }
                notifyDataSetChanged();
                refreshFragment.refresh(mDatas);
                updataDatas(mDatas);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void updataDatas(List<OrderDataBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void setRefreshFragment(RefreshFragment refreshFragment) {
        this.refreshFragment = refreshFragment;
    }

    public interface RefreshFragment {
        void refresh(List<OrderDataBean> datas);
//        void  refreshMoney(double[] money);
    }

    public interface TextItemOnClikListener {
        void onItemOnClikListener(int position);
    }

    public void setItemOnClikListener(TextItemOnClikListener onClikListener) {
        this.itemOnClikListener = onClikListener;
    }
}
