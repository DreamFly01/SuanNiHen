package com.fdl.activity.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fdl.BaseActivity;
import com.fdl.activity.buy.PayActivity;
import com.fdl.activity.buy.ProductDetailsActivity;
import com.fdl.adapter.OrderDetailsAdapter;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.OrderDataBean;
import com.fdl.bean.OrderDetailsBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.ClipUtils;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/7<p>
 * <p>changeTime：2019/1/7<p>
 * <p>version：1<p>
 */
public class MyOrderDetailsActivity extends BaseActivity {


    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.av_avi)
    AVLoadingIndicatorView avAvi;
    @BindView(R.id.avi1)
    RelativeLayout avi1;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.ll_noData)
    LinearLayout llNoData;
    @BindView(R.id.avi)
    RelativeLayout avi;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone1)
    TextView tvPhone1;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_extra)
    TextView tvExtra;
    @BindView(R.id.tv_integerl)
    TextView tvIntegerl;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_money2)
    TextView tvMoney2;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.tv_tell)
    TextView tvTell;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_pay_state)
    TextView tvPayState;
    @BindView(R.id.ll_03)
    LinearLayout ll03;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.ll_04)
    LinearLayout ll04;
    @BindView(R.id.tv_chose1)
    TextView tvChose1;
    @BindView(R.id.tv_chose2)
    TextView tvChose2;
    @BindView(R.id.tv_chose3)
    TextView tvChose3;
    @BindView(R.id.tv_chose4)
    TextView tvChose4;
    @BindView(R.id.tv_chose5)
    TextView tvChose5;
    @BindView(R.id.ll_state)
    LinearLayout llState;
    @BindView(R.id.tv_couponValue)
    TextView tvCouponValue;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;
    @BindView(R.id.tv_leaveword)
    TextView tvLeaveword;
    @BindView(R.id.ll_leaveword)
    LinearLayout llLeaveword;
    private Bundle bundle;
    private String orderid;
    private OrderDetailsAdapter adapter;
    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_myorderdetails_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            orderid = bundle.getString("orderid");
        }
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        heardTitle.setText("订单详情");
    }

    @Override
    public void setUpLisener() {

    }


    @OnClick({R.id.heard_back, R.id.tv_copy, R.id.tv_chose1, R.id.tv_chose2, R.id.tv_chose3, R.id.tv_chose4, R.id.tv_chose5, R.id.tv_tell, R.id.ll_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_copy:
                ClipUtils.copyText(this, orderDetailsBean.OrderNo, "复制成功");
                break;
            //取消订单
            case R.id.tv_chose1:
                dialogUtils.simpleDialog("确定取消订单？", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        cancelOrder(orderid);
                    }
                }, true);
                break;
            //去付款
            case R.id.tv_chose2:
                bundle = new Bundle();
                bundle.putString("TotalOrderNo", orderDetailsBean.ToTalOrderNo+"" +
                        "" +
                        "" +
                        "");
                bundle.putString("subOrderNo", orderDetailsBean.OrderNo + "");
                bundle.putString("TotalMoney", orderDetailsBean.TotalPrice + "");
                bundle.putString("Integral", orderDetailsBean.Integral + "");
                bundle.putString("Balance", orderDetailsBean.BalanceOne + "");


                JumpUtils.dataJump(this, PayActivity.class, bundle, true);
                break;
            //提醒发货
            case R.id.tv_chose3:
                postMsg(orderDetailsBean.ShopId + "", orderid);
                break;
            //去评论
            case R.id.tv_chose4:
                Bundle bundle = new Bundle();
                bundle.putString("url", orderDetailsBean.goodslist.get(0).GoodsImg);
                bundle.putString("name", orderDetailsBean.goodslist.get(0).Name);
                bundle.putString("orderNo", orderDetailsBean.OrderNo);
                bundle.putString("goodsId", orderDetailsBean.goodslist.get(0).Id + "");
                JumpUtils.dataJump(this, DiscussActivity.class, bundle, false);
                break;
            //确认收货
            case R.id.tv_chose5:
                dialogUtils.simpleDialog("是否确认收货？", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        comfireGet(orderid);
                    }
                }, true);
                break;
            case R.id.tv_tell:
                if (StrUtils.isEmpty(orderDetailsBean.ServiceTel)) {
                    dialogUtils.noBtnDialog("商家未留联系号码");
                } else {
                    Intent myIntentDial = new Intent("android.intent.action.CALL", Uri.parse("tel:" + orderDetailsBean.ServiceTel));
                    startActivity(myIntentDial);
                }
                break;
            case R.id.ll_chat:
                if (StrUtils.isEmpty(SPUtils.getInstance(this).getString(Contans.IM_ACCOUNT))) {
                    Toast.makeText(MyOrderDetailsActivity.this, "通讯异常，请稍后再试", Toast.LENGTH_SHORT).show();
                } else {
                    NimUIKit.startP2PSession(this, "supp_" + orderDetailsBean.ShopId


                    );
                }
                break;
        }
    }

    OrderDetailsBean orderDetailsBean;

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetOrderDetail(orderid, this, new NetSubscriber<BaseResultBean<OrderDetailsBean>>(MyOrderDetailsActivity.this, false) {
            @Override
            public void onResultNext(BaseResultBean<OrderDetailsBean> model) {
                avi.setVisibility(View.GONE);
                orderDetailsBean = model.data;
                fillView(model.data);
            }
        }));
    }

    private void fillView(OrderDetailsBean bean) {
        switch (bean.OrderState) {
            case 0:
                llState.setVisibility(View.GONE);
                break;
            case 1:
                tvChose1.setVisibility(View.VISIBLE);
                tvChose2.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvChose3.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvChose5.setVisibility(View.VISIBLE);
                break;
            case 4:
                tvChose4.setVisibility(View.VISIBLE);
                break;
            case 5:
                llState.setVisibility(View.GONE);
                break;
            case 6:
                ll03.setVisibility(View.GONE);
                ll04.setVisibility(View.GONE);
                llState.setVisibility(View.GONE);
                break;

        }
        if ("6".equals(bean.ShopType)) {
//            llAddress.setVisibility(View.GONE);
            tvExtra.setText("到店自取");
        } else {
            llAddress.setVisibility(View.VISIBLE);
            if (bean.ExpressPrice > 0) {
                tvExtra.setText("¥" + bean.ExpressPrice + "");
            } else {
                tvExtra.setText("包邮");
            }
        }
        if (bean.OrderState == 0 || bean.OrderState == 1) {
            ll01.setVisibility(View.GONE);
            ll03.setVisibility(View.GONE);
            ll04.setVisibility(View.GONE);
            tv01.setText("待付款");
        } else {
            tv01.setText("实付款");

        }
        if (StrUtils.isEmpty(bean.CouponValue)||bean.OrderState==6) {
            llCoupon.setVisibility(View.GONE);
        }
        switch (bean.PayType) {
            //余额支付
            case 1:
                tvPayState.setText("余额");
                break;
            //微信支付
            case 2:
                tvPayState.setText("微信");
                break;
            //支付宝
            case 3:
                tvPayState.setText("支付宝");
                break;
            case 4:
                tvPayState.setText("混合支付(微信+余额)");
                break;
            //混合支付
            case 5:
                tvPayState.setText("混合支付(支付宝+余额)");
                break;
            case 6:
                tvPayState.setText("货到付款");
                break;
        }
        if ("6".equals(bean.ShopType)) {
            tvAddress.setText(bean.SupplierAddress);
            tvName.setText(bean.SupplierName);
        } else {
            tvAddress.setText(bean.ReceiverAreaAddress + " " + bean.ReceiverAddress);
            tvName.setText(bean.ReceiverName);
        }
        tvPayTime.setText(bean.PayTime);
        tvLeaveword.setText(bean.LeaveWord);


        if ((bean.Integral / 100) == 0.00) {
            ll01.setVisibility(View.GONE);
        } else {
            ll01.setVisibility(View.VISIBLE);
            tvIntegerl.setText("-¥" + bean.Integral / 100 + "");
        }


        tvCouponValue.setText("-¥" + bean.CouponValue);
        tvMoney.setText("¥" + StrUtils.moenyToDH(bean.GoodsTotalPrice + ""));
        tvMoney2.setText("¥" + StrUtils.moenyToDH((bean.TotalPrice) + ""));
        tvPhone1.setText(bean.ReceiverTelPhone);
        tvOrderNo.setText(bean.OrderNo);
        tvOrderTime.setText(bean.CreateTime);
        adapter = new OrderDetailsAdapter(this, R.layout.item_order_item_layout, bean.goodslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                bundle = new Bundle();
                bundle.putInt("goodsId", bean.goodslist.get(position).GoodsId);
                JumpUtils.dataJump(MyOrderDetailsActivity.this, ProductDetailsActivity.class, bundle, false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    //好友代付
    private void ShareWechat(String title, String url) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText(getString(R.string.app_name));
        sp.setUrl(url);
        sp.setTitle(title);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.share(sp);
    }

    //取消订单
    private void cancelOrder(String orderid) {
        RequestClient.CancelOrder(orderid, this, new NetSubscriber<BaseResultBean<List<OrderDataBean>>>(MyOrderDetailsActivity.this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<OrderDataBean>> model) {
                ToastUtil.shortShow("取消成功~");
                dialogUtils.dismissDialog();
                MyOrderDetailsActivity.this.finish();
            }
        });
    }


    //发货提醒
    private void postMsg(String touid, String orderid) {
        RequestClient.PostMsg(touid, orderid, "", "", this, new NetSubscriber<BaseResultBean<List<OrderDataBean>>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<OrderDataBean>> model) {
                ToastUtil.shortShow("店家已收到您的信息，请耐心等候~");
            }
        });
    }

    //确认收货
    private void comfireGet(String orderid) {
        RequestClient.ComfireGet(orderid, this, new NetSubscriber<BaseResultBean<List<OrderDataBean>>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<OrderDataBean>> model) {
                ToastUtil.shortShow("确认收货成功~");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
