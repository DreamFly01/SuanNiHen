package com.sg.cj.snh.model.http;


import com.google.gson.Gson;

import com.sg.cj.snh.bean.CityBean;
import com.sg.cj.snh.model.http.api.HttpApi;
import com.sg.cj.snh.model.request.login.LoginRequest;
import com.sg.cj.snh.model.request.login.WeChatGetAccessTokenRequest;
import com.sg.cj.snh.model.request.login.WeChatGetUserinfoRequest;
import com.sg.cj.snh.model.request.login.WxauthRequest;
import com.sg.cj.snh.model.request.shopcar.DeleteCartGoodsRequest;
import com.sg.cj.snh.model.request.shopcar.ShopCarRequest;
import com.sg.cj.snh.model.request.shopcar.UpdateGoodsNumRequest;
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

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 14:56
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class RetrofitHelper implements HttpHelper {

  private HttpApi mHttpApi;

  @Inject
  public RetrofitHelper(HttpApi mHttpApi) {
    this.mHttpApi = mHttpApi;

  }


  //登录注册相关开始


  /**
   * 1．Api登录
   */
  @Override
  public Flowable<BaseResponse> doApiLogin(String uid) {
    return mHttpApi.doApiLogin(uid);
  }

  /**
   * 获取短信验证码
   *
   * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
   */
  @Override
  public Flowable<SendPhoneCodeResponse> doSendPhoneCode(String phone, int type) {
    return mHttpApi.doSendPhoneCode(phone, type);
  }

  /**
   * 3．验证短信验证码
   *
   * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
   */
  @Override
  public Flowable<BaseResponse> doValidateCode(String phone, String code, int type) {
    return mHttpApi.doValidateCode(phone, code, type);
  }

  /**
   * 4．短信验证登录
   *
   * @param phone 手机号码
   * @param code  短信验证码
   */
  @Override
  public Flowable<HttpResponse<PhoneLoginResponse>> doPhoneLogin(String phone, String code) {
    return mHttpApi.doPhoneLogin(phone, code);
  }

  /**
   * 5．账号密码登录
   */
  @Override
  public Flowable<HttpResponse<LoginResponse>> doLogin(String acount, String pwd) {
    //return mHttpApi.doLogin(acount, pwd);

    LoginRequest registerRequest=new LoginRequest(acount, pwd);
   // return mHttpApi.doRegister3(registerRequest.getRequestBody());

    return mHttpApi.doLogin(registerRequest.getRequestBody());
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
    return mHttpApi.doRegister(account, pwd, nickname, code);

    //RegisterRequest registerRequest=new RegisterRequest(account, pwd, nickname, code);
    //return mHttpApi.doRegister3(registerRequest.getRequestBody());
  }

  /**
   * 微信登录获取access_token
   * @param
   * @return
   */
  @Override
  public Flowable<WeChatGetAccessTokenResponse> doWeChatGetAccessToken(WeChatGetAccessTokenRequest request) {
    return mHttpApi.doWeChatGetAccessToken(request.getMapParams());
  }

  /**
   * 获取微信信息
   */
  @Override
  public Flowable<WeChatGetUserinfoResponse> doWeChatGetUserinfo(WeChatGetUserinfoRequest request) {
    return mHttpApi.doWeChatGetUserinfo(request.getMapParams());
  }

  /**
   * 6．微信授权登录
   */
  @Override
  public Flowable<HttpResponse<LoginResponse>> dowxauth(String openid) {
    RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),new WxauthRequest(openid).getJsonParams());

    return mHttpApi.dowxauth(body);
  }

  /**
   * 7．微信绑定
   */
  @Override
  public Flowable<HttpResponse<LoginResponse>> dowxbind(String openid, String account, String pwd) {
    return mHttpApi.dowxbind(openid, account, pwd);
  }

  @Override
  public Flowable<BaseResponse> doForgetloginpwd(String Account, String pwd) {
    return mHttpApi.doForgetloginpwd(Account, pwd);
  }


  //登录注册相关结束




  //我的相关开始

  /**
   * 9．个人中心提示
   */
  @Override
  public Flowable<PersonInfoResponse> doPersoninfo(String uid) {
    return mHttpApi.doPersoninfo(uid);
  }

  /**
   * 10．个人中心推荐商品
   */
  @Override
  public Flowable<GetDataResponse> doGetData(int index, int size, String name) {
    return mHttpApi.doGetData(index, size, name);
  }

  //我的相关结束


  //发现相关开始

  /**
   * 23．发现接口
   */
  @Override
  public Flowable<FindGoodsResponse> doFindGoods(int index, int size) {
    return mHttpApi.doFindGoods(index,size);
  }


  //发现相关结束


  //分类

  /**
   * 24．商品分类
   */
  @Override
  public Flowable<CategoryResponse> doGetclassify() {
    return mHttpApi.doGetclassify();
  }


  //分类
  //首页开始

  /**
   * 25．当地页广告
   */
  @Override
  public Flowable<LocalAdResponse> doGetLocalAd(String area) {
    return mHttpApi.doGetLocalAd(area);
  }

  /**
   * 26．本地推荐商品
   */
  @Override
  public Flowable<LocalgoodsdataResponse> doLocalgoodsdata(int index, int size, String area, String name) {
    return mHttpApi.doLocalgoodsdata(index, size, area, name);
  }

  /**
   * 26．全国商品
   */
  @Override
  public Flowable<LocalgoodsdataResponse> doGoodsdata(int index, int size, String name) {
    return  mHttpApi.doGoodsdata(index, size, name);
  }

  /**
   * 获取区县
   */
  @Override
  public Flowable<CityBean> doGetareas() {
    return mHttpApi.doGetareas();
  }

  /**
   * 18．购物车
   */
  @Override
  public Flowable<GetCartGoodsListResponse> doGetCartGoodsList(int uid) {


    return mHttpApi.doGetCartGoodsList(uid);
  }

  /**
   * 21．购物车中商品增减操作
   * @param Id
   * @param num
   * @return
   */
  @Override
  public Flowable<BaseResponse> doUpdateGoodsNum(int Id, int num) {

    String obj=new UpdateGoodsNumRequest(Id,num).getJsonParams();

    RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);


    return mHttpApi.doUpdateGoodsNum(body);
  }

  /**
   * 22．删除购物车商品
   */
  @Override
  public Flowable<BaseResponse> doDeleteCartGoods(DeleteCartGoodsRequest request) {
    RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),request.getJsonParams());


    return mHttpApi.doDeleteCartGoods(body);
  }


  //首页结束
}
