package com.sg.cj.snh.ui.activity.login;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.bean.WechatLoginSuccessEnevt;
import com.sg.cj.snh.contract.login.LoginContract;
import com.sg.cj.snh.presenter.login.LoginPresenter;
import com.sg.cj.snh.uitls.Event;
import com.sg.cj.snh.uitls.RxBus;
import com.sg.cj.snh.view.SmsBtnView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : ${CHENJIE}
 * created at  2018/12/11 16:04
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WechatBindActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


  @BindView(R.id.et_username)
  EditText etUsername;
  @BindView(R.id.et_psd)
  EditText etPsd;
  @BindView(R.id.smsBtn)
  SmsBtnView smsBtn;
  @BindView(R.id.btn_login)
  Button btnLogin;

  @Override
  protected int getLayout() {
    return R.layout.activity_wechat_bind;
  }

  @Override
  protected void initInject() {
    getActivityComponent().inject(this);
  }

  @Override
  protected void initEventAndData() {
    btnLogin.setText("绑定");
    etPsd.setHint("请输入密码");
    etUsername.setHint("请输入账号");
    smsBtn.setVisibility(View.GONE);

  }

  @Override
  public void loginSuccess() {
    finish();
  }

  @Override
  public void doSendPhoneCodeSuccess() {

  }
  @Override
  public void doValidateCode() {

  }

  @Override
  public void doForgetloginpwd() {

  }
  @Override
  public void wechatFinish() {
    EventBus.getDefault().post(new WechatLoginSuccessEnevt());

    finish();
  }

  private void next() {
    mPresenter.dowxbind("", etUsername.getText().toString(), etPsd.getText().toString());
  }

  @OnClick({R.id.btn_login})
  public void onViewClicked(View view) {
    switch (view.getId()) {

      case R.id.btn_login:
        next();
        break;
    }
  }
}
