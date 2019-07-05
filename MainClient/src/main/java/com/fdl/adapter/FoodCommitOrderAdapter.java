package com.fdl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.SupplierBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import java.util.List;


/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/6/25<p>
 * <p>changeTime：2019/6/25<p>
 * <p>version：1<p>
 */
public class FoodCommitOrderAdapter extends BaseQuickAdapter<CommTenant,BaseViewHolder> {
    public FoodCommitOrderAdapter(int layoutResId, @Nullable List<CommTenant> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommTenant item) {
        helper.setText(R.id.tv_name,item.CommTenantName);
        helper.setText(R.id.tv_num,"x"+item.total);
        helper.setText(R.id.tv_money,mContext.getResources().getString(R.string.moneyStr)+item.Price);
        ImageUtils.loadUrlImage(mContext,item.CommTenantIcon,helper.getView(R.id.iv_logo));
    }
}
