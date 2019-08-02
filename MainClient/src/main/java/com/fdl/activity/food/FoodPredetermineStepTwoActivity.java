package com.fdl.activity.food;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.BaseActivity;
import com.fdl.activity.buy.PayActivity;
import com.fdl.activity.buy.event.PayResultEvent;
import com.fdl.activity.supermarket.StoreCouponsActivity;
import com.fdl.adapter.PredetermineAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.FoodGoodsCommitBean;
import com.fdl.bean.OrdersData;
import com.fdl.bean.SubscribeApplyInfoBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.db.ShopTypeEnum;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/6/28<p>
 * <p>changeTime：2019/6/28<p>
 * <p>version：1<p>
 */
public class FoodPredetermineStepTwoActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    //    @BindView(R.id.heard_menu)
//    ImageView heardMenu;
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_numInfo)
    TextView tvNumInfo;
    @BindView(R.id.tv_timeInfo)
    TextView tvTimeInfo;
    @BindView(R.id.tv_personInfo)
    TextView tvPersonInfo;
    @BindView(R.id.iv_store_log)
    ImageView ivStoreLog;
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_clean)
    TextView tvClean;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_more_data)
    LinearLayout llMoreData;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.iv_upOrDown)
    ImageView upOrDown;
    @BindView(R.id.heard_fl_menu)
    FrameLayout heardMenu;
    @BindView(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tv_conpons)
    TextView tvConpons;
    @BindView(R.id.ll_coupons)
    LinearLayout llCoupons;
    @BindView(R.id.tv_redution)
    TextView tvRedution;

    private int applyId;
    private Bundle bundle;
    private PredetermineAdapter adapter;
    HashMap<Integer, OrdersData> tempMap = new HashMap<>();
    private DialogUtils dialogUtils;
    private boolean isNeedCommit = false;//数据是否需要提交
    private boolean isChange = false;//数据是否改变
    private int id;

    private int commitType;//数据更改方式 0 调用save生成新的预订单，1调用change 更改预订单
    private int changeType;//数据更改渠道 0 添加新的菜品，1 已经存在的菜品数量的增减
    private int changeCoupons;//优惠劵是否修改 0，无修改，1有修改

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_food_perdetermine_two_layout);
        EventBus.getDefault().register(this);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            applyId = bundle.getInt("applyId");
            id = bundle.getInt("storeId");
        }
        dialogUtils = new DialogUtils(this);

    }

    @Override
    public void setUpViews() {
        setImm(false);
        heardTitle.setText("订单详情");
        heardTvMenu.setText("去结算");
        setRecyclerView();
        getData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setRecyclerView() {
        adapter = new PredetermineAdapter(R.layout.item_predetermine_goods_layout, null);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUpLisener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                isChange = true;
                isNeedCommit = true;
                commitType = 1;
                changeType = 1;
                switch (view.getId()) {
                    case R.id.iv_delete:
                        ordersData.get(position).GoodsNum2 -= 1;
                        adapter.setNewData(ordersData);
                        setTvPrice(couponsValue, couponName);
                        break;
                    case R.id.iv_add:
                        ordersData.get(position).GoodsNum2 += 1;
                        adapter.setNewData(ordersData);
                        setTvPrice(couponsValue, couponName);
                        break;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private boolean isMore = false;

    @OnClick({R.id.heard_back, R.id.tv_add, R.id.tv_clean, R.id.ll_more_data, R.id.tv_share, R.id.tv_pay, R.id.heard_fl_menu, R.id.ll_coupons})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_add:
//                JumpUtils.dataJump(this,FoodAddGoodsActivity.class,bundle,false);
                Intent intent = new Intent(this, FoodAddGoodsActivity.class);
                bundle = new Bundle();
                bundle.putString("orderNo", orderNo);
                bundle.putInt("storeId", id);
                bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) ordersData);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1002);
                break;
            case R.id.tv_clean:
                setRecyclerViewData(removeNewList(returnData));
                returnData.clear();
                break;
            case R.id.ll_more_data:
                if (isMore) {
                    adapter.setNewData(myOrdersData);
                    upOrDown.setBackgroundResource(R.drawable.arrow_down);
                    isMore = false;
                } else {
                    adapter.setNewData(ordersData);
                    upOrDown.setBackgroundResource(R.drawable.arrow_up);
                    isMore = true;
                }
                break;
            case R.id.tv_share:
                if (isChange | changeCoupons == 1) {
                    setCommitData(0);
                } else {
                    dialogUtils.noBtnDialog("当前菜单无任何改变");
                }
                break;
            case R.id.tv_pay:

                break;
            case R.id.heard_fl_menu:
                pay();
                break;
            case R.id.ll_coupons:
                List<CommTenant> productData = new ArrayList<>();
                for (int i = 0; i < ordersData.size(); i++) {
                    CommTenant commTenant = new CommTenant();
                    commTenant.total = ordersData.get(i).GoodsNum + ordersData.get(i).GoodsNum2;
                    commTenant.CommTenantId = Long.parseLong(ordersData.get(i).GoodsId + "");
                    commTenant.Price = ordersData.get(i).SalesPrice;
                    commTenant.setCommTenantName(ordersData.get(i).GoodsName);
                    productData.add(commTenant);
                }
                Intent intent1 = new Intent(this, StoreCouponsActivity.class);
                intent1.putExtra("storeId", id);
                intent1.putExtra("money", totalPrice);
                intent1.putExtra("SupplierId", id);
                intent1.putExtra("from", ShopTypeEnum.TYPE1.getValue());
                intent1.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) productData);
                startActivityForResult(intent1, 1000);
                break;
        }
    }

    @Subscribe
    public void payResultEvent(PayResultEvent event) {
        if (event.getResult() == RESULT_OK) {
            shareOther();
        }
    }

    private void shareOther() {
        if (StrUtils.isEmpty(orderNo)) {
            setCommitData(1);
        } else {
            if (!isNeedCommit) {
//                dialogUtils.ShareDialog(shopName, shareUrl, "我在该店铺预订了"+time+"吃饭，邀请您点餐", imgUrl);
                dialogUtils.ShareDialog(shopName, shareUrl, "我在该店铺预订了"+time+"吃饭，邀请您点餐",
                        imgUrl,"分享给您的用餐成员，就可以一起来点餐啦！");
            } else {
                dialogUtils.noBtnDialog("请先提交菜单，再进行分享哦~");
            }
        }
    }

    private void pay() {
        if (isNeedCommit) {
            int noPayMoney =0;
            int payMoney = 0;
            for (OrdersData ordersDatum : ordersData) {
                noPayMoney += ordersDatum.GoodsNum2*ordersDatum.SalesPrice;
                payMoney+=ordersDatum.GoodsNum*ordersDatum.SalesPrice;
            }
            int finalPayMoney = payMoney;
            dialogUtils.twoBtnDialog("当前总价格为："+(noPayMoney+payMoney)+"元，其中未确定："+noPayMoney+"元,应支付为:"+payMoney+"元,是否继续支付？", new DialogUtils.ChoseClickLisener() {
                @Override
                public void onConfirmClick(View v) {
                    dialogUtils.dismissDialog();
                    if(finalPayMoney >0){
                        toPay();
                    }else{
                        dialogUtils.noBtnDialog("当前支付价格为0，无法前往支付");
                    }
                }

                @Override
                public void onCancelClick(View v) {
                    dialogUtils.dismissDialog();
                }
            },false);
//                    dialogUtils.noBtnDialog("请先提交菜单，再去买单哦~");
        }else if (ordersData.size() <= 0) {
            dialogUtils.noBtnDialog("当前订单没有菜品，请先去添加菜品哦~");
        } else {
            toPay();
        }

    }

    private String shareUrl;
    private String imgUrl;
    private String shopName;
    private String time;

    private void getData() {
        addSubscription(RequestClient.GetSubscribeApplyInfo(applyId, this, new NetSubscriber<BaseResultBean<SubscribeApplyInfoBean>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<SubscribeApplyInfoBean> model) {
                shopName = model.data.SUP.ShopName;
                time = model.data.FOOD.DinnerTime;
                fillView(model.data);
                if (StrUtils.isEmpty(model.data.FOOD.OrderNo)) {
                    commitType = 0;
                } else {
                    orderNo = model.data.FOOD.OrderNo;
                    commitType = 1;
                }
                shareUrl = model.data.FOOD.SHAREURL;
                imgUrl = model.data.SUP.Logo;
                int noCommit = 0;
                for (int i = 0; i < model.data.ORDERS.size(); i++) {
                    noCommit += model.data.ORDERS.get(i).GoodsNum2;
                }
                if (noCommit > 0) {
                    isChange = true;
                    isNeedCommit = true;
                    changeType =1 ;
                    dialogUtils.noBtnDialog("当前菜单有更新，请确认后提交新的菜单哦~");
                }
            }
        }));
    }

    /**
     * 创建预定订单
     */
    Map<String, Object> dataMap = new TreeMap<>();
    List<FoodGoodsCommitBean> goodsList = new ArrayList<>();

    private void setCommitData(int from) {
        dataMap.put("SupplierId", 330);
        dataMap.put("LeaveWord", "");
        dataMap.put("applyid", applyId);
        dataMap.put("CouponsId", couponsId);
        for (OrdersData data : ordersData) {
            FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
            bean.GoodsId = data.GoodsId;
            bean.Number = (data.GoodsNum + data.GoodsNum2);
            bean.NormsId = 0;
            bean.NormsInfo = "";
            bean.GoodsNum2 = 0;
            goodsList.add(bean);
        }
        dataMap.put("goodIds", goodsList);
        commitOrder(from);
    }

    /**
     * 预定订单更改
     */
    HashMap<Integer, FoodGoodsCommitBean> listMap = new HashMap<>();
    List<FoodGoodsCommitBean> goodsList1 = new ArrayList<>();
    private void setCommitData1() {
        if (changeType == 1) {
            for (OrdersData ordersDatum : ordersData) {
                FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
                bean.GoodsId = ordersDatum.GoodsId;
                bean.GoodsNum = ordersDatum.GoodsNum + ordersDatum.GoodsNum2;
                bean.GoodsNormId = 0;
                bean.OrderId = ordersDatum.Id;
                bean.GoodsNum2 = 0;
                goodsList1.add(bean);
            }
        } else {
            //订单号 为空
            if (listMap.size() > 0) {
                for (OrdersData returnDatum : returnData) {
                    for (OrdersData ordersDatum : ordersData) {
                        if (returnDatum.GoodsId == ordersDatum.GoodsId) {
                            listMap.get(returnDatum.GoodsId).GoodsId = ordersDatum.GoodsId;
                            listMap.get(returnDatum.GoodsId).OrderId = ordersDatum.Id;
                            listMap.get(returnDatum.GoodsId).GoodsNormId = 0;
                            listMap.get(returnDatum.GoodsId).GoodsNum += (ordersDatum.GoodsNum + ordersDatum.GoodsNum2);
                            listMap.get(returnDatum.GoodsId).GoodsNum2 = 0;
                        } else {
                            FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
                            bean.GoodsId = returnDatum.GoodsId;
                            bean.GoodsNum = returnDatum.GoodsNum + returnDatum.GoodsNum2;
                            bean.GoodsNormId = 0;
                            bean.OrderId = 0;
                            bean.GoodsNum2 = 0;
                            listMap.put(returnDatum.GoodsId, bean);
                        }
                    }
                }
            } else {
                for (OrdersData returnDatum : returnData) {
                    FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
                    bean.GoodsId = returnDatum.GoodsId;
                    bean.GoodsNum = returnDatum.GoodsNum + returnDatum.GoodsNum2;
                    bean.GoodsNormId = 0;
                    bean.OrderId = returnDatum.Id;
                    bean.GoodsNum2 = 0;
                    listMap.put(returnDatum.GoodsId, bean);

                }
            }
            for (Integer integer : listMap.keySet()) {
                goodsList1.add(listMap.get(integer));
            }
        }

    }

    private String orderNo;
    private void commitOrder(int isShare) {
        if (StrUtils.isEmpty(orderNo) && commitType == 0) {
            addSubscription(RequestClient.SaveOrder(dataMap, this, new NetSubscriber<BaseResultBean>(this, true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    isNeedCommit = false;
                    isChange = false;
                    commitType = 1;
                    orderNo = model.TotalOrderNo;
                    for (int i = 0; i < ordersData.size(); i++) {
                        if (ordersData.get(i).GoodsNum2 > 0) {
                            ordersData.get(i).GoodsNum += ordersData.get(i).GoodsNum2;
                            ordersData.get(i).GoodsNum2 = 0;
                        }
                    }
                    adapter.setNewData(ordersData);
                    if (isShare == 1) {
                        dialogUtils.ShareDialog(shopName, shareUrl, "我在该店铺预订了"+time+"吃饭，邀请您点餐", imgUrl);
                    } else {
                        dialogUtils.noBtnDialog("提交成功！");
                    }
                }
            }));
        } else {
            setCommitData1();
            RequestClient.ChangePredeterminerOrder(orderNo, 1, goodsList1, this, new NetSubscriber<BaseResultBean>(this, true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    isNeedCommit = false;
                    isChange = false;
                    commitType = 1;
                    dialogUtils.noBtnDialog("提交成功！");
                    for (int i = 0; i < ordersData.size(); i++) {
                        if (ordersData.get(i).GoodsNum2 > 0) {
                            ordersData.get(i).GoodsNum += ordersData.get(i).GoodsNum2;
                            ordersData.get(i).GoodsNum2 = 0;
                        }
                    }
                    adapter.setNewData(ordersData);
                    if (changeCoupons == 1) {
                        changeCoupons();
                    }
                }
            });
        }
    }

    //修改优惠劵
    private void changeCoupons() {
        addSubscription(RequestClient.CouponsChange(orderNo, couponsId, this, new NetSubscriber<BaseResultBean>(this) {
            @Override
            public void onResultNext(BaseResultBean model) {

            }
        }));
    }

    private List<OrdersData> ordersData = new ArrayList<>();
    private List<OrdersData> myOrdersData = new ArrayList<>();
    private double couponsValue;

    private void fillView(SubscribeApplyInfoBean bean) {
        ordersData = bean.ORDERS;
        tvTime.setText(bean.FOOD.LDinnerTime);
        tvAddress.setText(bean.SUP.Province + bean.SUP.City + bean.SUP.Area + bean.SUP.Address);
        tvPhone.setText(bean.SUP.ContactsTel);

        tvNumInfo.setText("成人" + bean.FOOD.AdultCount + ",儿童（6岁以下）" + bean.FOOD.ChildCount);
        tvTimeInfo.setText(bean.FOOD.DinnerTime);
        tvPersonInfo.setText(bean.FOOD.UName + "," + bean.FOOD.Phone);

        ImageUtils.loadUrlImage(this, bean.SUP.Logo, ivStoreLog);
        tvStoreName.setText(bean.SUP.ShopName);
        setRecyclerViewData(ordersData);
        for (int i = 0; i < ordersData.size(); i++) {
            tempMap.put(ordersData.get(i).GoodsId, ordersData.get(i));
        }

        couponsValue = bean.COUPON.CouponValue;
        couponName = bean.COUPON.CouponName;
        setTvPrice(couponsValue, couponName);
    }

    private void setRecyclerViewData(List<OrdersData> ordersData) {
        myOrdersData.clear();
        if (ordersData.size() > 3) {
            for (int i = 0; i < 3; i++) {
                myOrdersData.add(ordersData.get(i));
            }
            llMoreData.setVisibility(View.VISIBLE);
            adapter.setNewData(myOrdersData);
        } else {
            llMoreData.setVisibility(View.GONE);
            adapter.setNewData(ordersData);
        }
    }


    List<OrdersData> returnData = new ArrayList<>();
    private int couponsId = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //菜单修改
        if (requestCode == 1002 && resultCode == 1002) {
            if (null != data) {
                returnData = data.getParcelableArrayListExtra("data");
                if (returnData.size() > 0&&StrUtils.isEmpty(orderNo)) {
                    isChange = true;
                    isNeedCommit = true;
                }
                setRecyclerViewData(getNewList(returnData));
                setTvPrice(couponsValue, couponName);
            }
        }
        //
        else if (requestCode == 1000 && resultCode == 1001) {
            if (null != data) {
                if (couponsId == data.getIntExtra("couponsId", 0)) {
                    changeCoupons = 0;
                } else {
                    changeCoupons = 1;
                    isNeedCommit = true;
                }
                couponsValue = data.getDoubleExtra("CouponValue", 0);
                couponsId = data.getIntExtra("couponsId", 0);
                tvConpons.setText(data.getStringExtra("couponsInfo"));
                double price = totalPrice - couponsValue;
                tvTotalPrice.setText("¥" + StrUtils.moenyToDH(price + ""));
                tvRedution.setText("(已优惠：¥" + StrUtils.moenyToDH(couponsValue + "") + ")");
            }
        }
    }

    private List<OrdersData> getNewList(List<OrdersData> list) {

        for (OrdersData data : list) {
            int temp = data.GoodsId;
            if (tempMap.containsKey(temp)) {
                tempMap.get(temp).GoodsId = data.GoodsId;
                tempMap.get(temp).SalesPrice = data.SalesPrice;
                tempMap.get(temp).Image = data.Image;
                tempMap.get(temp).GoodsName = data.GoodsName;
                if (StrUtils.isEmpty(orderNo)) {
                    tempMap.get(temp).GoodsNum = 0;
                    tempMap.get(temp).GoodsNum2 = data.GoodsNum2;
                } else {
                    tempMap.get(temp).GoodsNum += data.GoodsNum2;
                    tempMap.get(temp).GoodsNum2 = 0;
                }
            } else {
                tempMap.put(temp, data);
            }
        }
        ordersData.clear();
        for (Integer integer : tempMap.keySet()) {
            ordersData.add(tempMap.get(integer));
        }
        return ordersData;
    }

    private List<OrdersData> removeNewList(List<OrdersData> list) {

        for (OrdersData data : list) {
            int temp = data.GoodsId;
            if (tempMap.containsKey(temp)) {

                tempMap.get(temp).GoodsNum -= data.GoodsNum;
                if (tempMap.get(temp).GoodsNum <= 0) {
                    tempMap.remove(temp);
                }
            }
        }
        ordersData.clear();
        for (Integer integer : tempMap.keySet()) {
            ordersData.add(tempMap.get(integer));
        }
        return ordersData;
    }

    private double totalMoney;

    private void toPay() {
        for (OrdersData data : ordersData) {
            totalMoney += data.GoodsNum * data.SalesPrice;
        }
        totalMoney -= couponsValue;
        bundle.putString("TotalMoney", totalMoney + "");
        bundle.putString("TotalOrderNo", orderNo);
        JumpUtils.dataJump(this, PayActivity.class, bundle, true);
    }


    private double totalPrice;
    private String couponName;

    private void setTvPrice(double couponValue, String couponName) {
        totalPrice = 0;
        for (int i = 0; i < ordersData.size(); i++) {

            totalPrice += ordersData.get(i).SalesPrice * (ordersData.get(i).GoodsNum + ordersData.get(i).GoodsNum2);
        }

        totalPrice -= couponValue;
        tvConpons.setText(couponName);
        tvRedution.setText("(已优惠：¥" + StrUtils.moenyToDH(couponValue + "") + ")");
        tvTotalPrice.setText("¥" + StrUtils.moenyToDH(totalPrice + ""));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersionBar.with(this).statusBarColor(R.color.app_red).statusBarDarkFont(false).init();
    }
}
