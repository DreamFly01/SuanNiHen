package com.fdl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.WithdrawListBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.fdl.utils.TimeUtils;
import com.sg.cj.snh.R;


import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/16<p>
 * <p>changeTime：2019/4/16<p>
 * <p>version：1<p>
 */
public class WithdrawListAdapter extends BaseQuickAdapter<WithdrawListBean,BaseViewHolder> {
    public WithdrawListAdapter(int layoutResId, @Nullable List<WithdrawListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawListBean item) {
        ImageUtils.loadUrlCircleImage(mContext,item.Icon, (ImageView) helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_content,item.Title);
        helper.setText(R.id.tv_money,StrUtils.moenyToDH(item.Money+""));
        helper.setText(R.id.tv_time,TimeUtils.timeStamp2Date(item.Date+"","yyyy-MM-dd HH:mm:ss"));
    }
}
