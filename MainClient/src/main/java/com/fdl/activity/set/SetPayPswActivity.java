package com.fdl.activity.set;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.AnimUtil;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.Md5Utils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.IdentifyCodeView;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/3<p>
 * <p>changeTime：2019/1/3<p>
 * <p>version：1<p>
 */
public class SetPayPswActivity extends BaseActivity {
    @BindView(R.id.heard_back)
    LinearLayout heardBack;
    @BindView(R.id.heard_title)
    TextView heardTitle;
    @BindView(R.id.heard_menu)
    ImageView heardMenu;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.tv_code)
    EditText tvCode;
    @BindView(R.id.identify_code_view)
    IdentifyCodeView identifyCodeView;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.et_old)
    EditText etOld;
    @BindView(R.id.et_new)
    EditText etNew;
    @BindView(R.id.ll_02)
    LinearLayout ll02;

    private DialogUtils dialogUtils;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setpaypsw_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        IsBang.setImmerHeard(this,rlHead);
        etPhone.setKeyListener(null);
        heardTitle.setText("设置支付密码");
        if (!StrUtils.isEmpty(PartyApp.getAppComponent().getDataManager().getTel())) {
            StringBuffer sbf = new StringBuffer(PartyApp.getAppComponent().getDataManager().getTel());
            etPhone.setText(sbf.replace(3, 7, "****").toString());
        }

        identifyCodeView.setActivity(this);
//        identifyCodeView.setEt_tel(etPhone);
        identifyCodeView.setIdentifyCodeViewLisener(new IdentifyCodeView.IdentifyCodeViewLisener() {
            @Override
            public void onYCIdentifyCodeViewClickLisener() {
                addSubscription(RequestClient.GetPhoneCode(PartyApp.getAppComponent().getDataManager().getTel(), "3", SetPayPswActivity.this, new NetSubscriber<BaseResultBean>(SetPayPswActivity.this, true) {
                    @Override
                    public void onResultNext(BaseResultBean model) {
                        showShortToast("短信已发送,请注意查收!");
                        identifyCodeView.StartCountDown(60000);
                    }
                }));
            }
        });
    }

    @Override
    public void setUpLisener() {

    }


    @OnClick({R.id.heard_back, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                break;
            case R.id.tv_commit:
                if (isGetCode) {
                    if (check()) {
                        validataCode();
                    }
                } else {
                    if (checkPsw()) {
                        setPayPsw();
                    }
                }
                break;
        }
    }

    private boolean isGetCode = true;

    private void validataCode() {
        addSubscription(RequestClient.ValidateCode(PartyApp.getAppComponent().getDataManager().getTel(), "3", tvCode.getText().toString().trim(), this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                AnimUtil.FlipAnimatorXViewShow(ll01, ll02, 200);
//                ll01.setVisibility(View.GONE);
//                ll02.setVisibility(View.VISIBLE);
                isGetCode = false;
            }
        }));
    }

    private void setPayPsw() {
        String payment = Md5Utils.md5(etNew.getText().toString().trim());
        addSubscription(RequestClient.FixPayPsw(payment, this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                dialogUtils.simpleDialog("设置支付密码成功", new DialogUtils.ConfirmClickLisener() {
                    @Override
                    public void onConfirmClick(View v) {
                        dialogUtils.dismissDialog();
                        SetPayPswActivity.this.finish();
                    }
                }, false);
            }
        }));
    }

    private boolean checkPsw() {
        if (StrUtils.isEmpty(etNew.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入支付密码");
            return false;
        }
        if (StrUtils.isEmpty(etOld.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请再次输入支付密码");
            return false;
        }
        if (!etNew.getText().toString().trim().equals(etOld.getText().toString().trim())) {
            dialogUtils.noBtnDialog("两次输入密码不一致");
            return false;
        }
        return true;
    }

    private boolean check() {
        if (StrUtils.isEmpty(etPhone.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入手机号码");
            return false;
        }
        if (!identifyCodeView.isGetVertifyCode()) {
            dialogUtils.noBtnDialog("请获取验证码");
            return false;
        }
        if (StrUtils.isEmpty(tvCode.getText().toString().trim())) {
            dialogUtils.noBtnDialog("请输入验证码");
            return false;
        }
        return true;
    }
}
