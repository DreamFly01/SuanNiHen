package com.fdl.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.GoodsBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/10<p>
 * <p>changeTime：2019/1/10<p>
 * <p>version：1<p>
 */
public class ShopAdapter extends BaseQuickAdapter<GoodsBean,BaseViewHolder> {

    public ShopAdapter(int layoutResId, @Nullable List<GoodsBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        TextView name = helper.getView(R.id.tv_name);
        TextView price1 = helper.getView(R.id.tv_price);
        TextView price2  = helper.getView(R.id.tv_price1);
        ImageView logo = helper.getView(R.id.iv_logo1);

        name.setText(item.Name);
        if(item.SalesPrice>=item.MarketPrice){
            price2.setVisibility(View.INVISIBLE);
        }else {
            price2.setVisibility(View.VISIBLE);
        }
        price1.setText("¥"+StrUtils.moenyToDH(item.SalesPrice+""));
        price2.setText("¥"+StrUtils.moenyToDH(item.MarketPrice+""));
        price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ImageUtils.loadUrlCorners(mContext,item.GoodsImg,logo);
    }
}
