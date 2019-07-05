package com.fdl.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdl.bean.IdBean;
import com.fdl.bean.OrderDataBean;
import com.fdl.bean.StoreinfoBean;
import com.fdl.bean.SupplierBean;
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
 * <p>creatTime：2019/1/4<p>
 * <p>changeTime：2019/1/4<p>
 * <p>version：1<p>
 */
public class OrderAdapter extends CommonAdapter<OrderDataBean> {
    private OrderItemAdapter adapter;

    public OrderAdapter(Context context, int layoutId, List<OrderDataBean> datas) {
        super(context, layoutId, datas);
    }

    EditText etMsg;
    StoreinfoBean storeinfoBean;
    List<StoreinfoBean> beanList = new ArrayList<>();

    @Override
    protected void convert(ViewHolder holder, OrderDataBean bean, int position) {
        ImageView ivShopLogo = holder.getView(R.id.iv_shop_logo);
        TextView tvShopName = holder.getView(R.id.tv_shopName);
        TextView tvExpressMoney = holder.getView(R.id.tv_ExpressMoney);
        etMsg = holder.getView(R.id.et_msg);
        TextView etTotalMoney = holder.getView(R.id.tv_TotalMoney1);
        RecyclerView recyclerView = holder.getView(R.id.recyclerView_order);
        adapter = new OrderItemAdapter(mContext, R.layout.item_order_item_layout, bean.goodslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        String imgUrl = bean.ShopLogo.substring(bean.ShopLogo.indexOf("GetResources/") + 13, bean.ShopLogo.length());
        ImageUtils.loadUrlCircleImage(mContext, imgUrl, ivShopLogo);
        tvShopName.setText(bean.ShopName);

        if (StrUtils.isEmpty(bean.ExpressMoney + "")) {

        } else {
            extra = bean.ExpressMoney;
        }
        if (bean.ExpressMoney > 0) {
            tvExpressMoney.setText(bean.ExpressMoney + "");
        } else {
            tvExpressMoney.setText("包邮");

        }

        double TotalMoney = 0;
        for (int i = 0; i < bean.goodslist.size(); i++) {
            TotalMoney = TotalMoney + bean.goodslist.get(i).SalesPrice*bean.goodslist.get(i).Number;
        }
        etTotalMoney.setText("¥" + TotalMoney);

        if (!StrUtils.isEmpty(bean.carId)) {
            storeinfoBean = new StoreinfoBean();
            storeinfoBean.LeaveWord = etMsg.getText().toString().trim();
            storeinfoBean.storeid = bean.SupplierId;
            storeinfoBean.CartIds = bean.carId;
            beanList.add(storeinfoBean);
        }

    }

    List<StoreinfoBean> stroeInfo;

    public String getMsg() {
        return etMsg.getText().toString().trim();
    }

    public List<StoreinfoBean> getStorInfo() {
        stroeInfo = beanList;
        return stroeInfo;
    }

    private double extra;
    public double getExtra() {
        return extra;
    }
}
