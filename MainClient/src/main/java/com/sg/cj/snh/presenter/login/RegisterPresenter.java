package com.sg.cj.snh.presenter.login;


import android.text.TextUtils;

import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.contract.login.RegisterContract;
import com.sg.cj.snh.model.DataManager;
import com.sg.cj.snh.model.request.login.LoginRequest;
import com.sg.cj.snh.model.response.BaseResponse;
import com.sg.cj.snh.model.response.login.LoginResponse;
import com.sg.cj.snh.model.response.login.PhoneLoginResponse;
import com.sg.cj.snh.model.response.login.RegisterResponse;
import com.sg.cj.snh.model.response.login.SendPhoneCodeResponse;
import com.sg.cj.snh.uitls.CommonSubscriber;
import com.sg.cj.snh.uitls.MD5Util;
import com.sg.cj.snh.uitls.RxPresenter;
import com.sg.cj.snh.uitls.RxUtil;

import javax.inject.Inject;

/**
 * author : ${CHENJIE}
 * created at  2018/10/25 17:16
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {


  private DataManager mDataManager;

  @Inject
  public RegisterPresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }


  @Override
  public void doSendPhoneCode(String phone, int type) {

    if(TextUtils.isEmpty(phone)){
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
    addSubscribe(mDataManager.doValidateCode(phone, code, type)
    .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleResult())
        .subscribeWith(new CommonSubscriber<BaseResponse>(mView)
        {
          @Override
          public void onNext(BaseResponse baseResponse) {

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
        .subscribeWith(new CommonSubscriber<PhoneLoginResponse>(mView)
        {
          @Override
          public void onNext(PhoneLoginResponse baseResponse) {

          }
        })
    );
  }

  /**
   * 8．注册
   *
   * @param account  手机号码
   * @param pwd      登录密码
   * @param nickname 会员名
   * @param code     短信验证码
   */
  @Override
  public void doRegister(String account, String pwd, String nickname, String code) {

    addSubscribe(mDataManager.doRegister(account, MD5Util.string2MD5(pwd), nickname, code)
    .compose(RxUtil.rxSchedulerHelper())
        .compose(RxUtil.handleHttpResponse())
        .subscribeWith(new CommonSubscriber<RegisterResponse>(mView){
          @Override
          public void onNext(RegisterResponse registerResponse) {

            saveLoginResponse(registerResponse);
            mView.doRegisterSuccess();
          }
        })
    );
  }

  public void saveLoginResponse(LoginResponse response) {
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
  }

}
