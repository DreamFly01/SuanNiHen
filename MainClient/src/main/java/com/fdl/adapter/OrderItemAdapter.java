package com.fdl.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.GoodsBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/4<p>
 * <p>changeTime：2019/1/4<p>
 * <p>version：1<p>
 */
public class OrderItemAdapter extends CommonAdapter<GoodsBean> {
    public OrderItemAdapter(Context context, int layoutId, List<GoodsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsBean bean, int position) {
        ImageView shopLog = holder.getView(R.id.iv_product_logo1);
        TextView tvName = holder.getView(R.id.tv_GoodsName);
        TextView tvSuk = holder.getView(R.id.tv_sku);
        TextView tvSale = holder.getView(R.id.tv_SalesPrice);
        TextView tvNum = holder.getView(R.id.tv_Number1);

        ImageUtils.loadUrlImage(mContext, bean.GoodsImg, shopLog);
        tvName.setText(bean.GoodsName);
        if (StrUtils.isEmpty(bean.NormalInfo)) {
            tvSuk.setText("" );
        }else {
            tvSuk.setText("规格："+bean.NormalInfo);
        }
        tvSale.setText("¥" + bean.SalesPrice);
        tvNum.setText("x" + bean.Number);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }
}
