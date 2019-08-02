package com.fdl.activity.buy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.fdl.BaseActivity;
import com.fdl.activity.buy.event.PayResultEvent;
import com.fdl.activity.goTravel.TravelActivity;
import com.fdl.activity.main.MainActivity;
import com.fdl.activity.wool.WoolDetailsActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.MessageEvent;
import com.fdl.bean.PayWxBean;
import com.fdl.bean.ShakyBean;
import com.fdl.bean.WoolBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.FinishActivityManager;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/27<p>
 * <p>changeTime：2018/12/27<p>
 * <p>version：1<p>
 */
public class PayActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_01)
    ImageView iv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.iv_02)
    ImageView iv02;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.iv_03)
    ImageView iv03;
    @BindView(R.id.ll_03)
    LinearLayout ll03;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_integerl)
    TextView tvIntegerl;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.iv_04)
    ImageView iv04;
    @BindView(R.id.ll_04)
    LinearLayout ll04;
    private String toltalOrderNo, syspaytype, otherpay, totalMoney, Balance, subOrderNo;
    private Bundle bundle;
    private double Integral;
    private DialogUtils dialogUtils;
    private int core = -1;
    private int hymId;
    private String type = "";
    private static final int SDK_PAY_FLAG = 1;
    private String TorderNo, orderNo;
    private LinearLayout llQlx, llHym;
    /**
     * 支付宝支付
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                        bundle = new Bundle();
                        bundle.putString("result", "支付成功");
                        showPlayResultDialog("支付成功");
//                        JumpUtils.dataJump(PayActivity.this, PayResultActivity.class, bundle, true);
                    } else if (TextUtils.equals(resultStatus, "6001")) {

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                        bundle = new Bundle();
                        bundle.putString("result", "支付失败");
                        JumpUtils.dataJump(PayActivity.this, PayResultActivity.class, bundle, true);
                    }
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay_layout);
        TorderNo = SPUtils.getInstance(this).getString("TorderNo");
        bundle = this.getIntent().getExtras();
        if (null != bundle) {
            toltalOrderNo = bundle.getString("TotalOrderNo");
            SPUtils.getInstance(this).saveData("TorderNo", toltalOrderNo);
            totalMoney = bundle.getString("TotalMoney");
            SPUtils.getInstance(this).saveData("TotalMoney", totalMoney);
            Balance = bundle.getString("Balance");
            subOrderNo = bundle.getString("subOrderNo");
            SPUtils.getInstance(this).saveData("subOrderNo", subOrderNo);
            core = bundle.getInt("core", -1);
            type = bundle.getString("type");
            SPUtils.getInstance(this).saveData("payshopType", type);
//            id = bundle.getInt("id");
        }
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        FinishActivityManager.getManager().addActivity(this);
//        ImmersionBar.with(this).statusBarColor(R.color.white).titleBar(rlHead).statusBarDarkFont(true).init();
        if (ImmersionBar.hasNotchScreen(this)) {
            IsBang.setImmerHeard(this, rlHead);
        }
        TextView title = (TextView) findViewById(R.id.heard_title);
        title.setText("支付");
        if (toltalOrderNo != null) {
            tvOrderNo.setText(toltalOrderNo);//订单号
        } else {
            tvOrderNo.setText(subOrderNo);//<我的订单>传过来的
        }

        if (!StrUtils.isEmpty(totalMoney)) {//总金额
            tvMoney.setText("¥" + StrUtils.moenyToDH(totalMoney));
        }
    }

    @Override
    public void setUpLisener() {

    }

    public boolean isClick1 = false;
    public boolean isClick2 = false;
    public boolean isClick3 = false;
    public boolean isClick4 = false;

    @OnClick({R.id.heard_back, R.id.ll_01, R.id.ll_02, R.id.ll_03, R.id.ll_04, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_01:
                if (isClick1) {
                    iv01.setBackgroundResource(R.drawable.pay_normall);
                    isClick1 = false;
                } else {
                    iv01.setBackgroundResource(R.drawable.pay_selete);
                    isClick1 = true;
                }
                break;
            case R.id.ll_02:
                if (isClick2) {
                    iv02.setBackgroundResource(R.drawable.pay_normall);
                    isClick2 = false;
                } else {
                    iv02.setBackgroundResource(R.drawable.pay_selete);
                    isClick2 = true;
                }
                break;
            case R.id.ll_03:
                if ((blance >= Double.parseDouble(totalMoney)) && isClick2) {
                    dialogUtils.twoBtnDialog("余额充足，是否选择微信支付？", new DialogUtils.ChoseClickLisener() {
                        @Override
                        public void onConfirmClick(View v) {
                            isClick2 = false;
                            isClick4 = false;
                            isClick3 = true;
                            iv02.setBackgroundResource(R.drawable.pay_normall);
                            iv04.setBackgroundResource(R.drawable.pay_normall);
                            iv03.setBackgroundResource(R.drawable.pay_selete);
                            dialogUtils.dismissDialog();
                        }

                        @Override
                        public void onCancelClick(View v) {
                            dialogUtils.dismissDialog();
                        }
                    }, false);

                } else {
                    if (isClick3) {
                        iv03.setBackgroundResource(R.drawable.pay_normall);
                        isClick3 = false;
                    } else {
                        iv03.setBackgroundResource(R.drawable.pay_selete);
                        isClick3 = true;
                        iv04.setBackgroundResource(R.drawable.pay_normall);
                        isClick4 = false;
                    }
                }

                break;
            case R.id.ll_04:
                if ((blance >= Double.parseDouble(totalMoney)) && isClick2) {
                    dialogUtils.twoBtnDialog("余额充足，是否选择支付宝支付？", new DialogUtils.ChoseClickLisener() {
                        @Override
                        public void onConfirmClick(View v) {
                            isClick2 = false;
                            isClick4 = true;
                            isClick3 = false;
                            iv02.setBackgroundResource(R.drawable.pay_normall);
                            iv03.setBackgroundResource(R.drawable.pay_normall);
                            iv04.setBackgroundResource(R.drawable.pay_selete);
                            dialogUtils.dismissDialog();
                        }

                        @Override
                        public void onCancelClick(View v) {
                            dialogUtils.dismissDialog();
                        }
                    }, false);

                } else {
                    if (isClick4) {
                        iv04.setBackgroundResource(R.drawable.pay_normall);
                        isClick4 = false;
                    } else {
                        iv04.setBackgroundResource(R.drawable.pay_selete);
                        iv03.setBackgroundResource(R.drawable.pay_normall);
                        isClick3 = false;
                        isClick4 = true;
                    }
                }

                break;
            case R.id.tv_commit:
                if (isFastClick()) {
                    return;
                }
                if (setPayData()) {
                    pay();
                }
                break;
        }
    }

    private double blance;

    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetMoney(this, new NetSubscriber<BaseResultBean>(this, false) {
            @Override
            public void onResultNext(BaseResultBean model) {
                blance = Double.parseDouble(model.money);
                Integral = model.Integral;
                tvBalance.setText("账户余额(¥ " + model.money + ")");
                tvIntegerl.setText("¥" + model.Integral / 100);
            }
        }));
    }

    PayWxBean bean;

    private void pay() {
        if (otherpay.equals("2") || otherpay.equals("4")) {//微信支付
            addSubscription(RequestClient.Pay(toltalOrderNo, syspaytype, otherpay, subOrderNo, this, new NetSubscriber<BaseResultBean<PayWxBean>>(this, true) {
                @Override
                public void onResultNext(BaseResultBean<PayWxBean> model) {
                    payWeChat(model.data);
                }
            }));
        } else if (otherpay.equals("3") || otherpay.equals("5")) {//支付宝支付
            addSubscription(RequestClient.Pay1(toltalOrderNo, syspaytype, otherpay, subOrderNo, this, new NetSubscriber<BaseResultBean<String>>(this, true) {
                @Override
                public void onResultNext(BaseResultBean<String> model) {
                    payAli(model.data);
                }
            }));
        } else {//余额支付
            addSubscription(RequestClient.Pay(toltalOrderNo, syspaytype, otherpay, subOrderNo, this, new NetSubscriber<BaseResultBean<PayWxBean>>(this, true) {
                @Override
                public void onResultNext(BaseResultBean<PayWxBean> model) {
                    bundle = new Bundle();
                    bundle.putString("result", "支付成功");
                    JumpUtils.dataJump(PayActivity.this, PayResultActivity.class, bundle, true);
                }
            }));
        }

    }

    //想后台发起支付请求
    private boolean setPayData() {
        syspaytype = "";
        otherpay = "";
        if (isClick1 && !isClick2) {
            syspaytype = "2";
        }
        if (!isClick1 && isClick2) {
            syspaytype = "1";
        }
        if (isClick1 && isClick2) {
            syspaytype = "1,2";
        }

        if ((isClick2 || isClick1) && !isClick3 && !isClick4) {
            otherpay = "1";
        } else if (isClick3 && !isClick1 && !isClick2) {
            otherpay = "2";
        } else if (isClick4 && !isClick1 && !isClick2) {
            otherpay = "3";
        } else if (isClick3 && (isClick2 || isClick1)) {
            otherpay = "4";
        } else if (isClick4 && (isClick2 || isClick1)) {
            otherpay = "5";
        } else {
            otherpay = "";
        }
        if (!(isClick1 | isClick2 | isClick3 | isClick4)) {
            dialogUtils.noBtnDialog("请选择支付方式");
            return false;
        }
        if (syspaytype.equals("2") && !(isClick2 | isClick3 | isClick4)) {
            if (Integral / 100 < Double.parseDouble(totalMoney)) {
                dialogUtils.noBtnDialog("积分不足");
                return false;
            }
        }
        if (syspaytype.equals("1") && !(isClick1 | isClick3 | isClick4)) {
            if (blance < Double.parseDouble(totalMoney)) {
                dialogUtils.noBtnDialog("余额不足");
                return false;
            }
        }
        if (syspaytype.equals("1,2") && !(isClick3 | isClick4)) {
            if ((blance + (Integral / 100)) < Double.parseDouble(totalMoney)) {
                dialogUtils.noBtnDialog("积分余额不足");
                return false;
            }
        }
        if ((otherpay.equals("4") || otherpay.equals("5")) && isClick2) {

            if (blance >= Double.parseDouble(totalMoney)) {
                dialogUtils.noBtnDialog("账户资金足够，请勿混合支付");
                return false;
            }
        }
        if ((otherpay.equals("4") || otherpay.equals("5")) && isClick2 && isClick1) {

            if ((blance + (Integral / 100)) >= Double.parseDouble(totalMoney)) {
                dialogUtils.noBtnDialog("账户资金足够，请勿混合支付");
                return false;
            }
        }
        return true;
    }

    //发起微信支付
    private void payWeChat(PayWxBean bean) {

        IWXAPI api = WXAPIFactory.createWXAPI(this, "wx223ecb204beddad4", false);//APPID
        api.registerApp("wx223ecb204beddad4");//APPID，注册本身APP
        PayReq req = new PayReq();//PayReq就是订单信息对象
        //给req对象赋值
        req.appId = "wx223ecb204beddad4";//APPID
        req.partnerId = bean.partnerid;//    商户号
        req.prepayId = bean.prepayid;//  预付款ID
        req.nonceStr = bean.noncestr;//随机数
        req.timeStamp = bean.timestamp;//时间戳
        req.packageValue = "Sign=WXPay";//固定值Sign=WXPay
        req.sign = bean.sign;//签名
        api.sendReq(req);//将订单信息对象发送给微信服务器，即发送支付请求

    }

    //发起支付宝支付
    private void payAli(final String orderInfo) {
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 弹出支付返回结果
     */
    private void showPlayResultDialog(String result) {
        Dialog dialog;
        //创建dialog实例化对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomTheme_Dialog);
        //加载 布局
        View v = LayoutInflater.from(this).inflate(R.layout.activity_result_layout, null);
        llQlx = v.findViewById(R.id.ll_qlx);
        llHym = v.findViewById(R.id.ll_hym);
        LinearLayout back = v.findViewById(R.id.heard_back);
        TextView heard_title = v.findViewById(R.id.heard_title);
        TextView tvMoney = v.findViewById(R.id.tv_money);
        TextView tvPayResult = v.findViewById(R.id.tv_pay_result);
        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //获取窗口对象
        WindowManager windowManager = this.getWindowManager();
        //获取屏幕宽高尺寸
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        //设置布局
        dialog.setContentView(v);

        heard_title.setText("支付结果");
        tvMoney.setText("" + StrUtils.moenyToDH(SPUtils.getInstance(this).getString("TotalMoney")));
        //        ImmersionBar.with(this).titleBar(rlHead).statusBarColor(R.color.white).statusBarDarkFont(false).init();
        tvPayResult.setText(result);
        if ("支付成功".equals(result)) {
            getShaky();
        }
        llHym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHymOrderNo();
            }
        });

        //去抽奖
        llQlx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity(TravelActivity.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PayResultEvent event = new PayResultEvent();
                event.setResult(RESULT_OK);
                EventBus.getDefault().post(event);
                FinishActivityManager.getManager().finishActivity(PayActivity.class);
                PayActivity.this.finish();

            }
        });
    }

    //获取羊毛订单id
    private void getHymOrderNo() {
        addSubscription(RequestClient.GetHymOrder(orderNo, this, new NetSubscriber<BaseResultBean<List<WoolBean.HymBean>>>(this, false) {
            @Override
            public void onResultNext(BaseResultBean<List<WoolBean.HymBean>> model) {
                bundle = new Bundle();
                bundle.putInt("id", model.data.get(0).Id);
                JumpUtils.dataJump(PayActivity.this, WoolDetailsActivity.class, bundle, true);
            }

        }));
    }

    private void getShaky() {
        addSubscription(RequestClient.GetHymOrQlx(TorderNo, this, new NetSubscriber<BaseResultBean<ShakyBean>>(this) {
            @Override
            public void onResultNext(BaseResultBean<ShakyBean> model) {
                orderNo = model.data.orderno;
                fillView(model.data);
            }
        }));
    }

    private void fillView(ShakyBean bean) {
        if (bean.ishym == 1) {
            llHym.setVisibility(View.VISIBLE);
        }
        if (bean.isqlx == 1) {
            llQlx.setVisibility(View.VISIBLE);
        }
    }
}
