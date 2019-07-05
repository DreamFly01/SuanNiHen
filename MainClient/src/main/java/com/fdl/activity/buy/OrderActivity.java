package com.fdl.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.account.AddressActivity;
import com.fdl.adapter.OrderAdapter;
import com.fdl.bean.AddressBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.GoodsBean;
import com.fdl.bean.IdBean;
import com.fdl.bean.OrderBean;
import com.fdl.bean.OrderDataBean;
import com.fdl.bean.StoreinfoBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.FinishActivityManager;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.sg.cj.snh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/27<p>
 * <p>changeTime：2018/12/27<p>
 * <p>version：1<p>
 */
public class OrderActivity extends BaseActivity {

    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.tv_name_phone)
    TextView tvNamePhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_noAddress)
    LinearLayout llNoAddress;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_TotalMoney)
    TextView tvTotalMoney;

    @BindView(R.id.ll_change_address)
    LinearLayout changAddress;
    private String goodsId, GoodsNum, GoodsNormId, shopLogo, CoverImg, GoodsNormName, totalPrice;
    private Bundle bundle;
    private DialogUtils dialogUtils;
    private String carId;
    private OrderAdapter adapter;
    private double money;
    private List<IdBean> idBeanList = new ArrayList<>();
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_layout);
        bundle = this.getIntent().getExtras();
        if (null != bundle) {
            goodsId = bundle.getString("goodsId");
            GoodsNum = bundle.getString("GoodsNum");
            GoodsNormId = bundle.getString("GoodsNormId");
            shopLogo = bundle.getString("shopLogo");
            CoverImg = bundle.getString("CoverImg");
            carId = bundle.getString("carId");
            money = bundle.getDouble("money");
            GoodsNormName = bundle.getString("NormsInfo");
            totalPrice = bundle.getString("totalPrice");
            idBeanList = bundle.getParcelableArrayList("idBeans");
//            if (!StrUtils.isEmpty(carId)) {
//                supplierBeanlist = bundle.getParcelableArrayList("supplierBeanlist");
//            }
        }
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        TextView title = (TextView) findViewById(R.id.heard_title);
        title.setText("确认订单");
        avi.setVisibility(View.VISIBLE);
        tvTotalMoney.setText(StrUtils.moenyToDH(totalPrice));
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetOrderData(GoodsNormName, goodsId, GoodsNum, GoodsNormId, carId, this, new NetSubscriber<BaseResultBean<OrderBean>>(this, false) {
            @Override
            public void onResultNext(BaseResultBean<OrderBean> orderModle) {
                //判断是否从购物车过来，如果是，则获取购物车信息
                avi.setVisibility(View.GONE);
                if (!StrUtils.isEmpty(carId)) {
                    supplierBeanlist = orderModle.data.supplierlist;
                    fillView(orderModle.data, supplierBeanlist);
                } else {
                    fillView(orderModle.data, null);
                }

            }
        }));

    }

    private List<OrderDataBean> supplierBeanlist = new ArrayList<>();
    private List<GoodsBean> goodslistBeans = new ArrayList<>();

    private void fillView(OrderBean bean, List<OrderDataBean> supplierBeanlist1) {
        if (!StrUtils.isEmpty(bean.ReceiveName)) {
            llAddress.setVisibility(View.VISIBLE);
            llNoAddress.setVisibility(View.GONE);
            tvNamePhone.setText(bean.ReceiveName + "         " + bean.ReceiveTel);
            tvAddress.setText(bean.AreaAddress + "-" + bean.DetailAddress);
            addressId = bean.AddressId + "";

        } else {
            llAddress.setVisibility(View.GONE);
            llNoAddress.setVisibility(View.VISIBLE);
            addressId = "no";
        }
        //直接下单过来填充数据
        if (StrUtils.isEmpty(carId)) {
            OrderDataBean orderDataBean = new OrderDataBean();
            GoodsBean goodslistBean = new GoodsBean();
            goodslistBean.NormsInfo = GoodsNormName;
            goodslistBean.SalesPrice = bean.SalesPrice;
            goodslistBean.GoodsImg = bean.GoodsImg;
            goodslistBean.GoodsName = bean.GoodsName;
            goodslistBean.Number = bean.Number;
            goodslistBean.GoodsImg = CoverImg;
            goodslistBeans.add(goodslistBean);
            orderDataBean.SupplierId = bean.SupplierId;
            orderDataBean.ExpressMoney = bean.ExpressMoney;
            orderDataBean.ShopLogo = bean.ShopLogo;
            orderDataBean.ShopName = bean.ShopName;
            orderDataBean.goodslist = goodslistBeans;
            supplierBeanlist.add(orderDataBean);
            adapter = new OrderAdapter(OrderActivity.this, R.layout.item_order_layout, supplierBeanlist);
            tvTotalMoney.setText(bean.TotalMoney + adapter.getExtra() + "");
            recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
            recyclerView.setAdapter(adapter);
        } else {
            //从购物车过来 填充数据
//            String[] ids = StringUtils.split(carId, ",");
//            List<String> idlist = new ArrayList<>();
//            StringBuffer id;
//            int index = 0;
//            for (int i = 0; i < supplierBeanlist1.size(); i++) {
//                id = new StringBuffer();
//                index = index + supplierBeanlist1.get(i).goodslist.size();
//                for (int j = 0; j < supplierBeanlist1.get(i).goodslist.size(); j++) {
//                    if (i == 0) {
//                        id = id.append(",").append(ids[j]);
//                    } else {
//                        id = id.append(",").append(ids[index]);
//                    }
//                }
//                supplierBeanlist1.get(i).carId = id.toString();
//
//            }
//            for (int i = 0; i < idBeanList.size(); i++) {
//                for (int j = 0; j < supplierBeanlist1.size(); j++) {
//                    if(idBeanList.get(i).id == supplierBeanlist1.get(j).SupplierId){
//                        supplierBeanlist1.get(i).carId = idBeanList.get(i).carId;
//                    }
//                }
//            }
            for (int i = 0; i < supplierBeanlist1.size(); i++) {
                for (int j = 0; j < idBeanList.size(); j++) {
                    if(supplierBeanlist1.get(i).SupplierId == idBeanList.get(j).id){
                        supplierBeanlist1.get(i).carId = idBeanList.get(j).carId;
                    }
                }
            }
            adapter = new OrderAdapter(OrderActivity.this, R.layout.item_order_layout, supplierBeanlist1);
            adapter.notifyItemChanged(supplierBeanlist1.size());
            recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
            recyclerView.setAdapter(adapter);
        }
    }

    @OnClick({R.id.ll_change_address, R.id.tv_add_my_address, R.id.tv_commit, R.id.heard_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_change_address:
                Intent intent = new Intent(OrderActivity.this, AddressActivity.class);
                intent.putExtra("order", "order");
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_add_my_address:
                Intent intent1 = new Intent(OrderActivity.this, AddressActivity.class);
                intent1.putExtra("order", "order");
                startActivityForResult(intent1, 0);
                break;
            case R.id.tv_commit:
                if (!"no".equals(addressId)) {
                    postOrderData();
                } else {
                    this.dialogUtils.noBtnDialog("请添加地址");
                }
                break;
            case R.id.heard_back:
                this.finish();
                break;
        }
    }

    //提交订单
    private String addressId, LeaveWord;
    private int id;

    private void postOrderData() {
        if (StrUtils.isEmpty(carId)) {
            LeaveWord = adapter.getMsg();
            addSubscription(RequestClient.PostOrderData(addressId, goodsId, GoodsNum, GoodsNormId, LeaveWord, this, new NetSubscriber<BaseResultBean>(this, true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    bundle = new Bundle();
                    bundle.putString("TotalOrderNo", model.TotalOrderNo);
                    bundle.putString("Balance", model.Balance + "");
                    bundle.putString("TotalMoney", tvTotalMoney.getText().toString().trim());
                    bundle.putString("Integral", model.Integral / 100 + "");
                    bundle.putString("carId", carId);
                    bundle.putInt("id", id);
                    JumpUtils.dataJump(OrderActivity.this, PayActivity.class, bundle, true);
                }
            }));
        } else {
            List<StoreinfoBean> storeinfo = adapter.getStorInfo();
            addSubscription(RequestClient.PostOrder(addressId, storeinfo, this, new NetSubscriber<BaseResultBean>(OrderActivity.this, true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    bundle = new Bundle();
                    bundle.putString("TotalOrderNo", model.TotalOrderNo);
                    bundle.putString("Balance", model.Balance + "");
                    bundle.putString("TotalMoney", tvTotalMoney.getText().toString().trim());
                    bundle.putString("Integral", model.Integral / 100 + "");
                    bundle.putString("carId", carId);
                    bundle.putInt("id", id);
                    JumpUtils.dataJump(OrderActivity.this, PayActivity.class, bundle, true);
                }
            }));
        }
    }

    AddressBean bean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                if (null != data) {

                    bean = data.getExtras().getParcelable("address");
                    llAddress.setVisibility(View.VISIBLE);
                    llNoAddress.setVisibility(View.GONE);
                    tvNamePhone.setText(bean.RealName + "         " + bean.TelPhone);
                    tvAddress.setText(bean.AreaAddress + "-" + bean.Address);
                    addressId = bean.Id + "";
                }

                break;
        }
    }
}
