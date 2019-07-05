package com.sg.cj.snh.model;


import com.sg.cj.snh.bean.CityBean;
import com.sg.cj.snh.model.http.HttpHelper;
import com.sg.cj.snh.model.prefs.PreferencesHelper;
import com.sg.cj.snh.model.request.login.WeChatGetAccessTokenRequest;
import com.sg.cj.snh.model.request.login.WeChatGetUserinfoRequest;
import com.sg.cj.snh.model.request.shopcar.DeleteCartGoodsRequest;
import com.sg.cj.snh.model.response.BaseResponse;
import com.sg.cj.snh.model.response.HttpResponse;
import com.sg.cj.snh.model.response.category.CategoryResponse;
import com.sg.cj.snh.model.response.findgoods.FindGoodsResponse;
import com.sg.cj.snh.model.response.login.LoginResponse;
import com.sg.cj.snh.model.response.login.PhoneLoginResponse;
import com.sg.cj.snh.model.response.login.RegisterResponse;
import com.sg.cj.snh.model.response.login.SendPhoneCodeResponse;
import com.sg.cj.snh.model.response.login.WeChatGetAccessTokenResponse;
import com.sg.cj.snh.model.response.login.WeChatGetUserinfoResponse;
import com.sg.cj.snh.model.response.main.LocalAdResponse;
import com.sg.cj.snh.model.response.main.LocalgoodsdataResponse;
import com.sg.cj.snh.model.response.self.GetDataResponse;
import com.sg.cj.snh.model.response.self.PersonInfoResponse;
import com.sg.cj.snh.model.response.shopcar.GetCartGoodsListResponse;

import java.util.Set;

import io.reactivex.Flowable;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 14:42
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class DataManager implements HttpHelper, PreferencesHelper {

  HttpHelper mHttpHelper;

  PreferencesHelper mPreferencesHelper;

  public DataManager() {
  }

  public DataManager(HttpHelper httpHelper, PreferencesHelper preferencesHelper) {
    mHttpHelper = httpHelper;

    mPreferencesHelper = preferencesHelper;
  }


  //http

  /**
   * 1．Api登录
   */
  @Override
  public Flowable<BaseResponse> doApiLogin(String uid) {
    return mHttpHelper.doApiLogin(uid);
  }


  //登录相关开始

  /**
   * 获取短信验证码
   *
   * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
   */
  @Override
  public Flowable<SendPhoneCodeResponse> doSendPhoneCode(String phone, int type) {
    return mHttpHelper.doSendPhoneCode(phone, type);
  }

  /**
   * 3．验证短信验证码
   *
   * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
   */
  @Override
  public Flowable<BaseResponse> doValidateCode(String phone, String code, int type) {
    return mHttpHelper.doValidateCode(phone, code, type);
  }

  /**
   * 4．短信验证登录
   *
   * @param phone 手机号码
   * @param code  短信验证码
   */
  @Override
  public Flowable<HttpResponse<PhoneLoginResponse>> doPhoneLogin(String phone, String code) {
    return mHttpHelper.doPhoneLogin(phone, code);
  }

  /**
   * 5．账号密码登录
   */
  @Override
  public Flowable<HttpResponse<LoginResponse>> doLogin(String acount, String pwd) {
    return mHttpHelper.doLogin(acount, pwd);
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
  public Flowable<HttpResponse<RegisterResponse>> doRegister(String account, String pwd, String nickname, String code) {
    return mHttpHelper.doRegister(account, pwd, nickname, code);
  }

  /**
   * 微信登录获取access_token
   */
  @Override
  public Flowable<WeChatGetAccessTokenResponse> doWeChatGetAccessToken(WeChatGetAccessTokenRequest request) {
    return mHttpHelper.doWeChatGetAccessToken(request);
  }


  /**
   * 获取微信信息
   */
  @Override
  public Flowable<WeChatGetUserinfoResponse> doWeChatGetUserinfo(WeChatGetUserinfoRequest request) {
    return mHttpHelper.doWeChatGetUserinfo(request);
  }


  /**
   * 6．微信授权登录
   */
  @Override
  public Flowable<HttpResponse<LoginResponse>> dowxauth(String openid) {
    return mHttpHelper.dowxauth(openid);
  }

  /**
   * 7．微信绑定
   */
  @Override
  public Flowable<HttpResponse<LoginResponse>> dowxbind(String openid, String account, String pwd) {
    return mHttpHelper.dowxbind(openid, account, pwd);
  }

  @Override
  public Flowable<BaseResponse> doForgetloginpwd(String Account, String pwd) {
    return mHttpHelper.doForgetloginpwd(Account, pwd);
  }

  //登录相关结束


  //我的相关开始

  /**
   * 9．个人中心提示
   */
  @Override
  public Flowable<PersonInfoResponse> doPersoninfo(String uid) {
    return mHttpHelper.doPersoninfo(uid);
  }

  /**
   * 10．个人中心推荐商品
   */
  @Override
  public Flowable<GetDataResponse> doGetData(int index, int size, String name) {
    return mHttpHelper.doGetData(index, size, name);
  }

  //我的相关结束


  //发现相关开始

  /**
   * 23．发现接口
   */
  @Override
  public Flowable<FindGoodsResponse> doFindGoods(int index, int size) {
    return mHttpHelper.doFindGoods(index, size);
  }


  //发现相关结束

//分类

  /**
   * 24．商品分类
   */
  @Override
  public Flowable<CategoryResponse> doGetclassify() {
    return mHttpHelper.doGetclassify();
  }


  //分类


  //首页开始

  /**
   * 25．当地页广告
   */
  @Override
  public Flowable<LocalAdResponse> doGetLocalAd(String area) {
    return mHttpHelper.doGetLocalAd(area);
  }

  /**
   * 25．当地商品
   *
   */
  @Override
  public Flowable<LocalgoodsdataResponse> doLocalgoodsdata(int index, int size, String area, String name) {
    return mHttpHelper.doLocalgoodsdata(index, size, area, name);
  }

  /**
   * 26．全国商品
   */
  @Override
  public Flowable<LocalgoodsdataResponse> doGoodsdata(int index, int size, String name) {
    return  mHttpHelper.doGoodsdata(index, size, name);
  }


  /**
   * 获取区县
   */
  @Override
  public Flowable<CityBean> doGetareas() {
    return mHttpHelper.doGetareas();
  }


  /**
   * 18．购物车
   */
  @Override
  public Flowable<GetCartGoodsListResponse> doGetCartGoodsList(int uid) {
    return mHttpHelper.doGetCartGoodsList(uid);
  }


  /**
   * 21．购物车中商品增减操作
   */
  @Override
  public Flowable<BaseResponse> doUpdateGoodsNum(int Id, int num) {
    return mHttpHelper.doUpdateGoodsNum(Id, num);
  }


  /**
   * 22．删除购物车商品
   */
  @Override
  public Flowable<BaseResponse> doDeleteCartGoods(DeleteCartGoodsRequest request) {
    return mHttpHelper.doDeleteCartGoods(request);
  }

  //首页结束


  // Preferences


  @Override
  public int getId() {
    return mPreferencesHelper.getId();
  }

  @Override
  public void setId(int Id) {
    mPreferencesHelper.setId(Id);
  }

  @Override
  public int getGradeId() {
    return mPreferencesHelper.getGradeId();
  }

  @Override
  public void setGradeId(int GradeId) {
    mPreferencesHelper.setGradeId(GradeId);
  }

  @Override
  public String getWxNickName() {
    return mPreferencesHelper.getWxNickName();
  }

  @Override
  public void setWxNickName(String WxNickName) {
    mPreferencesHelper.setWxNickName(WxNickName);
  }

  @Override
  public String getWxHeadImg() {
    return mPreferencesHelper.getWxHeadImg();
  }

  @Override
  public void setWxHeadImg(String WxHeadImg) {
    mPreferencesHelper.setWxHeadImg(WxHeadImg);
  }

  @Override
  public String getUnionID() {
    return mPreferencesHelper.getUnionID();
  }

  @Override
  public void setUnionID(String UnionID) {
    mPreferencesHelper.setUnionID(UnionID);
  }

  @Override
  public String getPayment() {
    return mPreferencesHelper.getPayment();
  }

  @Override
  public void setPayment(String Payment) {
    mPreferencesHelper.setPayment(Payment);
  }

  @Override
  public String getTel() {
    return mPreferencesHelper.getTel();
  }

  @Override
  public void setTel(String Tel) {
    mPreferencesHelper.setTel(Tel);
  }

  @Override
  public float getBalanceOne() {
    return mPreferencesHelper.getBalanceOne();
  }

  @Override
  public void setBalanceOne(float BalanceOne) {
    mPreferencesHelper.setBalanceOne(BalanceOne);
  }

  @Override
  public float getIntegral() {
    return mPreferencesHelper.getIntegral();
  }

  @Override
  public void setIntegral(float Integral) {
    mPreferencesHelper.setIntegral(Integral);
  }

  @Override
  public String getWxOpenId() {
    return mPreferencesHelper.getWxOpenId();
  }

  @Override
  public void setWxOpenId(String WxOpenId) {
    mPreferencesHelper.setWxOpenId(WxOpenId);
  }

  /**
   * 获取是否首次登录
   */
  @Override
  public boolean getLaunchFirst() {
    return mPreferencesHelper.getLaunchFirst();
  }

  /**
   * 设置是否首次登录
   */
  @Override
  public void setLaunchFirst(boolean isFirst) {
    mPreferencesHelper.setLaunchFirst(isFirst);
  }


  /**
   * 获取cookie
   */
  @Override
  public Set<String> getCookie() {
    return mPreferencesHelper.getCookie();
  }

  /**
   * 保存cookie
   */
  @Override
  public void setCookie(Set<String> cookies) {
    mPreferencesHelper.setCookie(cookies);
  }


  /**
   * 获取cookie
   */
  @Override
  public String getCookieString() {
    return mPreferencesHelper.getCookieString();
  }

  /**
   * 保存cookie
   */
  @Override
  public void setCookieString(String cookies) {
    mPreferencesHelper.setCookieString(cookies);
  }


  @Override
  public String getOpenid() {
    return mPreferencesHelper.getOpenid();
  }

  @Override
  public void setOpenid(String openid) {
    mPreferencesHelper.setOpenid(openid);
  }

  /**
   * 退出登陆
   */
  @Override
  public void cleanData() {
    mPreferencesHelper.cleanData();
    mPreferencesHelper.setLaunchFirst(false);
  }
}