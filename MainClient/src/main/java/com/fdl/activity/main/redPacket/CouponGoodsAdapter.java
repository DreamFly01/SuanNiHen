package com.fdl.activity.main.redPacket;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fdl.activity.main.redPacket.bean.CouponsGoodsBean;
import com.fdl.utils.ImageUtils;
import com.sg.cj.snh.R;

import java.util.List;

/**
 * @author 陈自强
 * @date 2019/8/1
 */
public class CouponGoodsAdapter extends BaseQuickAdapter<CouponsGoodsBean.DataBean.ListBean, BaseViewHolder> {

    public CouponGoodsAdapter(int layoutResId,@NonNull List<CouponsGoodsBean.DataBean.ListBean> data) {
        super(layoutResId,data);

    }

    @Override
    protected void convert(BaseViewHolder helper, CouponsGoodsBean.DataBean.ListBean item) {
        ImageView logo = helper.getView(R.id.iv_product_logo1);
        TextView name = helper.getView(R.id.tv_name);
        TextView distance = helper.getView(R.id.tv_distance);
        TextView address = helper.getView(R.id.tv_address1);
        TextView tvShop = helper.getView(R.id.tv_shop);

        ImageUtils.loadUrlImage(mContext,item.ShopLogo,logo);
        name.setText(item.ShopName);
        address.setText(item.Address);
        distance.setText(item.Distance+"元");
        tvShop.setText("进店购买");
    }
}
