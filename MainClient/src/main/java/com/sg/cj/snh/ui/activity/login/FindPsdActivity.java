package com.sg.cj.snh.ui.activity.login;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.contract.login.LoginContract;
import com.sg.cj.snh.presenter.login.LoginPresenter;
import com.sg.cj.snh.view.SmsBtnView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : ${CHENJIE}
 * created at  2018/12/11 16:04
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class FindPsdActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


  @BindView(R.id.et_username)
  EditText etUsername;
  @BindView(R.id.et_psd)
  EditText etPsd;
  @BindView(R.id.smsBtn)
  SmsBtnView smsBtn;
  @BindView(R.id.btn_login)
  Button btnLogin;
  @BindView(R.id.layout_sms)
  LinearLayout layoutSms;
  @BindView(R.id.et_pwd1)
  EditText etPwd1;
  @BindView(R.id.et_pwd2)
  EditText etPwd2;
  @BindView(R.id.btn_find)
  Button btnFind;
  @BindView(R.id.layout_pwd)
  LinearLayout layoutPwd;

  @Override
  protected int getLayout() {
    return R.layout.activity_find_psd;
  }

  @Override
  protected void initInject() {
    getActivityComponent().inject(this);
  }

  @Override
  protected void initEventAndData() {
    btnLogin.setText("下一步");
    smsBtn.setClick(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mPresenter.doSendPhoneCode(etUsername.getText().toString(), 5);
      }
    });
  }

  @Override
  public void doValidateCode() {
    layoutPwd.setVisibility(View.VISIBLE);
    layoutSms.setVisibility(View.GONE);
  }

  @Override
  public void loginSuccess() {

  }

  @Override
  public void doSendPhoneCodeSuccess() {
    smsBtn.startTime();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (null != smsBtn) {
      smsBtn.destory();
    }
  }

  private void next() {
    if (null != smsBtn) {
      smsBtn.onFinish();
    }
    mPresenter.doValidateCode(etUsername.getText().toString(), etPsd.getText().toString(), 5);
  }


  @Override
  public void wechatFinish() {

  }

  @Override
  public void doForgetloginpwd() {
finish();
  }

  private void forgetloginpwd(){

    String psd=etPwd1.getText().toString();
    String psdAgain=etPwd2.getText().toString();

    if(TextUtils.isEmpty(psd)){
      ToastUtil.shortShow("请输入登录密码");
      return;
    }if(TextUtils.isEmpty(psdAgain)){
      ToastUtil.shortShow("请输入确认密码");
      return;
    }
    if(!psd.equals(psdAgain)){
      ToastUtil.shortShow("两次输入的密码不一样，请重新输入");
      etPwd1.setText("");
      etPwd2.setText("");
      return;
    }

    mPresenter.doForgetloginpwd(etUsername.getText().toString(),psd);

  }

  @OnClick({R.id.btn_login, R.id.btn_find})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_login:
        next();
        break;
      case R.id.btn_find:
        forgetloginpwd();
        break;
    }
  }
}
