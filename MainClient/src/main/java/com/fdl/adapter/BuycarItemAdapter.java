package com.fdl.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.bean.BaseResultBean;
import com.fdl.bean.GoodsBean;
import com.fdl.bean.OrderDataBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/14<p>
 * <p>changeTime：2019/1/14<p>
 * <p>version：1<p>
 */
public class BuycarItemAdapter extends CommonAdapter<GoodsBean> {

    private boolean fatherChose = false;
    private int chosePostion = 0;
    private int myPostion;
    private refreshFather refreshFather;

    public BuycarItemAdapter(Context context, int layoutId, List<GoodsBean> datas) {
        super(context, layoutId, datas);
    }

    public BuycarItemAdapter(Context context, int layoutId, List<GoodsBean> datas, int postion) {
        super(context, layoutId, datas);
        this.myPostion = postion;
    }


    @Override
    protected void convert(ViewHolder holder, GoodsBean bean, int position) {
        ImageView chose = holder.getView(R.id.iv_item_chose);
        LinearLayout llChose = holder.getView(R.id.ll_chose);
        ImageView logo = holder.getView(R.id.iv_product_logo);
        ImageView add = holder.getView(R.id.iv_add);
        ImageView delete = holder.getView(R.id.iv_delete);
        TextView num = holder.getView(R.id.tv_num);
        TextView name = holder.getView(R.id.tv_name);
        TextView price = holder.getView(R.id.tv_price);
        TextView suk = holder.getView(R.id.tv_suk);
        final int[] numInt = {0};
        ImageUtils.loadUrlImage(mContext, mDatas.get(myPostion).goodsList.get(position).GoodsImg, logo);
        name.setText(mDatas.get(myPostion).goodsList.get(position).Name);
        price.setText("¥" + mDatas.get(myPostion).goodsList.get(position).SalesPrice);
        if (!StrUtils.isEmpty(mDatas.get(myPostion).goodsList.get(position).NormsInfo)) {
            suk.setText("规格：" + mDatas.get(myPostion).goodsList.get(position).NormsInfo);
        }
        num.setText(mDatas.get(myPostion).goodsList.get(position).Number + "");
        numInt[0] = mDatas.get(myPostion).goodsList.get(position).Number;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numInt[0] += 1;
                num.setText(numInt[0] + "");
                mDatas.get(myPostion).goodsList.get(position).Number = numInt[0];
                updataData(mDatas, myPostion);
                refreshFather.refresh(mDatas);
                updataGoods(bean.Id,numInt[0]);

            }
        });
        if (mDatas.get(myPostion).goodsList.get(position).isChose) {
            chose.setBackgroundResource(R.drawable.pay_selete);
        } else {
            chose.setBackgroundResource(R.drawable.pay_normall);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numInt[0] > 1) {
                    numInt[0] -= 1;
                    num.setText(numInt[0] + "");
                    mDatas.get(myPostion).goodsList.get(position).Number = numInt[0];
                    updataData(mDatas, myPostion);
                    refreshFather.refresh(mDatas);
                    updataGoods(bean.Id,numInt[0]);
                }
            }
        });
        llChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.get(myPostion).goodsList.get(position).isChose) {
                    chose.setBackgroundResource(R.drawable.pay_normall);
                    mDatas.get(myPostion).goodsList.get(position).isChose = false;
                } else {
                    if (mDatas.size() == 1) {
                        fatherChose = true;
                    }
                    chose.setBackgroundResource(R.drawable.pay_selete);
                    mDatas.get(myPostion).goodsList.get(position).isChose = true;
                }
                updataData(mDatas, myPostion);
                refreshFather.refresh(mDatas);
            }
        });
    }

    private List<OrderDataBean> mDatas = new ArrayList<>();
    public void setdata(List<OrderDataBean> datas){
        this.mDatas = datas;
    }
    public void updataData(List<OrderDataBean> datas, int position) {
        mDatas = datas;
        this.myPostion = position;
        notifyDataSetChanged();
    }

    public void setRefreshFather(refreshFather refreshFather){
        this.refreshFather = refreshFather;
    }
    public interface refreshFather {
        void refresh(List<OrderDataBean> datas);
    }

    private void updataGoods(int id,int num){
        RequestClient.UpdataGoodsNum(id,num, mContext, new NetSubscriber<BaseResultBean>(mContext,true) {
            @Override
            public void onResultNext(BaseResultBean model) {

            }
        });
    }
}
