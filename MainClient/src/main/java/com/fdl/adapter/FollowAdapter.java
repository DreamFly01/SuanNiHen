package com.fdl.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.ProductFollowBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/11<p>
 * <p>changeTime：2019/1/11<p>
 * <p>version：1<p>
 */
public class FollowAdapter extends CommonAdapter<ProductFollowBean> {

    private boolean isVisible = false;
    private Map<Integer,Integer> ids = new TreeMap<>();
    public FollowAdapter(Context context, int layoutId, List<ProductFollowBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductFollowBean productFollowBean, int position) {
        TextView name = holder.getView(R.id.tv_name);
        TextView price = holder.getView(R.id.tv_price);
        ImageView logo = holder.getView(R.id.iv_logo);
        ImageView chose = holder.getView(R.id.iv_chose);

        name.setText(productFollowBean.GoodsName);
        price.setText("¥"+productFollowBean.SalesPrice);
        ImageUtils.loadUrlImage(mContext,productFollowBean.CoverImgId,logo);
        if(isVisible){
            chose.setVisibility(View.VISIBLE);
        }else {
            chose.setVisibility(View.GONE);
        }
        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productFollowBean.isChose){
                    chose.setBackgroundResource(R.drawable.pay_normall);
                    productFollowBean.isChose = false;
                    ids.remove(position);
                }else {
                    chose.setBackgroundResource(R.drawable.pay_selete);
                    productFollowBean.isChose = true;
                    ids.put(position,productFollowBean.Id);
                }
            }
        });
    }

    public void addDatas(List<ProductFollowBean> datas){
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
    public void setChoseIsVisible(boolean isVisible){
        this.isVisible = isVisible;
        notifyDataSetChanged();
    }
    public Map<Integer, Integer> getIds(){
        return ids;
    }
}
