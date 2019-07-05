package com.fdl.activity.food;

import android.content.Intent;
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
import com.fdl.adapter.PredetermineAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.FoodGoodsCommitBean;
import com.fdl.bean.OrdersData;
import com.fdl.bean.SubscribeApplyInfoBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;

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

    private int applyId;
    private Bundle bundle;
    private PredetermineAdapter adapter;
    HashMap<Integer, OrdersData> tempMap = new HashMap<>();
    private DialogUtils dialogUtils;
    private boolean isCommit = false;//数据是否提交
    private boolean isChange = false;//数据是否改变
    private int id;

    private int commitType;//数据更改方式 0 调用save生成新的预订单，1调用change 更改预订单
    private int changeType;//数据更改渠道 0 添加新的菜品，1 已经存在的菜品数量的增减

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_food_perdetermine_two_layout);
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
        heardTvMenu.setText("分享");
        setRecyclerView();
        getData();

    }

    private void setRecyclerView() {
        adapter = new PredetermineAdapter(R.layout.item_predetermine_goods_layout, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUpLisener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                isChange = true;
                isCommit = false;
                commitType = 1;
                changeType = 1;
                switch (view.getId()) {
                    case R.id.iv_delete:
                        ordersData.get(position).GoodsNum2 -= 1;
                        adapter.setNewData(ordersData);
                        setTvPrice();
                        break;
                    case R.id.iv_add:
                        ordersData.get(position).GoodsNum += 1;
                        adapter.notifyItemChanged(position);
                        setTvPrice();
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

    @OnClick({R.id.heard_back, R.id.tv_add, R.id.tv_clean, R.id.ll_more_data, R.id.tv_share, R.id.tv_pay, R.id.heard_fl_menu})
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
                    upOrDown.setBackgroundResource(R.drawable.arrow_up);
                    isMore = false;
                } else {
                    adapter.setNewData(ordersData);
                    upOrDown.setBackgroundResource(R.drawable.arrow_down);
                    isMore = true;
                }
                break;
            case R.id.tv_share:
                if (isChange) {
                    setCommitData();
                } else {
                    dialogUtils.noBtnDialog("当前菜单无任何改变");
                }
                break;
            case R.id.tv_pay:
                if (!isCommit) {
                    dialogUtils.noBtnDialog("请先提交菜单，再进行分享哦~");
                } else {
                    toPay();
                }

                break;
            case R.id.heard_fl_menu:
                if (!isChange) {
                    dialogUtils.ShareDialog(shopName, shareUrl, "我在算你狠平台预订了一个美食订单，快来一起点餐吧~", imgUrl);
                } else if (isCommit) {
                    dialogUtils.ShareDialog(shopName, shareUrl, "我在算你狠平台预订了一个美食订单，快来一起点餐吧~", imgUrl);
                } else {
                    dialogUtils.noBtnDialog("请先提交菜单，再进行分享哦~");
                }

                break;
        }
    }

    private String shareUrl;
    private String imgUrl;
    private String shopName;
    private void getData() {
        addSubscription(RequestClient.GetSubscribeApplyInfo(applyId, this, new NetSubscriber<BaseResultBean<SubscribeApplyInfoBean>>(this,true) {
            @Override
            public void onResultNext(BaseResultBean<SubscribeApplyInfoBean> model) {
                shopName = model.data.SUP.ShopName;
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
                    noCommit+=model.data.ORDERS.get(i).GoodsNum2;
                }
                if(noCommit>0){
                    dialogUtils.noBtnDialog("当前菜单有更新，请确认后提交新的菜单");
                }
            }
        }));
    }

    /**
     * 创建预定订单
     */
    Map<String, Object> dataMap = new TreeMap<>();
    List<FoodGoodsCommitBean> goodsList = new ArrayList<>();

    private void setCommitData() {
        dataMap.put("SupplierId", 330);
        dataMap.put("LeaveWord", "");
        dataMap.put("applyid", applyId);
        dataMap.put("CouponsId", 0);
        for (OrdersData data : ordersData) {
            FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
            bean.GoodsId = data.GoodsId;
            bean.Number = (data.GoodsNum+data.GoodsNum2);
            bean.NormsId = 0;
            bean.NormsInfo = "";
            bean.GoodsNum2 = 0;
            goodsList.add(bean);
        }
        dataMap.put("goodIds", goodsList);
        commitOrder();
    }

    /**
     * 预定订单更改
     */
    HashMap<Integer, FoodGoodsCommitBean> listMap = new HashMap<>();
    List<FoodGoodsCommitBean> goodsList1 = new ArrayList<>();

    // TODO: 2019/7/3 当前更改菜单 需要重新new一个list
    private void setCommitData1() {
        if (changeType == 1) {
            for (OrdersData ordersDatum : ordersData) {
                FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
                bean.GoodsId = ordersDatum.GoodsId;
                bean.GoodsNum = ordersDatum.GoodsNum+ordersDatum.GoodsNum2;
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
                            listMap.get(returnDatum.GoodsId).GoodsNum +=  (ordersDatum.GoodsNum+ordersDatum.GoodsNum2);
                            listMap.get(returnDatum.GoodsId).GoodsNum2=0;
                        } else {
                            FoodGoodsCommitBean bean = new FoodGoodsCommitBean();
                            bean.GoodsId = returnDatum.GoodsId;
                            bean.GoodsNum = returnDatum.GoodsNum+returnDatum.GoodsNum2;
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
                    bean.GoodsNum = returnDatum.GoodsNum+returnDatum.GoodsNum2;
                    bean.GoodsNormId = 0;
                    bean.OrderId = 0;
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

    private void commitOrder() {

        if (StrUtils.isEmpty(orderNo) && commitType == 0) {
            addSubscription(RequestClient.SaveOrder(dataMap, this, new NetSubscriber<BaseResultBean>(this, true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    isCommit = true;
                    isChange = false;
                    commitType = 1;
                    dialogUtils.noBtnDialog("提交成功！");
                    orderNo = model.TotalOrderNo;
                }
            }));
        } else {
            setCommitData1();
            RequestClient.ChangePredeterminerOrder(orderNo, 1, goodsList1, this, new NetSubscriber<BaseResultBean>(this, true) {
                @Override
                public void onResultNext(BaseResultBean model) {
                    isCommit = true;
                    isChange = false;
                    commitType = 1;
                    dialogUtils.noBtnDialog("提交成功！");
                    orderNo = model.TotalOrderNo;
                }
            });
        }
    }

    private List<OrdersData> ordersData = new ArrayList<>();
    private List<OrdersData> myOrdersData = new ArrayList<>();

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
        setTvPrice();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == 1002) {
            if (null != data) {
                returnData = data.getParcelableArrayListExtra("data");
                if (returnData.size() > 0) {
                    isChange = true;
                }
                setTvPrice();
                setRecyclerViewData(getNewList(returnData));
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
                tempMap.get(temp).GoodsNum += data.GoodsNum;
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
        bundle.putString("TotalMoney", totalMoney + "");
        bundle.putString("TotalOrderNo", orderNo);
        JumpUtils.dataJump(this, PayActivity.class, bundle, true);
    }


    private double totalPrice;

    private void setTvPrice() {
        totalPrice = 0;
        for (int i = 0; i < ordersData.size(); i++) {
            totalPrice += ordersData.get(i).SalesPrice * ordersData.get(i).GoodsNum;
        }
        tvTotalPrice.setText("¥" + StrUtils.moenyToDH(totalPrice + ""));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersionBar.with(this).statusBarColor(R.color.app_red).statusBarDarkFont(false).init();
    }
}
