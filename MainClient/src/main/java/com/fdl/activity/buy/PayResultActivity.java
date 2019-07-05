package com.fdl.activity.buy;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.goTravel.TravelActivity;
import com.fdl.activity.main.MainActivity;
import com.fdl.activity.wool.WoolDetailsActivity;
import com.fdl.bean.BaseResultBean;
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
import com.netease.nim.uikit.business.session.emoji.EmoticonView;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 余额支付结果页
 */
public class PayResultActivity extends BaseActivity {
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
    @BindView(R.id.tv_pay_result)
    TextView tvPayResult;
    @BindView(R.id.ll_hym)
    LinearLayout llHym;
    @BindView(R.id.ll_qlx)
    LinearLayout llQlx;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    private DialogUtils dialogUtils;
    private int hymId;
    private Bundle bundle;
    private String result;
    private String TorderNo, orderNo;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setImm(false);
        setContentView(R.layout.activity_result_layout);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            result = bundle.getString("result");
        }
        dialogUtils = new DialogUtils(this);

    }

    @Override
    public void setUpViews() {
        heardTitle.setText("支付结果");
        tvPayResult.setText(result);
        TorderNo = SPUtils.getInstance(this).getString("TorderNo");
        if ("支付成功".equals(result)) {
            getShaky();
        }
        tvMoney.setText(StrUtils.moenyToDH(SPUtils.getInstance(this).getString("TotalMoney" + "")));
    }

    @Override
    public void setUpLisener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }

    //获取羊毛订单id
    private void getHymOrderNo() {

        addSubscription(RequestClient.GetHymOrder(orderNo, this, new NetSubscriber<BaseResultBean<List<WoolBean.HymBean>>>(this, false) {
            @Override
            public void onResultNext(BaseResultBean<List<WoolBean.HymBean>> model) {
                bundle = new Bundle();
                bundle.putInt("id", model.data.get(0).Id);
                JumpUtils.dataJump(PayResultActivity.this, WoolDetailsActivity.class, bundle, true);

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

    @OnClick({R.id.ll_hym, R.id.ll_qlx, R.id.heard_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_hym:
                getHymOrderNo();
                break;
            case R.id.ll_qlx:
                jumpActivity(TravelActivity.class);
                break;
            case R.id.heard_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersionBar.with(this).statusBarColor(R.color.white).statusBarDarkFont(true).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
