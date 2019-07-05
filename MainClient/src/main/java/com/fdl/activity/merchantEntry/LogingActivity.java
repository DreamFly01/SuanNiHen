package com.fdl.activity.merchantEntry;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CityBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.IdentifyCodeView;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/22<p>
 * <p>changeTime：2019/1/22<p>
 * <p>version：1<p>
 */
public class LogingActivity extends BaseActivity {
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
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.identify_code_view)
    IdentifyCodeView identifyCodeView;

    private DialogUtils dialogUtils;
    private Bundle bundle;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_loginmerchant_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("商家入驻");
        IsBang.setImmerHeard(this, rlHead);
        identifyCodeView.setActivity(this);
        identifyCodeView.setEt_tel(etPhone);
        if (Contans.debug) {
            etPhone.setText("17688829959");
        }
    }

    @Override
    public void setUpLisener() {
        identifyCodeView.setIdentifyCodeViewLisener(new IdentifyCodeView.IdentifyCodeViewLisener() {
            @Override
            public void onYCIdentifyCodeViewClickLisener() {
                getCode();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.heard_back, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                this.finish();
                break;
            case R.id.tv_commit:
                if (Contans.debug) {
                    bundle = new Bundle();
                    bundle.putString("phone", etPhone.getText().toString().trim());
                    JumpUtils.dataJump(LogingActivity.this, EntryChoseActivity.class, bundle, false);
                } else {

                    if (check()) {
                        loging();
                    }
                }

                break;
        }
    }

    private boolean check() {
        if (!identifyCodeView.isGetVertifyCode()) {
            dialogUtils.noBtnDialog("请先获取短信验证码");
            return false;
        }
        if (StrUtils.isEmpty(etCode.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入验证码");
            return false;
        }
        return true;
    }

    private void getCode() {
        addSubscription(RequestClient.PostSms(etPhone.getText().toString().trim(), this, new NetSubscriber<BaseResultBean<List<CityBean>>>(LogingActivity.this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<CityBean>> model) {
                showShortToast("短信发送成功，请注意查收！");
                identifyCodeView.StartCountDown(60000);
            }
        }));
    }

    private void loging() {
        addSubscription(RequestClient.MerchantLogin(etPhone.getText().toString().trim(), etCode.getText().toString().trim(), this, new NetSubscriber<BaseResultBean<List<CityBean>>>(LogingActivity.this, true) {
            @Override
            public void onResultNext(BaseResultBean<List<CityBean>> model) {
                bundle = new Bundle();
                bundle.putString("phone", etPhone.getText().toString().trim());
                JumpUtils.dataJump(LogingActivity.this, EntryChoseActivity.class, bundle, false);
//                jumpActivity(EntryChoseActivity.class);
            }
        }));
    }
}
