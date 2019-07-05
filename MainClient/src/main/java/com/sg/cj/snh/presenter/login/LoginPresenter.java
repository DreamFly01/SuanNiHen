package com.sg.cj.snh.presenter.login;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.fdl.activity.main.MainActivity;
import com.fdl.jpush.TagAliasOperatorHelper;
import com.fdl.utils.Contans;
import com.fdl.utils.JumpUtils;
import com.fdl.utils.SPUtils;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.contract.login.LoginContract;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.request.login.WeChatGetAccessTokenRequest;
import com.sg.cj.snh.model.request.login.WeChatGetUserinfoRequest;
import com.sg.cj.snh.model.response.BaseResponse;
import com.sg.cj.snh.model.response.login.LoginResponse;
import com.sg.cj.snh.model.response.login.PhoneLoginResponse;
import com.sg.cj.snh.model.response.login.SendPhoneCodeResponse;
import com.sg.cj.snh.model.response.login.WeChatGetAccessTokenResponse;
import com.sg.cj.snh.model.response.login.WeChatGetUserinfoResponse;
import com.sg.cj.snh.ui.activity.login.LoginActivity;
import com.sg.cj.snh.uitls.CommonSubscriber;
import com.sg.cj.snh.uitls.MD5Util;
import com.sg.cj.snh.uitls.RxPresenter;
import com.sg.cj.snh.uitls.RxUtil;

import javax.inject.Inject;

import static com.fdl.jpush.TagAliasOperatorHelper.ACTION_SET;
import static com.fdl.jpush.TagAliasOperatorHelper.sequence;

/**
 * author : ${CHENJIE}
 * created at  2018/10/25 17:16
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {


    private DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void doLogin(String acount, String pwd) {
        addSubscribe(mDataManager.doLogin(acount, MD5Util.string2MD5(pwd))
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleHttpResponse())
                .subscribeWith(new CommonSubscriber<LoginResponse>(mView, false) {
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        saveLoginResponse(loginResponse);
                        mView.loginSuccess();
                    }
                })
        );
    }

    @Override
    public void doSendPhoneCode(String phone, int type) {

        if (TextUtils.isEmpty(phone)) {
            mView.showErrorMsg("请输入正确的手机号码");
            return;
        }

        addSubscribe(mDataManager.doSendPhoneCode(phone, type)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.<SendPhoneCodeResponse>handleResult())
                .subscribeWith(new CommonSubscriber<SendPhoneCodeResponse>(mView) {
                    @Override
                    public void onNext(SendPhoneCodeResponse response) {
                        mView.doSendPhoneCodeSuccess();

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }

    /**
     * 3．验证短信验证码
     *
     * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
     */
    @Override
    public void doValidateCode(String phone, String code, int type) {

        if (TextUtils.isEmpty(phone)) {
            mView.showErrorMsg("请输入正确的手机号码");
            return;
        }


        if (TextUtils.isEmpty(code)) {
            mView.showErrorMsg("请输入验证码");
            return;
        }


        addSubscribe(mDataManager.doValidateCode(phone, code, type)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult())
                .subscribeWith(new CommonSubscriber<BaseResponse>(mView) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {

                        if (baseResponse.getCode().equals(Constants.SUCCESS_CODE)) {
                            mView.doValidateCode();
                        } else {
                            mView.showErrorMsg(baseResponse.getMsg());
                        }
                    }
                })
        );
    }


    /**
     * 4．短信验证登录
     *
     * @param phone 手机号码
     * @param code  短信验证码
     */
    @Override
    public void doPhoneLogin(String phone, String code) {
        addSubscribe(mDataManager.doPhoneLogin(phone, code)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleHttpResponse())
                .subscribeWith(new CommonSubscriber<PhoneLoginResponse>(mView) {
                    @Override
                    public void onNext(PhoneLoginResponse baseResponse) {
                        saveLoginResponse(baseResponse);
                        mView.loginSuccess();
                    }
                })
        );
    }

    private void saveLoginResponse(LoginResponse response) {
        mDataManager.setId(response.Id);
        mDataManager.setGradeId(response.GradeId);
        mDataManager.setBalanceOne(response.BalanceOne);
        mDataManager.setIntegral(response.Integral);
        mDataManager.setPayment(response.Payment);
        mDataManager.setTel(response.Tel);
        mDataManager.setUnionID(response.WxOpenId);
        mDataManager.setWxHeadImg(response.WxHeadImg);
        mDataManager.setWxNickName(response.WxNickName);
        mDataManager.setWxOpenId(response.WxOpenId);
        mDataManager.setUnionID(response.UnionID);
        boolean isAliasAction = true;
        int action = ACTION_SET;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        if (isAliasAction) {
            tagAliasBean.alias = response.Id + "";
        } else {
//            tagAliasBean.tags = 1;
        }
        tagAliasBean.isAliasAction = isAliasAction;
        TagAliasOperatorHelper.getInstance().handleAction(PartyApp.getAppComponent().getContext(), sequence, tagAliasBean);
        if(null!=response.nimResult){
        SPUtils.getInstance(PartyApp.getInstance()).saveData(Contans.IM_TOKEN,response.nimResult.Token);
        SPUtils.getInstance(PartyApp.getInstance()).saveData(Contans.IM_ACCOUNT,response.nimResult.Accid);
        }

    }

    @Override
    public void doWeChatGetAccessToken(String code) {

        addSubscribe(mDataManager.doWeChatGetAccessToken(new WeChatGetAccessTokenRequest(code))
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleWeChatResult())
                .subscribeWith(new CommonSubscriber<WeChatGetAccessTokenResponse>(mView) {
                    @Override
                    public void onNext(WeChatGetAccessTokenResponse weChatGetAccessTokenResponse) {
                        saveWeChatAccessToken(weChatGetAccessTokenResponse);
                        dowxauth(weChatGetAccessTokenResponse.unionid);
//                        doWeChatGetUserinfo(weChatGetAccessTokenResponse.access_token,weChatGetAccessTokenResponse.unionid);
                    }
                })
        );

    }


    private void saveWeChatAccessToken(WeChatGetAccessTokenResponse weChatGetAccessTokenResponse) {
        mDataManager.setOpenid(weChatGetAccessTokenResponse.openid);
        mDataManager.setUnionID(weChatGetAccessTokenResponse.unionid);
    }


    @Override
    public void doWeChatGetUserinfo(String access_token, String openid) {
        addSubscribe(mDataManager.doWeChatGetUserinfo(new WeChatGetUserinfoRequest(access_token, openid))
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleWeChatResult())
                .subscribeWith(new CommonSubscriber<WeChatGetUserinfoResponse>(mView) {
                    @Override
                    public void onNext(WeChatGetUserinfoResponse weChatGetUserinfoResponse) {

                    }
                })
        );
    }


    @Override
    public void dowxauth(String openid) {
        addSubscribe(mDataManager.dowxauth(openid)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleHttpResponse())
                .subscribeWith(new CommonSubscriber<LoginResponse>(mView) {
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        saveLoginResponse(loginResponse);
//                        imLoging(loginResponse.nimResult.Token,loginResponse.nimResult.Accid,PartyApp.getAppComponent().getContext());
                        mView.loginSuccess();
                    }
                })
        );
    }

    @Override
    public void dowxbind(String openid, String account, String pwd) {

        addSubscribe(mDataManager.dowxbind(mDataManager.getUnionID(), account, MD5Util.string2MD5(pwd))
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleHttpResponse())
                .subscribeWith(new CommonSubscriber<LoginResponse>(mView) {
                    @Override
                    public void onNext(LoginResponse response) {
                        saveLoginResponse(response);
                        mView.loginSuccess();
                    }
                })
        );
    }


    @Override
    public void doForgetloginpwd(String Account, String pwd) {
        addSubscribe(mDataManager.doForgetloginpwd(Account, MD5Util.string2MD5(pwd))
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult())
                .subscribeWith(new CommonSubscriber<BaseResponse>(mView) {
                    @Override
                    public void onNext(BaseResponse response) {

                        mView.doForgetloginpwd();
                    }
                })
        );
    }
}
