package com.fdl.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.aries.ui.view.radius.RadiusLinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.daoBean.MyBankBean;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;
import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/2<p>
 * <p>changeTime：2019/4/2<p>
 * <p>version：1<p>
 */
public class MyBanksAdapter extends BaseQuickAdapter<MyBankBean, BaseViewHolder> {
    public MyBanksAdapter(int layoutResId, @Nullable List<MyBankBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBankBean item) {
        ImageUtils.loadUrlCircleImage(mContext, item.Logo, (ImageView) helper.getView(R.id.iv_bank_bg));
        helper.setText(R.id.tv_bank_name, item.BankTypeName);

        helper.setText(R.id.tv_bank_no, StrUtils.hideCardNo(item.BankCardNo));
        RadiusLinearLayout bankBg = helper.getView(R.id.ll_bank);
        bankBg.getDelegate().setBackgroundColor(Color.parseColor(item.Color));
        helper.addOnClickListener(R.id.btnDelete);

    }
}
