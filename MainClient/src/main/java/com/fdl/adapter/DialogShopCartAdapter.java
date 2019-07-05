package com.fdl.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.daoBean.CommTenant;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/28<p>
 * <p>changeTime：2019/1/28<p>
 * <p>version：1<p>
 */
public class DialogShopCartAdapter extends BaseQuickAdapter<CommTenant,BaseViewHolder> {

    public DialogShopCartAdapter(int layoutResId, @Nullable List<CommTenant> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommTenant item) {
        helper.setText(R.id.tv_name,item.CommTenantName);
        helper.setText(R.id.tv_price,item.Price*item.total+"");
        helper.setText(R.id.tv_goodsNum,item.total+"");
        helper.addOnClickListener(R.id.iv_delete);
        helper.addOnClickListener(R.id.iv_add);
    }
}
