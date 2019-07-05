package com.fdl.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.RefundOrderBean;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class RefundDetailsAdapter extends CommonAdapter<RefundOrderBean.SerBean> {
    public RefundDetailsAdapter(Context context, int layoutId, List<RefundOrderBean.SerBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, RefundOrderBean.SerBean serBean, int position) {
        TextView tvContent = holder.getView(R.id.tv_content);
        TextView tvTime = holder.getView(R.id.tv_content_time);
        ImageView ivState = holder.getView(R.id.iv_state);
        ImageView ivLine = holder.getView(R.id.iv_state_line);

        tvContent.setText(serBean.Commit);
        tvTime.setText(serBean.CreateTime);
        if(position == 0){
            tvContent.setTextColor(Color.parseColor("#A4D288"));
            tvTime.setTextColor(Color.parseColor("#A4D288"));
            ivState.setBackgroundResource(R.drawable.is_frist_bg);
        }else {
            tvContent.setTextColor(Color.parseColor("#999999"));
            tvTime.setTextColor(Color.parseColor("#999999"));
            ivState.setBackgroundResource(R.drawable.no_frist_bg);
        }
        if(position == mDatas.size()-1){
            ivLine.setVisibility(View.GONE);
        }
    }
}
