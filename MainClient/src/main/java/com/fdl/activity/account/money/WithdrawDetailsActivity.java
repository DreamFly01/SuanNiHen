package com.fdl.activity.account.money;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdl.bean.BaseResultBean;
import com.fdl.bean.WithdrawDetailsBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.ImageUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.StrUtils;
import com.fdl.utils.TimeUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.snh.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/16<p>
 * <p>changeTime：2019/4/16<p>
 * <p>version：1<p>
 */
public class WithdrawDetailsActivity extends Activity {
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
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_payState)
    TextView tvPayState;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_orderNo)
    TextView tvOrderNo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_sxf)
    TextView tvSxf;
    @BindView(R.id.tv_bankCard)
    TextView tvBankCard;
    @BindView(R.id.tv_withdraw_time)
    TextView tvWithdrawTime;
    @BindView(R.id.tv_lsh)
    TextView tvLsh;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.tv_myMoney)
    TextView tvMyMoney;
    @BindView(R.id.iv_process)
    ImageView ivProcess;
    private int id;
    private int type; //类型【1充值，2线上消费，3线下消费，4转出，5转入，6提现，7提现驳回,8消费奖励，9好友帮薅羊毛收益,10帮好友薅羊收益】
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawdetails_layout);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        if (null != bundle) {
            id = bundle.getInt("id");
            type = bundle.getInt("type");
        }
        setView();
        getData();
    }
    private ImmersionBar immersionBar;
    private void setView() {
        immersionBar = ImmersionBar.with(this);
        if (ImmersionBar.hasNotchScreen(this)) {
            IsBang.setImmerHeard(this, rlHead, "#ffffff");
        } else {
            immersionBar.statusBarColor(R.color.white).titleBar(R.id.rl_head).init();
        }
        immersionBar.statusBarDarkFont(true).init();
        if (type == 6 | type == 7) {
            heardTitle.setText("提现详情");
            ll02.setVisibility(View.VISIBLE);
            ll01.setVisibility(View.GONE);
        } else {
            heardTitle.setText("明细详情");
            ll01.setVisibility(View.VISIBLE);
            ll02.setVisibility(View.GONE);
        }
    }

    private void getData() {
        RequestClient.GetSupplierMoneyDetails(id + "", this, new NetSubscriber<BaseResultBean<WithdrawDetailsBean>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<WithdrawDetailsBean> model) {
                fillView(model.data);
            }
        });
    }

    private void fillView(WithdrawDetailsBean bean) {
        if (type == 6 || type == 7) {
            tvMoney.setText("¥" + StrUtils.moenyToDH(bean.Money + ""));
            tvSxf.setText("¥" + StrUtils.moenyToDH(bean.Poundage + ""));
            tvWithdrawTime.setText(TimeUtils.timeStamp2Date(bean.CreateTime + "", ""));
            tvBankCard.setText(bean.Bank + " 尾号" + bean.BankNo.substring(bean.BankNo.length() - 4, bean.BankNo.length()));
            switch (bean.CheckState) {
                case 0:
                    ivProcess.setBackgroundResource(R.drawable.withdraw_process_0);
                    break;
                case 1:
                    ivProcess.setBackgroundResource(R.drawable.withdraw_process_1);
                    break;
                case 2:
                    ivProcess.setBackgroundResource(R.drawable.withdraw_process_2);
                    break;

            }
        } else {
            ImageUtils.loadUrlImage(this, bean.Icon, ivLogo);
            tvPayState.setText(bean.PayMethods);
            tvContent.setText(bean.ShopGoodsName);
            tvOrderNo.setText(bean.OrderNo);
            tvTime.setText(TimeUtils.timeStamp2Date(bean.CreateTime + "", ""));
            tvMyMoney.setText(bean.Money + "");
        }
    }

    @OnClick(R.id.heard_back)
    public void onClick() {
        this.finish();
    }
}
