package com.sg.cj.snh.ui.activity.login;


import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fdl.activity.main.MainActivity;
import com.fdl.activity.main.RegisterActivity;
import com.fdl.activity.merchantEntry.LogingActivity;
import com.fdl.utils.Contans;
import com.fdl.utils.FinishActivityManager;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.bean.WechatLoginSuccessEnevt;
import com.sg.cj.snh.contract.login.LoginContract;
import com.sg.cj.snh.presenter.login.LoginPresenter;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * author : ${CHENJIE}
 * created at  2018/10/24 15:58
 * e_mail : chenjie_goodboy@163.com
 * describle : 登陆主页
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    private int SMS_LOGIN = 0, REGISTER = 2;

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_forget_psd)
    TextView txtForgetPsd;
    @BindView(R.id.txt_sms_login)
    TextView txtSmsLogin;
    @BindView(R.id.txt_regiest)
    TextView txtRegiest;
    @BindView(R.id.btn_wechat_login)
    Button btnQh;
    private ImmersionBar immersionBar;
    private Bundle bundle;
    private String type = "";
    private int index = -1;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        EventBus.getDefault().register(this);
        SgLog.d("登录");

//    PartyApp.getAppComponent().getDataManager().cleanData();
        // mPresenter.loadToken();
        // mPresenter.loadCode();
        immersionBar = ImmersionBar.with(this);
        immersionBar.init();
        bundle = getIntent().getExtras();
        if (null != bundle) {
            type = bundle.getString("type");
            index = bundle.getInt(MainActivity.SHOW_FRAGMENT_INDEX,-1);
        }
        String tel = PartyApp.getAppComponent().getDataManager().getTel();
//        if(!StrUtils.isEmpty(PartyApp.getAppComponent().getDataManager().getTel())){
        etUsername.setText(PartyApp.getAppComponent().getDataManager().getTel());
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (immersionBar != null) {
            immersionBar.destroy();
        }
        EventBus.getDefault().unregister(this);
    }

    //18974297333
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WechatLoginSuccess(WechatLoginSuccessEnevt messageEvent) {
        finish();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    private void bus() {
    }


    @Override
    public void loginSuccess() {
        imLoging(SPUtils.getInstance(this).getString(Contans.IM_ACCOUNT), SPUtils.getInstance(this).getString(Contans.IM_TOKEN));
        Bundle bundle = new Bundle();
        if (index != -1) {
            bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX, index);
            JumpUtils.dataJump(LoginActivity.this, MainActivity.class, bundle, true);
        } else {
            this.finish();
        }
//    Intent intent =new Intent(this,MainActivity.class);
//    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//    startActivity(intent);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = new Bundle();
            bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX, 0);
            JumpUtils.dataJump(LoginActivity.this, MainActivity.class, bundle, true);

        }
    }

    @OnClick({R.id.btn_login, R.id.txt_forget_psd, R.id.txt_regiest, R.id.btn_wechat_login, R.id.txt_sms_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.doLogin(etUsername.getText().toString(), etPsd.getText().toString());
                break;
            case R.id.txt_forget_psd:
                startAct(FindPsdActivity.class);
                break;
            case R.id.txt_sms_login:
                startAct(SmsLoginActivity.class);
                break;
            case R.id.txt_regiest:
                startAct(RegisterActivity.class);
//        JumpUtils.simpJump(this,RegisterActivity.class,false);
                break;
            case R.id.btn_wechat_login:
                wxLogin();
                break;

        }
    }


    //  //先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。
//     if (!WXUtils.isWXAppInstalled()) {
//    ToastUtils.showToast("您还未安装微信客户端");
//    return;
//  }
    //微信登录
    public void wxLogin() {
        FinishActivityManager.getManager().addActivity(this);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        //像微信发送请求
        PartyApp.mWxApi.sendReq(req);
    }

    @Override
    public void wechatFinish() {

    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        bundle = new Bundle();
        if (index != -1) {
            bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX, 0);
            JumpUtils.dataJump(this, com.fdl.activity.main.MainActivity.class, bundle, true);
        }
    }

    private void imLoging(String imAccount, String imPsw) {
        LoginInfo info = new LoginInfo(imAccount, imPsw); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        SPUtils.getInstance(LoginActivity.this).saveData(Contans.IM_TOKEN, param.getToken());
                        SPUtils.getInstance(LoginActivity.this).saveData(Contans.IM_ACCOUNT, param.getAccount());
                        NimUIKitImpl.setAccount(param.getAccount());

                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {
//                Log.d(HttpLogger.LOGKYE,"exception");
                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
}
