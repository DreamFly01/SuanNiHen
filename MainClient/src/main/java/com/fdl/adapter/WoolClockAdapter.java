package com.fdl.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.bean.WoolBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.TimeUtils;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/9<p>
 * <p>changeTime：2019/1/9<p>
 * <p>version：1<p>
 */
public class WoolClockAdapter extends RecyclerView.Adapter<WoolClockAdapter.ViewHolder> {

    private List<WoolBean> mDatas = new ArrayList<>();
    private SparseArray<CountDownTimer> countDownMap;
    private Context mContext;
    private onItemClick onItemClick;
    private long time1;
    public WoolClockAdapter(Context context,List<WoolBean> mDatas) {
        this.mDatas = mDatas;
        this.mContext = context;
        countDownMap = new SparseArray<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wooll_layout, parent, false);
        return new ViewHolder(view);
    }
    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        for (int i = 0,length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvOrder.setText("订单号：" + mDatas.get(position).OrderNo);
        holder.tvName.setText(mDatas.get(position).GoodsName);
        holder.tvNum.setText("件数：X" + mDatas.get(position).Number);
        holder.tvSale.setText("¥" + mDatas.get(position).SalesPrice + "");
        holder.tvSale1.setText("¥" + mDatas.get(position).MarketPrice+ "");
        holder.tvSale1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvSale2.setText("已薅羊毛：¥" + mDatas.get(position).BargainPrice + "");

        if (mDatas.get(position).State == 2) {
            holder.hym.setText("前往分享薅羊毛");
            time1 = mDatas.get(position).CutDownTime * 60 * 60 * 1000;
            time1-=1000;
            if (holder.countDownTimer != null) {
                holder.countDownTimer.cancel();
            }
            holder.countDownTimer = new CountDownTimer(time1, 1000) {
                public void onTick(long millisUntilFinished) {
                    holder.time.setText(TimeUtils.formatTime(millisUntilFinished));
                }
                public void onFinish() {
                    holder.time.setText("已结束");
                }
            }.start();
            countDownMap.put(holder.time.hashCode(),holder.countDownTimer);
            holder.hym.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            holder.hym.setText("已完成");
//            holder.hym.setVisibility(View.GONE);
            holder.time.setText("活动已结束");
            holder.time.setVisibility(View.GONE);
            holder.ivClock.setVisibility(View.GONE);
        }
        holder.hym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(position);
            }
        });
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(position);
            }
        });

        ImageUtils.loadUrlImage(mContext, mDatas.get(position).CoverImg, holder.ivLogo);
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && !mDatas.isEmpty()) {
            return mDatas.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvOrder ;
        TextView tvName ;
        TextView tvNum ;
        TextView tvSale ;
        TextView tvSale1 ;
        TextView tvSale2 ;
        ImageView ivLogo ;
        TextView time ;
        TextView hym ;
        ImageView ivClock;
        LinearLayout ll_item;
        public CountDownTimer countDownTimer;
        public ViewHolder(View itemView) {
            super(itemView);
             tvOrder = (TextView) itemView.findViewById(R.id.tv_order_no);
             tvName = (TextView) itemView.findViewById(R.id.tv_GoodsName);
             tvNum = (TextView) itemView.findViewById(R.id.tv_Number1);
             tvSale = (TextView) itemView.findViewById(R.id.tv_SalesPrice);
             tvSale1 = (TextView) itemView.findViewById(R.id.tv_SalesPrice1);
             tvSale2 = (TextView) itemView.findViewById(R.id.tv_BargainPrice);
             ivLogo = (ImageView) itemView.findViewById(R.id.iv_product_logo1);
             time = (TextView) itemView.findViewById(R.id.tv_clock);
             hym = (TextView) itemView.findViewById(R.id.tv_hym);
             ivClock = (ImageView)itemView.findViewById(R.id.iv_clock);
             ll_item = (LinearLayout)itemView.findViewById(R.id.ll_item_hym);
        }
    }


    public void addData(List<WoolBean> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }
    public void setNewData(List<WoolBean> list){
        mDatas = list;
        notifyDataSetChanged();
    }
    public void setOnClick(onItemClick click){
        this.onItemClick = click;
        notifyDataSetChanged();
    }
    public interface onItemClick{
        void onClick(int position);
    }
}
