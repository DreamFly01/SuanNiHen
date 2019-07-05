package com.fdl.activity.account;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.account.money.MyBanksActivity;
import com.fdl.activity.account.money.WithdrawActivity;
import com.fdl.activity.account.money.WithdrawListActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.sg.cj.snh.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/18<p>
 * <p>changeTime：2019/1/18<p>
 * <p>version：1<p>
 */
public class AccountMoneyActivity extends BaseActivity {
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
    @BindView(R.id.ll_withdraw)
    LinearLayout llWithdraw;
    @BindView(R.id.ll_back_money)
    LinearLayout llBackMoney;
    @BindView(R.id.ll_score)
    LinearLayout llScore;
    @BindView(R.id.tv_back_money)
    TextView tvBackMoney;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_my_bankcards)
    LinearLayout llMyBankcards;
private Bundle bundle;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_money_layout);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        heardTvMenu.setVisibility(View.VISIBLE);
        heardTvMenu.setText("明细");
        heardTvMenu.setTextColor(Color.WHITE);
        heardTitle.setText("我的卡包");
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

    public double minMoney;
    @Override
    public void getDataOnCreate() {
        super.getDataOnCreate();
        addSubscription(RequestClient.GetWalletInfo(this, new NetSubscriber<BaseResultBean>(this, false) {
            @Override
            public void onResultNext(BaseResultBean model) {
                avi.setVisibility(View.GONE);
                tvBackMoney.setText("¥"+model.dfmoney + "");
                tvScore.setText(model.Integral + "");
                tvMoney.setText(model.money);
                minMoney = model.minTxMoney;
            }
        }));
    }

    @OnClick({R.id.heard_back, R.id.heard_tv_menu, R.id.ll_withdraw, R.id.ll_back_money, R.id.ll_score,R.id.ll_my_bankcards})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.heard_tv_menu:
//                bundle = new Bundle();
//                bundle.putInt("type",0);
                JumpUtils.simpJump(this,WithdrawListActivity.class,false);
                break;
            case R.id.ll_withdraw:
                JumpUtils.simpJump(this,WithdrawActivity.class,false);
                break;
            case R.id.ll_back_money:
                bundle = new Bundle();
                bundle.putInt("type",1);
                JumpUtils.dataJump(this,AccountDetailsActivity.class,bundle,false);
                break;
            case R.id.ll_score:
                bundle = new Bundle();
                bundle.putInt("type",2);
                JumpUtils.dataJump(this,AccountDetailsActivity.class,bundle,false);
                break;
            case R.id.ll_my_bankcards:
                JumpUtils.simpJump(this,MyBanksActivity.class,false);
                break;
        }
    }
}
