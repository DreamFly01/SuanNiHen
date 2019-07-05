package com.fdl.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.FoodNumSelectBean;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/6/26<p>
 * <p>changeTime：2019/6/26<p>
 * <p>version：1<p>
 */
public class FoodPredetermine2Adapter extends BaseQuickAdapter<FoodNumSelectBean,BaseViewHolder>{
    public FoodPredetermine2Adapter(int layoutResId, @Nullable List<FoodNumSelectBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, FoodNumSelectBean item) {
        TextView tv1 = helper.getView(R.id.tv_content1);
        TextView tv2 = helper.getView(R.id.tv_content2);
        LinearLayout ll = helper.getView(R.id.ll_item);
        helper.setText(R.id.tv_content1,item.content1);
        tv2.setText(item.content2);
        if(!StrUtils.isEmpty(item.content2)){
            tv2.setVisibility(View.VISIBLE);
        }
        if (item.isSelect) {
            ll.setBackgroundResource(R.drawable.shape_soild_red_bg);
            tv1.setTextColor(mContext.getResources().getColor(R.color.white));
            tv2.setTextColor(mContext.getResources().getColor(R.color.white));

        }else {
            ll.setBackgroundResource(R.drawable.shape_black_30);
            tv1.setTextColor(Color.parseColor("#999999"));
            tv2.setTextColor(Color.parseColor("#999999"));
        }
    }
}

