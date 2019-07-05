package com.fdl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.CouponsBean;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/10<p>
 * <p>changeTime：2019/5/10<p>
 * <p>version：1<p>
 */
public class MyCouponsAdapter extends BaseQuickAdapter<CouponsBean,BaseViewHolder> {
    public MyCouponsAdapter(int layoutResId, @Nullable List<CouponsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponsBean item) {
        helper.setText(R.id.tv_money,item.CouponValue+"");
        helper.setText(R.id.tv_money1,"满"+item.ConditionValue+"元使用");
        helper.setText(R.id.tv_time,item.BeginDate+"-"+item.EndDate);
        ImageView ivSelect = helper.getView(R.id.iv_is_select);
        if(from == 1){
            helper.setText(R.id.tv_state,"点击使用");
        }else {
            switch (item.UseState)
            {
                case 0:
                    helper.setText(R.id.tv_state,"未使用");
                    break;
                case 1:
                    helper.setText(R.id.tv_state,"已使用");
                    break;
                case 2:
                    helper.setText(R.id.tv_state,"已过期");
                    break;
            }
        }

        if(item.UseState==2){
            helper.getView(R.id.ll_left).setBackgroundResource(R.drawable.product_coupons_right_gray_bg);
            helper.getView(R.id.ll_right).setBackgroundResource(R.drawable.product_coupons_left_gray_bg);
        }else {
            if(item.CouponWay==1){
                helper.getView(R.id.ll_left).setBackgroundResource(R.drawable.store_coupons_right_red_bg);
                helper.getView(R.id.ll_right).setBackgroundResource(R.drawable.store_coupons_left_red_bg);
            }else if(item.CouponWay == 2){
                helper.getView(R.id.ll_left).setBackgroundResource(R.drawable.product_coupons_right_blue_bg);;
                helper.getView(R.id.ll_right).setBackgroundResource(R.drawable.product_coupson_left_blue_bg);
            }
        }
    }
    private int from;
    public void setFrom(int from){
        this.from = from;
    }
}
