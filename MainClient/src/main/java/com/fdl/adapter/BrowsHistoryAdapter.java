package com.fdl.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.GoodsBean;
import com.fdl.bean.ProductFollowBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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
public class BrowsHistoryAdapter extends CommonAdapter<GoodsBean> {

    private boolean isVisible = false;
    private Map<Integer,Integer> ids = new TreeMap<>();
    public BrowsHistoryAdapter(Context context, int layoutId, List<GoodsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsBean bean, int position) {
        TextView name = holder.getView(R.id.tv_name);
        TextView price = holder.getView(R.id.tv_price);
        ImageView logo = holder.getView(R.id.iv_logo);
        ImageView chose = holder.getView(R.id.iv_chose);

        name.setText(bean.GoodsName);
        price.setText("¥"+bean.SalesPrice);
        ImageUtils.loadUrlImage(mContext,bean.CoverImgId,logo);
        if(isVisible){
            chose.setVisibility(View.VISIBLE);
        }else {
            chose.setVisibility(View.GONE);
        }
        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean.isChose){
                    chose.setBackgroundResource(R.drawable.pay_normall);
                    bean.isChose = false;
                    ids.remove(position);
                }else {
                    chose.setBackgroundResource(R.drawable.pay_selete);
                    bean.isChose = true;
                    ids.put(position,bean.Id);
                }
            }
        });
    }

    public void addDatas(List<GoodsBean> datas){
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
