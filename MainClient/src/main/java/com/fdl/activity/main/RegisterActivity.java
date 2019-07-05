package com.fdl.activity.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdl.BaseActivity;
import com.fdl.activity.set.ProtocolActivity;
import com.fdl.bean.BaseResultBean;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.utils.Contans;
import com.fdl.utils.DialogUtils;
import com.fdl.utils.IsBang;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.Md5Utils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.fdl.wedgit.IdentifyCodeView;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.response.login.LoginResponse;
import com.sg.cj.snh.presenter.login.RegisterPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/21<p>
 * <p>changeTime：2019/1/21<p>
 * <p>version：1<p>
 */
public class RegisterActivity extends BaseActivity {
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
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.ic_getCode)
    IdentifyCodeView icGetCode;
    @BindView(R.id.et_psw1)
    EditText etPsw1;
    @BindView(R.id.et_psw2)
    EditText etPsw2;
    @BindView(R.id.ll_loging_2)
    LinearLayout llLoging2;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.radio_button_1)
    ImageView radioButton1;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_layout);
        dialogUtils = new DialogUtils(this);
    }

    @Override
    public void setUpViews() {
        heardTitle.setText("注册");
        icGetCode.setEt_tel(etPhone);
        icGetCode.setActivity(this);
        IsBang.setImmerHeard(this,rlHead);
    }

    @Override
    public void setUpLisener() {
        icGetCode.setIdentifyCodeViewLisener(new IdentifyCodeView.IdentifyCodeViewLisener() {
            @Override
            public void onYCIdentifyCodeViewClickLisener() {
                getCode();

            }
        });
    }


    @OnClick({R.id.heard_back, R.id.btn_commit, R.id.radio_button_1, R.id.tv_xieyi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heard_back:
                JumpUtils.simpJump(RegisterActivity.this,MainActivity.class,true);
                break;
            case R.id.btn_commit:
                if (checkPsw()) {
                    regist();
                }
                break;
            case R.id.radio_button_1:
                if (isClick) {
                    radioButton1.setBackgroundResource(R.drawable.xiyi_2);
                    isClick = false;
                } else {
                    radioButton1.setBackgroundResource(R.drawable.xiyi_1);
                    isClick = true;
                }
                break;
            case R.id.tv_xieyi:
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                JumpUtils.dataJump(this, ProtocolActivity.class, bundle, false);
                break;
        }
    }

    private boolean isClick = true;

    private boolean checkPsw() {
        String cpsw1 = etPsw1.getText().toString().trim();
        String cpsw2 = etPsw2.getText().toString().trim();
        if (!StrUtils.checkMobile(etPhone.getText().toString().trim())) {
            showDialog("请输入正确的手机号码！");
            return false;
        }
        if (StrUtils.isEmpty(etPhone.getText().toString().trim())) {
            showDialog("手机号码不能为空！");
            return false;
        }
        if (StrUtils.isEmpty(cpsw1)) {
            showDialog("密码不能为空！");
            return false;
        }

        if(!StrUtils.isPsw(etPsw1.getText().toString().trim())){
            showDialog("请设置6-20字母数字组合密码");
            return false;
        }
        if (!(cpsw1.equals(cpsw2))) {
            showDialog("两次密码不一致！");
            return false;
        }
        if (!isClick) {
            showDialog("请同意forany用户协议！");
            return false;
        }
        if (StrUtils.isEmpty(etCode.getText().toString())) {
            showDialog("请输入验证码！");
            return false;
        }
        if (!icGetCode.isGetVertifyCode()) {
            showDialog("请点击获取验证码！");
            return false;
        }
        return true;
    }

    private void getCode() {
        addSubscription(RequestClient.GetPhoneCode(etPhone.getText().toString().trim(), "2", this, new NetSubscriber<BaseResultBean>(this, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                icGetCode.StartCountDown(60000);
            }
        }));
    }

    private void regist() {
        addSubscription(RequestClient.Regist(etPhone.getText().toString().trim(), Md5Utils.md5(etPsw1.getText().toString()), "", etCode.getText().toString(), this, new NetSubscriber<BaseResultBean<LoginResponse>>(this, true) {
            @Override
            public void onResultNext(BaseResultBean<LoginResponse> model) {
                    saveLoginResponse(model.data);
                JumpUtils.simpJump(RegisterActivity.this,MainActivity.class,true);
            }
        }));
    }

    private DialogUtils dialogUtils;

    private void showDialog(String content) {
        dialogUtils.simpleDialog(content, new DialogUtils.ConfirmClickLisener() {
            @Override
            public void onConfirmClick(View v) {
                dialogUtils.dismissDialog();
            }
        }, true);
    }

    private DataManager mDataManager;
    public void saveLoginResponse(LoginResponse response) {
        mDataManager = PartyApp.getAppComponent().getDataManager();
        mDataManager.setId(response.Id);
        mDataManager.setGradeId(response.GradeId);
        mDataManager.setBalanceOne(response.BalanceOne);
        mDataManager.setIntegral(response.Integral);
        mDataManager.setPayment(response.Payment);
        mDataManager.setTel(response.Tel);
        mDataManager.setUnionID(response.UnionID);
        mDataManager.setWxHeadImg(response.WxHeadImg);
        mDataManager.setWxNickName(response.WxNickName);
        mDataManager.setWxOpenId(response.WxOpenId);
        if(null!=response.nimResult){
            SPUtils.getInstance(PartyApp.getInstance()).saveData(Contans.IM_TOKEN,response.nimResult.Token);
            SPUtils.getInstance(PartyApp.getInstance()).saveData(Contans.IM_ACCOUNT,response.nimResult.Accid);
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        JumpUtils.simpJump(RegisterActivity.this,MainActivity.class,true);
    }
}
