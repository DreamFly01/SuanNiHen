package com.fdl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.MenuBean;
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
public class MenuAdapter extends BaseQuickAdapter<MenuBean,BaseViewHolder> {


    public MenuAdapter(int layoutResId, @Nullable List<MenuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuBean item) {
        helper.setText(R.id.tv_name,item.name);
        helper.setImageResource(R.id.iv_logo,item.imgRes);
    }
}
