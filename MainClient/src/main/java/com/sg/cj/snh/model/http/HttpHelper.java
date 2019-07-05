package com.sg.cj.snh.model.http;


import com.sg.cj.snh.bean.CityBean;
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

import java.util.Map;

import io.reactivex.Flowable;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 14:52
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public interface HttpHelper {


  //登录注册相关开始


  /**
   *
   *1．Api登录
   *
   */

  Flowable<BaseResponse> doApiLogin(String uid);




  /**
   * 获取短信验证码
   *
   * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
   */
  Flowable<SendPhoneCodeResponse> doSendPhoneCode(String phone, int type);


  /**
   * 3．验证短信验证码
   *
   * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
   */


  Flowable<BaseResponse> doValidateCode(String phone, String code, int type);


  /**
   * 4．短信验证登录
   *
   * @param phone 手机号码
   * @param code  短信验证码
   */

  Flowable<HttpResponse<PhoneLoginResponse>> doPhoneLogin(String phone, String code);

  /**
   * 5．账号密码登录
   */


  Flowable<HttpResponse<LoginResponse>> doLogin(String acount, String pwd);


  /**
   * 8．注册
   *
   * @param account  手机号码
   * @param pwd      登录密码
   * @param nickname 会员名
   * @param code     短信验证码
   */


  Flowable<HttpResponse<RegisterResponse>> doRegister(String account, String pwd, String nickname, String code);


  /**
   * 微信登录获取access_token
   * @param
   * @return
   */

  Flowable<WeChatGetAccessTokenResponse> doWeChatGetAccessToken(WeChatGetAccessTokenRequest request);


  /**
   * 获取微信信息
   * @param
   *
   */


  Flowable<WeChatGetUserinfoResponse> doWeChatGetUserinfo(WeChatGetUserinfoRequest request);



  /**
   * 6．微信授权登录
   * @param openid
   * @return
   */

  Flowable<HttpResponse<LoginResponse>> dowxauth( String openid);


  /**
   * 7．微信绑定
   * @param openid
   * @param account
   * @param pwd
   * @return
   */

  Flowable<HttpResponse<LoginResponse>> dowxbind(String openid,String account, String pwd);


  Flowable<BaseResponse> doForgetloginpwd(String Account,String pwd);


  //登录注册相关结束


  //我的相关开始

  /**
   * 9．个人中心提示
   */


  Flowable<PersonInfoResponse> doPersoninfo(String uid);


  /**
   * 10．个人中心推荐商品
   */

  Flowable<GetDataResponse> doGetData(int index, int size, String name);


  //我的相关结束

  //发现相关开始

  /**
   * 23．发现接口
   * @param index
   * @param size
   * @return
   */
  Flowable<FindGoodsResponse> doFindGoods(int index, int size);
  //发现相关结束



  //分类

  /**
   * 24．商品分类
   * @return
   */

  Flowable<CategoryResponse> doGetclassify();





  //分类



  //首页开始

  /**
   * 25．当地页广告
   * @param area
   * @return
   */

  Flowable<LocalAdResponse> doGetLocalAd(String area);


  /**
   * 26．本地推荐商品
   * @param area
   * @return
   */

  Flowable<LocalgoodsdataResponse> doLocalgoodsdata(int index,int size,String area, String name);


  /**
   * 26．全国商品
   */
  Flowable<LocalgoodsdataResponse> doGoodsdata(int index, int size, String name);



  /**
   * 获取区县
   *
   * @return
   */

  Flowable<CityBean> doGetareas();




  /**
   * 18．购物车
   * @param uid
   * @return
   */

  Flowable<GetCartGoodsListResponse> doGetCartGoodsList(int uid);


  /**
   * 21．购物车中商品增减操作
   * @param Id
   * @param num
   * @return
   */

  Flowable<BaseResponse> doUpdateGoodsNum(int Id, int num);



  /**
   * 22．删除购物车商品
   * @param requestBody
   * @return
   */
  Flowable<BaseResponse> doDeleteCartGoods(DeleteCartGoodsRequest request);



  //首页结束


}
