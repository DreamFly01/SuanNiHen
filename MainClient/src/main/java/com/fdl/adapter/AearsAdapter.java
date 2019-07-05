package com.fdl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.AreasBean;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/3/22<p>
 * <p>changeTime：2019/3/22<p>
 * <p>version：1<p>
 */
public class AearsAdapter extends BaseQuickAdapter<AreasBean,BaseViewHolder> {
    private int isClick = -1;
    public AearsAdapter(int layoutResId, @Nullable List<AreasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreasBean item) {
        if(isClick == helper.getPosition()){
            helper.getView(R.id.iv_chose).setBackgroundResource(R.drawable.pay_selete);
        }else{
            helper.getView(R.id.iv_chose).setBackgroundResource(R.drawable.pay_normall);
        }
        helper.setText(R.id.tv_address,item.AddressName);
    }
    public void setIsChose(int position){
        this.isClick = position;
        notifyDataSetChanged();
    }
}
