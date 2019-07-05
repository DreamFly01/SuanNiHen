package com.fdl.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.bean.ScrollBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/25<p>
 * <p>changeTime：2019/1/25<p>
 * <p>version：1<p>
 */
public class ScrollRightAdapter extends BaseSectionQuickAdapter<ScrollBean, BaseViewHolder> {
    private int shopType;
    public ScrollRightAdapter(int layoutResId, int sectionHeadResId, List<ScrollBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ScrollBean item) {
        helper.setText(R.id.right_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScrollBean item) {
        ScrollBean.ScrollItemBean t = item.t;
        helper.setText(R.id.tv_name, t.CommTenantName);
        helper.setText(R.id.tv_num, "销量：" + t.OnThePin);
        helper.setText(R.id.tv_price, "¥" + t.Price);
        helper.setText(R.id.tv_suk, "/" + t.UnitsTitle);
        helper.setText(R.id.tv_goodsNum, t.total + "");
        if (t.Inventory > 0) {
            helper.setText(R.id.tv_inventory, "库存：" + t.Inventory);
        } else {
            helper.setText(R.id.tv_inventory, "已售罄");

        }
        ImageUtils.loadUrlImage(mContext, t.CommTenantIcon, helper.getView(R.id.iv_product_logo1));
        if(shopType == 1){
        helper.setVisible(R.id.ll_num,false);
        helper.setVisible(R.id.ll_add,false);
        }else {
            if (t.total > 0) {
                helper.getView(R.id.ll_num).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.ll_num).setVisibility(View.GONE);
            }
        }

        helper.addOnClickListener(R.id.iv_add);
        helper.addOnClickListener(R.id.iv_delete);
        helper.addOnClickListener(R.id.iv_product_logo1);
    }
    public void setType(int type){
        this.shopType = type;
    }
}
