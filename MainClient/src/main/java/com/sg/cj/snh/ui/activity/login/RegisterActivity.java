package com.sg.cj.snh.ui.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.bean.WechatLoginSuccessEnevt;
import com.sg.cj.snh.contract.login.RegisterContract;
import com.sg.cj.snh.presenter.login.RegisterPresenter;
import com.sg.cj.snh.view.SmsBtnView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : ${CHENJIE}
 * created at  2018/11/26 16:22
 * e_mail : chenjie_goodboy@163.com
 * describle : 注册
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

private boolean codeInput=false;

  @BindView(R.id.et_phone)
  EditText etPhone;
  @BindView(R.id.et_code)
  EditText etCode;
  @BindView(R.id.smsBtn)
  SmsBtnView smsBtn;
  @BindView(R.id.et_nickname)
  EditText etNickname;
  @BindView(R.id.et_psd)
  EditText etPsd;
  @BindView(R.id.et_psd_again)
  EditText etPsdAgain;
  @BindView(R.id.btn_register)
  Button btnRegister;
  @BindView(R.id.btn_back_login)
  Button btnBackLogin;

  @Override
  protected void initInject() {
    getActivityComponent().inject(this);
  }

  @Override
  protected int getLayout() {
    return R.layout.activity_register;
  }

  @Override
  protected void initEventAndData() {

    smsBtn.setClick(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        mPresenter.doSendPhoneCode(etPhone.getText().toString(),2);
      }
    });

//    etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//      @Override
//      public void onFocusChange(View v, boolean hasFocus) {
//        if(hasFocus){
//          codeInput=true;
//        }else {
//
//          if(codeInput){
//            if(!TextUtils.isEmpty(etCode.getText().toString())){
//              mPresenter.doValidateCode(etPhone.getText().toString(),etCode.getText().toString(),2);
//            }
//          }
//
//          codeInput=false;
//        }
//      }
//    });
  }


  private void doRegister(){

    String psd=etPsd.getText().toString();
    String psdAgain=etPsdAgain.getText().toString();

    if(TextUtils.isEmpty(psd)){
      ToastUtil.shortShow("请输入登录密码");
      return;
    }if(TextUtils.isEmpty(psdAgain)){
      ToastUtil.shortShow("请输入确认密码");
      return;
    }
    if(!psd.equals(psdAgain)){
      ToastUtil.shortShow("两次输入的密码不一样，请重新输入");
      etPsd.setText("");
      etPsdAgain.setText("");
      return;
    }

    smsBtn.onFinish();
    mPresenter.doRegister(etPhone.getText().toString(),etPsd.getText().toString(),
        etNickname.getText().toString(),etCode.getText().toString());
  }


  @OnClick({ R.id.btn_register, R.id.btn_back_login})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_register:
        doRegister();
        break;
      case R.id.btn_back_login:
        break;
    }
  }




  @Override
  public void doSendPhoneCodeSuccess() {
    smsBtn.startTime();
  }

  @Override
  public void doRegisterSuccess() {
    EventBus.getDefault().post(new WechatLoginSuccessEnevt());
    finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if(null!=smsBtn) {
      smsBtn.destory();
    }
  }
}
