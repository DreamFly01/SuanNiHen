package com.sg.cj.snh.adaper;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sg.cj.snh.R;
import com.sg.cj.snh.model.response.main.LocalgoodsdataResponse;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/21<p>
 * <p>changeTime：2019/1/21<p>
 * <p>version：1<p>
 */
public class MySgQuickAdapter extends CommonAdapter<LocalgoodsdataResponse.DataBean> {
    public MySgQuickAdapter(Context context, int layoutId, List<LocalgoodsdataResponse.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, LocalgoodsdataResponse.DataBean dataBean, int position) {
        ImageView img = holder.getView(R.id.img_product);
        if (!TextUtils.isEmpty(dataBean.GoodsImg)) {
            Glide.with(mContext).load(dataBean.GoodsImg).into(img);
        } else {
            img.setImageDrawable(null);
        }

       TextView product  = holder.getView(R.id.txt_product);
        product.setText(dataBean.Name);
       TextView price =  holder.getView(R.id.txt_new_price);
        price.setText(dataBean.SalesPrice+"");
        TextView txtOldPrice = holder.getView(R.id.txt_old_price);

        TextView name = holder.getView(R.id.txt_old_price);
        name.setText(dataBean.Name);
        txtOldPrice.setText("" + dataBean.MarketPrice);
        txtOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
    public void addDatas(List<LocalgoodsdataResponse.DataBean> datas){
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
}
