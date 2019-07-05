package com.fdl.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fdl.activity.order.MyRefundOrderActivity;
import com.fdl.activity.order.RefundOrderDetailsActivity;
import com.fdl.bean.GoodsBean;
import com.fdl.bean.RefundOrderBean;
import com.fdl.utils.JumpUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class MyRefundOrderAdapter extends CommonAdapter<RefundOrderBean> {

    private OrderItemAdapter adapter;
    public MyRefundOrderAdapter(Context context, int layoutId, List<RefundOrderBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, RefundOrderBean reBean, int position) {
        TextView orderNo = holder.getView(R.id.tv_order_no);
        TextView state = holder.getView(R.id.tv_state);
        RecyclerView recyclerView = holder.getView(R.id.item_recyclerview);
        TextView result = holder.getView(R.id.tv_result);
        TextView tvDetails = holder.getView(R.id.tv_details);

        orderNo.setText("订单号" + reBean.seriallist.get(0).OrderNo);
        if (reBean.seriallist.get(0).State == 0) {
            state.setText("处理中");
        }
        if(reBean.seriallist.get(0).State == 1){
            state.setText("已完成");
        }
        result.setText(reBean.seriallist.get(0).Commit);
        tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adapter = new OrderItemAdapter(mContext,R.layout.item_order_item_layout,reBean.goodslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder1, int position1) {
                mOnItemClickListener.onItemClick(holder.getConvertView(),holder,position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("orderNo",reBean.OrderNo);
                JumpUtils.dataJump((Activity) mContext,RefundOrderDetailsActivity.class,bundle,false);
            }
        });
    }
    public void addData(List<RefundOrderBean> list){
        mDatas.addAll(list);
        notifyDataSetChanged();
    }
}
