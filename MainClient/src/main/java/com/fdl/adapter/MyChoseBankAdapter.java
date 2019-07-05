package com.fdl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.daoBean.MyBankBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/2<p>
 * <p>changeTime：2019/4/2<p>
 * <p>version：1<p>
 */
public class MyChoseBankAdapter extends BaseQuickAdapter<MyBankBean,BaseViewHolder> {
    public MyChoseBankAdapter(int layoutResId, @Nullable List<MyBankBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBankBean item) {
        helper.setText(R.id.tv_bank_name,item.BankTypeName+" "+item.BankCardNo.substring(item.BankCardNo.length()-4,item.BankCardNo.length()));
        ImageUtils.loadUrlImage(mContext,item.Logo, (ImageView) helper.getView(R.id.iv_bank_logo));
        ImageView chose  = helper.getView(R.id.bank_chose);
        if(item.isChose){
            chose.setBackgroundResource(R.drawable.pay_selete);
        }else {
            chose.setBackgroundResource(R.drawable.pay_normall);
        }
    }
}
