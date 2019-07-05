package com.fdl.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.MainProcuctBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/12<p>
 * <p>changeTime：2019/2/12<p>
 * <p>version：1<p>
 */
public class MainProductAdapter extends BaseQuickAdapter<MainProcuctBean,BaseViewHolder> {
    public MainProductAdapter(int layoutResId, @Nullable List<MainProcuctBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainProcuctBean item) {
        ImageView logo = helper.getView(R.id.iv_logo);
        TextView marketPrice = helper.getView(R.id.tv_price1);
        helper.setText(R.id.tv_name,item.Name);
        helper.setText(R.id.tv_price,"¥"+item.SalesPrice);
        ImageUtils.loadUrlCorners(mContext,item.GoodsImg,logo);
        marketPrice.setText("¥"+item.MarketPrice+"");
        marketPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        if(item.SalesPrice>=item.MarketPrice){
            marketPrice.setVisibility(View.GONE);
        } else{
            marketPrice.setVisibility(View.VISIBLE);
        }

    }

}
