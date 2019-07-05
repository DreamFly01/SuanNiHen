package com.fdl.activity.set;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.main.MainActivity;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.GlideCacheUtil;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.ui.activity.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/2<p>
 * <p>changeTime：2019/1/2<p>
 * <p>version：1<p>
 */
public class SetActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.ll_03)
    LinearLayout ll03;
    @BindView(R.id.ll_04)
    LinearLayout ll04;
    @BindView(R.id.ll_05)
    LinearLayout ll05;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.heard_tv_menu)
    TextView heardTvMenu;
    @BindView(R.id.tv_cahe_size)
    TextView tvCaheSize;

    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this, rlHead);
        heardTitle.setText("设置");
        tvCaheSize.setText("("+GlideCacheUtil.getInstance().getCacheSize(this)+")");
    }

    @Override
    public void setUpLisener() {

    }


    @OnClick({R.id.heard_back, R.id.ll_01, R.id.ll_02, R.id.ll_03, R.id.ll_04, R.id.ll_05, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.ll_01:
                jumpActivity(SelfInfoActivity.class);
                break;
            case R.id.ll_02:
                jumpActivity(AccountSafeActivity.class);
                break;
            case R.id.ll_03:
                jumpActivity(AboutActivity.class);
                break;
            case R.id.ll_04:
                jumpActivity(FeedActivity.class);
                break;
            case R.id.ll_05:
                dialogUtils.twoBtnDialog("确认清除缓存么？", new DialogUtils.ChoseClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        GlideCacheUtil.getInstance().clearImageAllCache(SetActivity.this);
                        tvCaheSize.setText("(0.00k)");
                        dialogUtils.dismissDialog();
                    }

                    @Override
                    public void onCancelClick(View v) {
                        dialogUtils.dismissDialog();
                    }
                }, true);
                break;
            case R.id.tv_exit:
                String tel = PartyApp.getAppComponent().getDataManager().getTel();
//                PartyApp.getAppComponent().getDataManager().cleanData();
                SPUtils.getInstance(this).cleanUserData();
                PartyApp.getAppComponent().getDataManager().setTel(tel);
                NIMClient.getService(AuthService.class).logout();
                Bundle bundle = new Bundle();
                bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX,0);
                JumpUtils.dataJump(this, LoginActivity.class, bundle,true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
