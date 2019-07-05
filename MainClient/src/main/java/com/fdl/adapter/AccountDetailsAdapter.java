package com.fdl.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.AccountDetailsBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.fdl.utils.TimeUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/18<p>
 * <p>changeTime：2019/1/18<p>
 * <p>version：1<p>
 */
public class AccountDetailsAdapter extends BaseQuickAdapter<AccountDetailsBean,BaseViewHolder> {

    private int type;

    public AccountDetailsAdapter(int layoutResId, @Nullable List<AccountDetailsBean> data) {
        super(layoutResId, data);
    }

    public void setType(int type) {
        this.type = type;
    }
    @Override
    protected void convert(BaseViewHolder helper, AccountDetailsBean item) {
        TextView title = helper.getView(R.id.tv_title);
        TextView from = helper.getView(R.id.tv_from);
        TextView time = helper.getView(R.id.tv_time);
        TextView money = helper.getView(R.id.tv_money);
        ImageView ivLogo = helper.getView(R.id.iv_logo);
        switch (type) {
            case 0:
//                orderNo.setText(accountDetailsBean.Reason);
//                money.setText(accountDetailsBean.Money + "");
//                if (accountDetailsBean.Money > 0) {
//                    money.setTextColor(Color.parseColor("#ff7c00"));
//                } else {
//                    money.setTextColor(Color.GRAY);
//                }
                break;
            //待返金额
            case 1:
                ImageUtils.loadUrlImage(mContext, item.GooodsImg, ivLogo);
                title.setText(item.GooodsName);
                money.setText(item.BargainPrice+"");
                if (item.BargainsType == 1) {
                    from.setText("来源于分享薅羊毛");
                }
                if (item.BargainsType == 0) {
                    from.setText("来源于好友薅羊毛");
                }
                time.setText("返现时间：" + TimeUtils.formatTimeToYMD(item.DTime));
                break;
            //待返积分
            case 2:
                ivLogo.setVisibility(View.GONE);
                title.setText(item.Title);
                from.setVisibility(View.GONE);
                time.setText(TimeUtils.formatTimeToYMD(item.CreateTime));
                if (item.Money > 0) {
                    money.setText("+"+item.Money);
                } else {
                    money.setText(""+item.Money);
                }
//                title.setText(accountDetailsBean.Reason);
//                money.setText(accountDetailsBean.Money + "");
//                if (accountDetailsBean.Money > 0) {
//                    money.setTextColor(Color.parseColor("#ff7c00"));
//                } else {
//                    money.setTextColor(Color.GRAY);
//                }
                break;
        }
//        time.setText(TimeUtils.formatTimeToYMD(accountDetailsBean.CreateTime));
    }
}
