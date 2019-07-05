//package com.fdl.activity.account.money;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.fdl.BaseActivity;
//import com.fdl.requestApi.RequestClient;
//import com.fdl.utils.DialogUtils;
//import com.fdl.utils.IsBang;
//import com.fdl.utils.SPUtils;
//import com.fdl.utils.StrUtils;
//import com.sg.cj.snh.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * <p>desc：<p>
// * <p>author：DreamFly<p>
// * <p>creatTime：2019/4/2<p>
// * <p>changeTime：2019/4/2<p>
// * <p>version：1<p>
// */
//public class CapitalActivity extends BaseActivity {
//    @BindView(R.id.heard_back)
//    LinearLayout heardBack;
//    @BindView(R.id.heard_title)
//    TextView heardTitle;
//    @BindView(R.id.heard_menu)
//    ImageView heardMenu;
//    @BindView(R.id.heard_tv_menu)
//    TextView heardTvMenu;
//
//    @BindView(R.id.rl_head)
//    LinearLayout rlHead;
//    @BindView(R.id.tv_money)
//    TextView tvMoney;
//    @BindView(R.id.ll_01)
//    LinearLayout ll01;
//    @BindView(R.id.ll_02)
//    LinearLayout ll02;
//
//    private int index = 1;
//    public static boolean isForeground = true;
//    private DialogUtils dialogUtils;
//    @Override
//    protected void initContentView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_capital_layout);
//        dialogUtils = new DialogUtils(this);
//    }
//
//    @Override
//    public void setUpViews() {
//        isForeground = false;
//        heardTitle.setText("账户资金");
//        IsBang.setImmerHeard(this,rlHead);
//        rlMenu.setVisibility(View.VISIBLE);
//        heardTvMenu.setText("明细");
//    }
//
//    @Override
//    public void setUpLisener() {
//
//    }
//
//    @Override
//    public void getDataOnCreate() {
//        super.getDataOnCreate();
//        addSubscription(RequestClient.GetAccountMoney(index, this, new NetSubscriber<BaseResultBean<MoneyBean>>(this,true) {
//            @Override
//            public void onResultNext(BaseResultBean<MoneyBean> model) {
//                tvMoney.setText(StrUtils.moenyToDH(model.data.HavaMoney+""));
//            }
//        }));
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }
//
//    @OnClick({R.id.heard_back, R.id.ll_01, R.id.ll_02,R.id.rl_menu})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.heard_back:
//                this.finish();
//                break;
//            case R.id.ll_01:
//                if ("1".equals(SPUtils.getInstance(this).getString(Contans.IS_FULL))) {
//                    jumpActivity(WithdrawRecordActivity.class);
//
//                } else if ("0".equals(SPUtils.getInstance(this).getString(Contans.IS_FULL))) {
//                    dialogUtils.twoBtnDialog("是否马上完善店铺信息", new DialogUtils.ChoseClickLisener() {
//                        @Override
//                        public void onConfirmClick(View v) {
//                            dialogUtils.dismissDialog();
//                            JumpUtils.simpJump(CapitalActivity.this, PerfectMyLocalActivity.class, false);
//                        }
//
//                        @Override
//                        public void onCancelClick(View v) {
//                            dialogUtils.dismissDialog();
//                        }
//                    }, true);
//                }
//                break;
//            case R.id.ll_02:
//                jumpActivity(MyBanksActivity.class);
//                break;
//            case R.id.rl_menu:
//                jumpActivity(WithdrawListActivity.class);
//                break;
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        isForeground = true;
//    }
//}
