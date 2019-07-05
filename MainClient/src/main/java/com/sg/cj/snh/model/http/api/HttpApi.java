package com.sg.cj.snh.model.http.api;


import com.sg.cj.snh.bean.CityBean;
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
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * author : ${CHENJIE}
 * created at  2018/11/1 11:31
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public interface HttpApi {



  //登录相关开始


  /**
   * 1．Api登录
   */
  @FormUrlEncoded
  @POST("http://shop.hnyunshang.com/wx_user/login/apilogin")
  Flowable<BaseResponse> doApiLogin(@Field("uid") String uid);


  /**
   * 2．获取短信验证码
   *
   * 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
   */
  @FormUrlEncoded
  @POST("user/sendphonecode")
  Flowable<SendPhoneCodeResponse> doSendPhoneCode(@Field("phone") String phone, @Field("type") int type);


  /**
   * 3．验证短信验证码
   *
   * @param type 验证码类型1 短信登录，2 注册 ，3修改支付密码，4修改手机号码
   */

  @FormUrlEncoded
  @POST("user/validatecode")
  Flowable<BaseResponse> doValidateCode(@Field("phone") String phone, @Field("code") String code, @Field("type") int type);


  /**
   * 4．短信验证登录
   *
   * @param phone 手机号码
   * @param code  短信验证码
   */

  @FormUrlEncoded
  @POST("user/phonelogin")
  Flowable<HttpResponse<PhoneLoginResponse>> doPhoneLogin(@Field("phone") String phone, @Field("code") String code);


  /**
   * 5．账号密码登录
   */

  @FormUrlEncoded
  @POST("user/login")
  Flowable<HttpResponse<LoginResponse>> doLogin(@Field("acount") String acount, @Field("pwd") String pwd);


  //@FormUrlEncoded
  @POST("user/login")
  Flowable<HttpResponse<LoginResponse>> doLogin(@Body RequestBody requestBody);


  /**
   * 8．注册
   *
   * @param account  手机号码
   * @param pwd      登录密码
   * @param nickname 会员名
   * @param code     短信验证码
   */

  @FormUrlEncoded
  @POST("user/Register")
  Flowable<HttpResponse<RegisterResponse>> doRegister(@Field("acount") String account, @Field("pwd") String pwd, @Field("nickname") String nickname, @Field("code") String code);


//  @POST("user/Register")
//  Flowable<HttpResponse<RegisterResponse>> doRegister2(@QueryMap Map<String, String> params);
//
//
//  @POST("user/Register")
//  Flowable<HttpResponse<RegisterResponse>> doRegister3(@Body RequestBody requestBody);


  /**
   * 微信登录获取access_token
   */
  @GET("https://api.weixin.qq.com/sns/oauth2/access_token")
  Flowable<WeChatGetAccessTokenResponse> doWeChatGetAccessToken(@QueryMap Map<String, String> params);


  /**
   * 获取微信信息
   *
   * @param params return
   */

  @GET("https://api.weixin.qq.com/sns/userinfo")
  Flowable<WeChatGetUserinfoResponse> doWeChatGetUserinfo(@QueryMap Map<String, String> params);


  /**
   * 6．微信授权登录
   * @param
   * @return
   */


  @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
  @POST("user/wxauth")
  Flowable<HttpResponse<LoginResponse>> dowxauth(@Body RequestBody requestBody);

  /**
   * 7．微信绑定
   * @param openid
   * @param account
   * @param pwd
   * @return
   */

  @FormUrlEncoded
  @POST("user/wxbind")
  Flowable<HttpResponse<LoginResponse>> dowxbind(@Field("openid") String openid,@Field("account") String account,@Field("pwd") String pwd);



  @FormUrlEncoded
  @POST("webapi/person/forgetloginpwd")
  Flowable<BaseResponse> doForgetloginpwd(@Field("Account") String Account,@Field("Pwd") String pwd);


  //登录相关结束


  //我的相关开始

  /**
   * 9．个人中心提示
   */

  @GET("person/personinfo")
  Flowable<PersonInfoResponse> doPersoninfo(@Query("uid") String uid);


  /**
   * 10．个人中心推荐商品
   */

  @GET("person/getdata")
  Flowable<GetDataResponse> doGetData(@Query("index") int index, @Query("size") int size, @Query("name") String name);

  //我的相关结束


  //发现

  /**
   * 23．发现接口
   */
  @GET("webapi/FindGoods/Get")
  Flowable<FindGoodsResponse> doFindGoods(@Query("index") int index, @Query("size") int size);


  //发现


  //分类

  /**
   * 24．商品分类
   */
  @GET("webapi/GoodsList/getclassify")
  Flowable<CategoryResponse> doGetclassify();


  //分类

  //首页开始

  /**
   * 25．当地页广告
   */
  @GET("webapi/local/getld")
  Flowable<LocalAdResponse> doGetLocalAd(@Query("area") String area);


  /**
   * 26．本地推荐商品
   */
  @GET("webapi/goodslist/Localgoodsdata")
  Flowable<LocalgoodsdataResponse> doLocalgoodsdata(@Query("index") int index, @Query("size") int size, @Query("area") String area, @Query("name") String name);


  /**
   * 26．全国商品
   */
  @GET("goodslist/getData")
  Flowable<LocalgoodsdataResponse> doGoodsdata(@Query("index") int index, @Query("size") int size,  @Query("name") String name);



  /**
   * 获取区县
   */
  @GET("webapi/local/getareas")
  Flowable<CityBean> doGetareas();

  /**
   * 18．购物车
   */

  @GET("webapi/shopcart/GetCartGoodsList")
  Flowable<GetCartGoodsListResponse> doGetCartGoodsList(@Query("uid") int uid);


  /**
   * 21．购物车中商品增减操作
   */

//  @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//  @FormUrlEncoded
//  @POST("webapi/shopcart/UpdateGoodsNum")
//  Flowable<BaseResponse> doUpdateGoodsNum(@Field("Id") int Id, @Field("num") int num);
  @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
  @POST("webapi/shopcart/UpdateGoodsNum")
  Flowable<BaseResponse> doUpdateGoodsNum(@Body RequestBody requestBody);


  /**
   * 22．删除购物车商品
   * @param requestBody
   * @return
   */

  @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
  @POST("webapi/shopcart/DeleteCartGoods")
  Flowable<BaseResponse> doDeleteCartGoods(@Body RequestBody requestBody);


  //首页结束

}
