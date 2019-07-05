package com.fdl.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.SuperMarketBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/24<p>
 * <p>changeTime：2019/1/24<p>
 * <p>version：1<p>
 */
public class StroreAdapter extends BaseMultiItemQuickAdapter<SuperMarketBean,BaseViewHolder> {

private int shopType;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public StroreAdapter(List<SuperMarketBean> data) {
        super(data);
        addItemType(SuperMarketBean.NETDATE,R.layout.item_store_layout);
        addItemType(SuperMarketBean.CATCHDATE,R.layout.item_store_layout);
        addItemType(SuperMarketBean.NETDATE_TITLE,R.layout.item_nettitle_layout);
        addItemType(SuperMarketBean.CATCHDATE_TITLE,R.layout.item_catchtitle_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, SuperMarketBean item) {
        ImageView logo = helper.getView(R.id.iv_product_logo1);
        TextView name = helper.getView(R.id.tv_name);
        TextView distance = helper.getView(R.id.tv_distance);
        TextView address = helper.getView(R.id.tv_address1);
        LinearLayout dp = helper.getView(R.id.ll_dp);
        switch (helper.getItemViewType())
        {
            case 1:
                if(shopType == 6){
                    helper.setText(R.id.tv_shop,"到店自取");
                }
                ImageUtils.loadUrlImage(mContext,item.Icon,logo);
                name.setText(item.SupplierName);
                distance.setText(StrUtils.getDistance(item.Distance));
                address.setText(item.Address);
                break;
            case 2:
                if(shopType == 6){
                    helper.setText(R.id.tv_shop,"到店自取");
                }
                ImageUtils.loadUrlImage(mContext,item.Icon,logo);
                name.setText(item.SupplierName);
                distance.setVisibility(View.GONE);
                address.setText(item.Address);
                break;
            case 3:
                break;
            case 4:
                break;
        }
//        dp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
    public void setShopType(int shopType){
        this.shopType = shopType;
    }
}
