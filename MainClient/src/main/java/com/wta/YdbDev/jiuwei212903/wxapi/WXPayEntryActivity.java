package com.wta.YdbDev.jiuwei212903.wxapi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.buy.PayActivity;
import com.fdl.activity.buy.PayResultActivity;
import com.fdl.activity.goTravel.TravelActivity;
import com.fdl.activity.wool.WoolDetailsActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ShakyBean;
import com.fdl.bean.WoolBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.FinishActivityManager;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/28<p>
 * <p>changeTime：2018/12/28<p>
 * <p>version：1<p>
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    //    @BindView(R.id.heard_back)
//    LinearLayout heardBack;
//    @BindView(R.id.heard_title)
//    TextView heardTitle;
//    @BindView(R.id.heard_menu)
//    ImageView heardMenu;
//    @BindView(R.id.heard_tv_menu)
//    TextView heardTvMenu;
//    @BindView(R.id.rl_head)
//    LinearLayout rlHead;
    private IWXAPI api;
    private Bundle bundle;
    private int errCord;
    private String TorderNo, orderNo;//总订单、订单号
    private LinearLayout llHym, llQlx, rl_head;

    private ImmersionBar immersionBar;
    Dialog dialog;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
//        setContentView(R.layout.a);
//        immersionBar = ImmersionBar.with(this);
//        if (ImmersionBar.hasNotchScreen(this)) {
//            IsBang.setImmerHeard(this, rlHead, "#ffffff");
//        } else {
//            immersionBar.statusBarColor(R.color.white).titleBar(R.id.rl_head).init();
//        }
//        immersionBar.statusBarDarkFont(true).init();
        api = WXAPIFactory.createWXAPI(this, "wx223ecb204beddad4");//这里填入自己的微信APPID
        api.registerApp("wx223ecb204beddad4");
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void setUpViews() {
//        heardTitle.setText("支付结果");
//        heardBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                FinishActivityManager.getManager().finishActivity(PayActivity.class);
//                WXPayEntryActivity.this.finish();
//            }
//        });
        TorderNo = SPUtils.getInstance(this).getString("TorderNo");
        getShaky();
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        SgLog.d("coyc", "onPayFinish, errCode = " + baseResp.errCode);
        bundle = new Bundle();
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            errCord = baseResp.errCode;
            if (errCord == 0) {
                bundle.putString("result", "支付成功");
                showPlayResultDialog("支付成功");

            } else if (errCord == -2) {
                bundle.putString("result", "取消支付");
                this.finish();
            } else {
                bundle.putString("result", "支付失败");
                FinishActivityManager.getManager().finishActivity(PayActivity.class);
                JumpUtils.dataJump(this, PayResultActivity.class, bundle, true);
            }
            //这里接收到了返回的状态码可以进行相应的操作，如果不想在这个页面操作可以把状态码存在本地然后finish掉这个页面，这样就回到了你调起支付的那个页面
            //获取到你刚刚存到本地的状态码进行相应的操作就可以了
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    /**
     * 弹出支付返回结果
     */
    private void showPlayResultDialog(String result) {

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
        heard_title.setText("支付结果");
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

        tvMoney.setText(StrUtils.moenyToDH(SPUtils.getInstance(this).getString("TotalMoney")));
        tvPayResult.setText(result);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
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
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (errCord == 0) {
                    FinishActivityManager.getManager().finishActivity(PayActivity.class);
                }
                WXPayEntryActivity.this.finish();
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
                JumpUtils.dataJump(WXPayEntryActivity.this, WoolDetailsActivity.class, bundle, true);

            }

        }));
    }

    //去抽奖
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
