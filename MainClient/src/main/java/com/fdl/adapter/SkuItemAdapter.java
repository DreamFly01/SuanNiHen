package com.fdl.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fdl.bean.ProductDetailsBean;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.w3c.dom.Text;

import java.util.List;

import static com.sg.cj.snh.R.color.white;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/25<p>
 * <p>changeTime：2018/12/25<p>
 * <p>version：1<p>
 */
public class SkuItemAdapter extends CommonAdapter<ProductDetailsBean.ShopNormsValuesListView> {
private int flag;
private boolean isClick = false;

    public SkuItemAdapter(Context context, int layoutId, List<ProductDetailsBean.ShopNormsValuesListView> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return super.onCreateViewHolder(null, viewType);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetailsBean.ShopNormsValuesListView shopNormsValuesListView, int position) {
        TextView name = holder.getView(R.id.tv_item_name);
        name.setText(mDatas.get(position).NormValue);
        if(isClick){

        if(flag==position){
            name.setBackgroundResource(R.drawable.shape_red_bg);
            name.setTextColor(Color.WHITE);
        }else {
            name.setBackgroundResource(R.drawable.shape_gray_reangle_bg);
            name.setTextColor(Color.BLACK);
        }
        }

    }
    public void setFlag(int flag){
        this.flag = flag;
        this.notifyDataSetChanged();
    }
    public void setClick(boolean isClick){
        this.isClick = isClick;
        this.notifyDataSetChanged();
    }
}
