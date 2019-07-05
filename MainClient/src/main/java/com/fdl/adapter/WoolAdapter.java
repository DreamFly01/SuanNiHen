package com.fdl.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.WoolBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.TimeUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class WoolAdapter extends CommonAdapter<WoolBean> {
    long time1;
    TextView time;
    private SparseArray<CountDownTimer> countDownMap;
    public WoolAdapter(Context context, int layoutId, List<WoolBean> datas) {
        super(context, layoutId, datas);
        countDownMap = new SparseArray<>();
    }


    @Override
    protected void convert(ViewHolder holder, WoolBean woolBean, int position) {
        CountDownTimer countDownTimer = null;

        TextView tvOrder = holder.getView(R.id.tv_order_no);
        TextView tvName = holder.getView(R.id.tv_GoodsName);
        TextView tvNum = holder.getView(R.id.tv_Number1);
        TextView tvSale = holder.getView(R.id.tv_SalesPrice);
        TextView tvSale1 = holder.getView(R.id.tv_SalesPrice1);
        TextView tvSale2 = holder.getView(R.id.tv_BargainPrice);
        ImageView ivLogo = holder.getView(R.id.iv_product_logo1);
        time = holder.getView(R.id.tv_clock);
        TextView hym = holder.getView(R.id.tv_hym);
        tvOrder.setText("订单号：" + woolBean.OrderNo);
        tvName.setText(woolBean.GoodsName);
        tvNum.setText("件数：X" + woolBean.Number);
        tvSale.setText("¥" + woolBean.Cheap + "");
        tvSale1.setText("¥" + woolBean.SalesPrice + "");
        tvSale1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvSale2.setText("已薅羊毛：¥" + woolBean.BargainPrice + "");
        if (woolBean.State == 2) {
            hym.setText("前往分享薅羊毛");
            hym.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            hym.setText("已完成");
        }
        time1 = woolBean.CutDownTime * 60 * 60 * 1000;
        time1-=1000;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(time1, 1000) {
            public void onTick(long millisUntilFinished) {
               time.setText(TimeUtils.formatTime(millisUntilFinished));
//                Log.e("TAG", data.name + " :  " + millisUntilFinished);
            }
            public void onFinish() {
//                holder.timeTv.setText("00:00:00");
//                holder.statusTv.setText(data.name + ":结束");
            }
        }.start();

        countDownMap.put(time.hashCode(),countDownTimer);

        ImageUtils.loadUrlImage(mContext, woolBean.CoverImg, ivLogo);
    }

    public void addData(List<WoolBean> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }


    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
//        Log.e("TAG",  "size :  " + countDownMap.size());
        for (int i = 0,length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

}
