package com.fdl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/29<p>
 * <p>changeTime：2019/1/29<p>
 * <p>version：1<p>
 */
public class SupermartOrderAdapter extends BaseQuickAdapter<CommTenant,BaseViewHolder> {

    public SupermartOrderAdapter(int layoutResId, @Nullable List<CommTenant> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommTenant item) {
        ImageUtils.loadUrlImage(mContext,item.CommTenantIcon,helper.getView(R.id.iv_product_logo1));
        helper.setText(R.id.tv_name,item.CommTenantName);
        helper.setText(R.id.tv_num,"x"+item.total);
        helper.setText(R.id.tv_price,"¥"+item.Price);
    }
}
