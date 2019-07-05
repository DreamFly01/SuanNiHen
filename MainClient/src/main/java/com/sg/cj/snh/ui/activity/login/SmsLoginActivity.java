package com.sg.cj.snh.ui.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fdl.activity.main.MainActivity;
import com.fdl.utils.JumpUtils;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.bean.WechatLoginSuccessEnevt;
import com.sg.cj.snh.contract.login.LoginContract;
import com.sg.cj.snh.contract.login.RegisterContract;
import com.sg.cj.snh.presenter.login.LoginPresenter;
import com.sg.cj.snh.presenter.login.RegisterPresenter;
import com.sg.cj.snh.view.SmsBtnView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : ${CHENJIE}
 * created at  2018/12/11 16:04
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SmsLoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


  @BindView(R.id.et_username)
  EditText etUsername;
  @BindView(R.id.et_psd)
  EditText etPsd;
  @BindView(R.id.smsBtn)
  SmsBtnView smsBtn;
  @BindView(R.id.btn_login)
  Button btnLogin;
  @BindView(R.id.txt_sms_login)
  TextView txtSmsLogin;
  @BindView(R.id.txt_regiest)
  TextView txtRegiest;

  @Override
  protected int getLayout() {
    return R.layout.activity_sms_login;
  }

  @Override
  protected void initInject() {
    getActivityComponent().inject(this);
  }

  @Override
  protected void initEventAndData() {
    smsBtn.setClick(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mPresenter.doSendPhoneCode(etUsername.getText().toString(),1);
      }
    });
  }
  @Override
  public void doValidateCode() {

  }
  @Override
  public void doForgetloginpwd() {

  }
  @Override
  public void doSendPhoneCodeSuccess() {
    smsBtn.startTime();
  }

  @Override
  public void loginSuccess() {
    EventBus.getDefault().post(new WechatLoginSuccessEnevt());

    Bundle bundle = new Bundle();
    bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX,0);
    JumpUtils.dataJump(SmsLoginActivity.this,MainActivity.class,bundle,true);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if(null!=smsBtn) {
      smsBtn.destory();
    }
  }


  private void smsLogin(){
    String phone=etUsername.getText().toString();
    String code=etPsd.getText().toString();

    if(TextUtils.isEmpty(phone)){
      showErrorMsg("请输入手机号");
      return;
    }
    if(TextUtils.isEmpty(code)){
      showErrorMsg("请输入验证码");
      return;
    }

    smsBtn.onFinish();
    mPresenter.doPhoneLogin(phone,code);
  }

  @OnClick({R.id.smsBtn, R.id.btn_login, R.id.txt_sms_login, R.id.txt_regiest})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.smsBtn:
//        mPresenter.doSendPhoneCode(etUsername.getText().toString(),1);
        break;
      case R.id.btn_login:
        smsLogin();
        break;
      case R.id.txt_sms_login:
        finish();
        break;
      case R.id.txt_regiest:
        startAct(RegisterActivity.class);
        finish();
        break;
    }
  }

  @Override
  public void wechatFinish() {

  }
}
