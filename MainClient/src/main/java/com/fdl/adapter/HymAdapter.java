package com.fdl.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.WoolBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/9<p>
 * <p>changeTime：2019/1/9<p>
 * <p>version：1<p>
 */
public class HymAdapter extends CommonAdapter<WoolBean.HymBean> {
    public HymAdapter(Context context, int layoutId, List<WoolBean.HymBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, WoolBean.HymBean hymBean, int position) {
        TextView tvMoney = holder.getView(R.id.tv_hym_money);
        ImageView ivLogo = holder.getView(R.id.iv_user_logo);

        ImageUtils.loadUrlCircleImage(mContext,hymBean.WxHeadImg,ivLogo);
        tvMoney.setText(hymBean.BK_BargainPrice+"");
    }
}
