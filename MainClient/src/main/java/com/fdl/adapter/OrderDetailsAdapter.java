package com.fdl.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.GoodsBean;
import com.fdl.bean.OrderDetailsBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/7<p>
 * <p>changeTime：2019/1/7<p>
 * <p>version：1<p>
 */
public class OrderDetailsAdapter extends CommonAdapter<GoodsBean> {
    public OrderDetailsAdapter(Context context, int layoutId, List<GoodsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsBean goodBean, int position) {
        TextView name = holder.getView(R.id.tv_GoodsName);
        TextView suk = holder.getView(R.id.tv_sku);
        TextView price = holder.getView(R.id.tv_SalesPrice);
        TextView num = holder.getView(R.id.tv_Number1);
        ImageView logo = holder.getView(R.id.iv_product_logo1);

        ImageUtils.loadUrlImage(mContext, goodBean.GoodsImg, logo);
        name.setText(goodBean.Name);
        if (StrUtils.isEmpty(goodBean.NormsInfo)) {
            suk.setVisibility(View.GONE);
        } else {
            suk.setText("规格：" + goodBean.NormsInfo);
        }
        price.setText("¥" + goodBean.SalesPrice);
        num.setText("x" + goodBean.Number);
    }
}
