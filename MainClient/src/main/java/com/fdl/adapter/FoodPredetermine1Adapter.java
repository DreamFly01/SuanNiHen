package com.fdl.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.activity.goTravel.Prize;
import com.fdl.bean.FoodNumSelectBean;
import com.sg.cj.snh.R;

import java.util.List;
import java.util.Map;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/6/26<p>
 * <p>changeTime：2019/6/26<p>
 * <p>version：1<p>
 */
public class FoodPredetermine1Adapter extends BaseQuickAdapter<FoodNumSelectBean,BaseViewHolder>{
    public FoodPredetermine1Adapter(int layoutResId, @Nullable List<FoodNumSelectBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, FoodNumSelectBean item) {
        TextView tv = helper.getView(R.id.tv_content1);
        helper.setText(R.id.tv_content1,item.content1);
        if (item.isSelect) {
            tv.setBackgroundResource(R.drawable.shape_soild_red_bg);
            tv.setTextColor(mContext.getResources().getColor(R.color.white));

        }else {
            tv.setBackgroundResource(R.drawable.shape_black_30);
            tv.setTextColor(Color.parseColor("#999999"));
        }
    }
}

