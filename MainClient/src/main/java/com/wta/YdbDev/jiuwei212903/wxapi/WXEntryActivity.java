package com.wta.YdbDev.jiuwei212903.wxapi;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.fdl.activity.main.MainActivity;
import com.fdl.utils.FinishActivityManager;
import com.fdl.utils.JumpUtils;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.common.base.utils.ToastUtil;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.R;
import com.sg.cj.snh.base.BaseActivity;
import com.sg.cj.snh.bean.WechatLoginSuccessEnevt;
import com.sg.cj.snh.contract.login.LoginContract;
import com.sg.cj.snh.presenter.login.LoginPresenter;
import com.sg.cj.snh.ui.activity.login.LoginActivity;
import com.sg.cj.snh.ui.activity.login.WechatBindActivity;
import com.sg.cj.snh.uitls.RxBus;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

/**
 * author : ${CHENJIE}
 * created at  2018/12/4 20:15
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WXEntryActivity extends BaseActivity<LoginPresenter> implements IWXAPIEventHandler, LoginContract.View {
    private static final String TAG = "WXEntryActivity";
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData() {
        //PartyApp.mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //这句没有写,是不能执行回调的方法的
        PartyApp.mWxApi.handleIntent(getIntent(), this);
    }


    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp baseResp) {
        SgLog.i(TAG, "onResp:------>");
        SgLog.i(TAG, "error_code:---->" + baseResp.errCode);
        int type = baseResp.getType(); //类型：分享还是登录
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                ToastUtil.shortShow("拒绝授权微信登录");
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录";
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享";
                }
                ToastUtil.shortShow(message);

                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    //用户换取access_token的code，仅在ErrCode为0时有效
                    String code = ((SendAuth.Resp) baseResp).code;
                    SgLog.i(TAG, "code:------>" + code);
                    //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
                    //WXLoginUtils().getWXLoginResult(code, this);
                    mPresenter.doWeChatGetAccessToken(code);
                } else if (type == RETURN_MSG_TYPE_SHARE) {
//          ToastUtils.showToast(mContext, "微信分享成功");
//          Toast.makeText(mContext,"微信分享成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);

        if (msg.equals("获取用户信息为空")) {
            Bundle bundle = new Bundle();
            bundle.putInt("typeCode", 1);
            JumpUtils.dataJump(WXEntryActivity.this, com.fdl.activity.main.WechatBindActivity.class, bundle, true);
            return;
        }
        if (msg.equals("用户存在微信账户,需要绑定或注册现有账号")) {
            Bundle bundle = new Bundle();
            bundle.putInt("typeCode", 2);
            JumpUtils.dataJump(WXEntryActivity.this, com.fdl.activity.main.WechatBindActivity.class, bundle, true);
            return;
        }

    }

    @Override
    public void wechatFinish() {
        startAct(com.fdl.activity.main.WechatBindActivity.class);
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
    public void loginSuccess() {
//    EventBus.getDefault().post(new WechatLoginSuccessEnevt());
//        Bundle bundle = new Bundle();
//        bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX, 0);
//        JumpUtils.simpJump(this, MainActivity.class, true);
        FinishActivityManager.getManager().finishActivity(LoginActivity.class);
        this.finish();
    }
}

