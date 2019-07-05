package com.fdl.adapter.foodadapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.GoodsBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 美食店铺-订单管理
 */
public class FoodOrderManageItemAdapter extends CommonAdapter<GoodsBean> {
    public FoodOrderManageItemAdapter(Context context, int layoutId, List<GoodsBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsBean goodsBean, int position) {
        ImageView logo = holder.getView(R.id.iv_product_logo1);
        TextView name = holder.getView(R.id.tv_GoodsName);
        TextView suk = holder.getView(R.id.tv_sku);
        TextView price = holder.getView(R.id.tv_SalesPrice);
        TextView num = holder.getView(R.id.tv_Number1);

        ImageUtils.loadUrlImage(mContext, goodsBean.GoodsImg, logo);
        name.setText(goodsBean.Name);
        suk.setText(goodsBean.NormsInfo);
        price.setText("¥" + goodsBean.SalesPrice + "");
        num.setText("x" + goodsBean.Number);
    }
}
