package com.fdl.requestApi;


import com.fdl.bean.AccountDetailsBean;
import com.fdl.bean.AddressBean;
import com.fdl.bean.AgreementBean;
import com.fdl.bean.BanksBean;
import com.fdl.bean.BannerBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CityBean;
import com.fdl.bean.CouponsBean;
import com.fdl.bean.DiscoverBean;
import com.fdl.bean.FoodPredeterminelistBean;
import com.fdl.bean.GoodsBean;
import com.fdl.bean.MainProcuctBean;
import com.fdl.bean.MoneyBean;
import com.fdl.bean.MyCouponsBean;
import com.fdl.bean.NormBean;
import com.fdl.bean.ShakyBean;
import com.fdl.bean.ShopGoodsType;
import com.fdl.bean.StoreBannerBean;
import com.fdl.bean.StoreClassficationBean;
import com.fdl.bean.SubscribeApplyInfoBean;
import com.fdl.bean.SubscribeInfoBean;
import com.fdl.bean.SuperMarkGoodsBean;
import com.fdl.bean.SuperMarketBean;
import com.fdl.bean.TravelBean;
import com.fdl.bean.TravelLuckyBean;
import com.fdl.bean.VersionBean;
import com.fdl.bean.WalletInfoBean;
import com.fdl.bean.MyOrderBean;
import com.fdl.bean.OrderBean;
import com.fdl.bean.OrderDataBean;
import com.fdl.bean.OrderDetailsBean;
import com.fdl.bean.PayWxBean;
import com.fdl.bean.ProductDetailsBean;
import com.fdl.bean.ProductFollowBean;
import com.fdl.bean.RefundOrderBean;
import com.fdl.bean.ShopBean;
import com.fdl.bean.ShopDetailsBean;
import com.fdl.bean.WechatBean;
import com.fdl.bean.WinningDetailsBean;
import com.fdl.bean.WinningLogBean;
import com.fdl.bean.WinningLogUserBean;
import com.fdl.bean.WithdrawBean;
import com.fdl.bean.WithdrawDetailsBean;
import com.fdl.bean.WoolBean;
import com.fdl.bean.daoBean.MyBankBean;
import com.fdl.bean.daoBean.SupplierBean;
import com.sg.cj.snh.model.response.login.LoginResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface RequestApi {

    /***
     * 请求成功code
     */
    int OK_CODE = 1;
    /***
     * API host,后续url不要开头带/
     */
    @GET("webapi/Version/GetVersion")
    Observable<BaseResultBean<VersionBean>> GetVersion(@QueryMap Map<String,Object> params);

    /**
     *获取微信openid
     */

//    @Headers({"api_version:1.0","Content-Type:application/json"})
//    @GET("sns/oauth2/access_token")
//    Observable<WxBean> GetWxData(@QueryMap Map<String, String> params);


    /**
     * 上传文件
     * @param parts
     * @return
     */
    @Multipart
    @POST("api/ApiSource")
    Observable<BaseResultBean> UpLoadFile(@Part List<MultipartBody.Part> parts);

    /**
     * 获取短信验证码
     * @param params
     * @return
     */
    @POST("user/sendphonecode")
    Observable<BaseResultBean> GetPhoneCode(@Body Map<String,Object> params);

    /**
     * 验证短信
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/validatecode")
    Observable<BaseResultBean> ValidateCode(@FieldMap Map<String,Object> params);

    @POST("person/updateusertel")
    Observable<BaseResultBean> updateusertel(@Body Map<String,Object> params);
    /**
     * 注册
     * @param prams
     * @return
     */
    @FormUrlEncoded
    @POST("user/Register")
    Observable<BaseResultBean<LoginResponse>> Regist(@FieldMap Map<String,Object> prams);
    /**
     * 商品详情
     * @param params
     * @return
     */
    @GET("webapi/GoodsDetail/GetDetail")
    Observable<BaseResultBean<ProductDetailsBean>> GetProductDetails(@QueryMap Map<String,Object> params);

    /**
     * 商品加入购物车
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("webapi/shopcart/AddToCart")
    Observable<BaseResultBean> AddProductToCar(@FieldMap Map<String ,Object> params);

    /**
     * 关注商品
     * @param params
     * @return
     */
    @GET("webapi/GoodsDetail/FollowGoods")
    Observable<BaseResultBean> IsFollow(@QueryMap Map<String,Object> params);

    /**
     * 获取购物车信息
     * @param params
     * @return
     */
    @GET("webapi/shopcart/GetCartGoodsList")
    Observable<BaseResultBean<List<OrderDataBean>>> GetBuyCar(@QueryMap Map<String,Object> params);
    /**
     * 下单页面数据
     * @return
     */
    @GET("webapi/ShopOrder/ConfirmOrder")
    Observable<BaseResultBean<OrderBean>> GetOrderData(@QueryMap Map<String,Object> params);

    /**
     * 提交订单(直接下单)
     * @param params
     * @return
     */
    @POST("webapi/ShopOrder/SaveOrder")
    Observable<BaseResultBean> PostOrderData(@Body Map<String,Object> params);

    /**
     * 提交订单(购物车)
     * @return
     */
//    @FormUrlEncoded
    @POST("webapi/ShopOrder/SaveOrderByCart")
    Observable<BaseResultBean> PostOrder(@Body Map<String,Object> params);
//    @POST("webapi/ShopOrder/SaveOrderByCart")
//    Observable<BaseResultBean> PostOrder(@Query("addressid") String aid,@Query("userid") int uid,@Body List<StoreinfoBean> beans);
    /**
     * 订单支付
     * @param params
     * @return
     */

    @POST("paynew/userpay")
    Observable<BaseResultBean<PayWxBean>> Pay(@Body Map<String,Object> params);
    @POST("paynew/userpay")
    Observable<BaseResultBean<String>> Pay1(@Body Map<String,Object> params);
    /**
     * 获取收货地址
     * @param params
     * @return
     */
    @GET("webapi/DeliveryAddress/GetAddress")
    Observable<BaseResultBean<List<AddressBean>>> GetAddress(@QueryMap Map<String,Object> params);

    /**
     * 上传收货地址
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("webapi/DeliveryAddress/Add")
    Observable<BaseResultBean> PostAddress(@FieldMap Map<String,Object> params);

    /**
     * 删除收货地址
     * @param params
     * @return
     */
    @GET("webapi/DeliveryAddress/Delete")
    Observable<BaseResultBean> DelAddress(@QueryMap Map<String,Object> params);


    /**
     * 用户修改地址
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("webapi/DeliveryAddress/Update")
    Observable<BaseResultBean> FixAddress(@FieldMap Map<String,Object> params);

    /**
     * 用户修改头像
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("person/updateuserinfo")
    Observable<BaseResultBean> FixHeardImg(@FieldMap Map<String,Object> params);

    /**
     * 用户提交反馈意见
     * @return
     */
    @FormUrlEncoded
    @POST("person/addopinion")
    Observable<BaseResultBean> FeedSuggestion(@FieldMap Map<String,Object> params);

    /**
     * 修改登陆密码
     * @return
     */
    @FormUrlEncoded
    @POST("person/updateloginpwd")
    Observable<BaseResultBean> FixPsw(@FieldMap Map<String,Object> params);

    /**
     * 设置支付密码
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("person/updatepaypwd")
    Observable<BaseResultBean> FixPayPsw(@FieldMap Map<String,Object> params);


    /**
     * 获取订单列表
     * @param params
     * @return
     */
    @GET("webapi/ShopOrder/GetOrderList")
    Observable<BaseResultBean<List<MyOrderBean>>> GetOrderList(@QueryMap Map<String,Object> params);

    /**
     * 提醒发货
     * @param params
     * @return
     */

    @POST("webapi/message/postmsg")
    Observable<BaseResultBean> PostMsg(@Body Map<String,Object> params);

    /**
     * 确认收货
     * @param params
     * @return
     */
    @GET("webapi/ShopOrder/ConfirmReceiveProduct")
    Observable<BaseResultBean> ComfireGet(@QueryMap Map<String,Object> params);

    /**
     * 取消订单
     * @param params
     * @return
     */
    @GET("webapi/ShopOrder/CancelOrder")
    Observable<BaseResultBean> CancelOrder (@QueryMap Map<String ,Object> params);

    /**
     * 获取订单详情
     * @param params
     * @return
     */
    @GET("webapi/ShopOrder/GetOrderDetail")
    Observable<BaseResultBean<OrderDetailsBean>> GetOrderDetail(@QueryMap Map<String,Object> params);


    /**
     * 获取售后列表
     * @param params
     * @return
     */
    @GET("webapi/refundOrder/getlist")
    Observable<BaseResultBean<List<RefundOrderBean>>> GetRefundOrderList(@QueryMap Map<String,Object> params);

    /**
     * 获取售后点单详情
     * @param params
     * @return
     */
    @GET("webapi/refundOrder/getdetail")
    Observable<BaseResultBean<RefundOrderBean>> GetRefundOrderDetail(@QueryMap Map<String,Object> params);

    /**
     * 获取用户资金
     * @param params
     * @return
     */
    @GET("person/getmoneyinfo")
    Observable<BaseResultBean> GetMoney(@QueryMap Map<String,Object> params);

    /**
     * 获取羊毛列表
     * @param params
     * @return
     */
    @GET("webapi/BargainS/Gethymlist")
    Observable<BaseResultBean<List<WoolBean>>> GetHymList(@QueryMap Map<String,Object> params);

    /**
     * 获取羊毛详情
     * @param params
     * @return
     */
    @GET("webapi/BargainS/Details")
    Observable<BaseResultBean<WoolBean>> GetHymDetails(@QueryMap Map<String,Object> params);

    /**
     * 获取薅羊毛订单号
     * @param params
     * @return
     */
    @GET("BargainS/GetOrderhym")
    Observable<BaseResultBean<List<WoolBean.HymBean>>> GetHymOrder(@QueryMap Map<String,Object> params);

    /**
     * 提交评论
     * @return
     */
    @FormUrlEncoded
    @POST("webapi/ShopOrder/PostComment")
    Observable<BaseResultBean> PostComment(@FieldMap Map<String,Object> params);

    /**
     * 获取店铺详情
     * @param params
     * @return
     */
    @GET("webapi/Shop/GetShopInfo")
    Observable<BaseResultBean<ShopDetailsBean>> GetShopInfo(@QueryMap Map<String,Object> params);

    /**
     * 获取店铺商品
     * @param params
     * @return
     */
    @GET("webapi/shop/GetPage")
    Observable<BaseResultBean<List<GoodsBean>>> GetShopProduct(@QueryMap Map<String,Object> params);

    /**
     * 关注店铺
     * @param params
     * @return
     */
    @GET("webapi/Shop/FollowShop")
    Observable<BaseResultBean> FollowShop (@QueryMap Map<String,Object> params);


    /**
     * 收藏店铺列表
     * @param params
     * @return
     */
    @GET("webapi/person/getgoodsfollow")
    Observable<BaseResultBean<List<ProductFollowBean>>> GetProductFollow(@QueryMap Map<String,Object> params);


    /**
     * 删除商品收藏记录
     * @param params
     * @return
     */
    @GET("webapi/person/deletegoodsfollow")
    Observable<BaseResultBean> DeleteProductFollow(@QueryMap Map<String,Object> params);

    /**
     * 关注店铺列表
     * @param prams
     * @return
     */
    @GET("webapi/person/getshopfollow")
    Observable<BaseResultBean<List<ShopBean>>>  GetShopFollow(@QueryMap Map<String,Object> prams);

    /**
     * 删除店铺关注
     * @param params
     * @return
     */
    @GET("webapi/person/deleteshopfollow")
    Observable<BaseResultBean> DeleteShopFollow(@QueryMap Map<String,Object> params);

    /**
     * 获取浏览记录列表
     * @param params
     * @return
     */
    @GET("webapi/person/getgoodsviewer")
    Observable<BaseResultBean<List<GoodsBean>>> GetBrowsHistory(@QueryMap Map<String,Object> params);

    /**
     * 删除浏览记录
     * @param params
     * @return
     */
    @GET("webapi/person/deletegoodsviewer")
    Observable<BaseResultBean> DeleteBrowsHistory(@QueryMap Map<String,Object> params);

    /**
     * 删除购物车商品
     * @param params
     * @return
     */
    @POST("webapi/shopcart/DeleteCartGoods")
//    Observable<BaseResultBean> DeleteCarGoods(@FieldMap Map<String,Object> params);
    Observable<BaseResultBean> DeleteCarGoods(@Body Map<String,Object> params);

    /**
     * 购物车商品数量添加
     * @param prams
     * @return
     */
    @POST("webapi/shopcart/UpdateGoodsNum")
    Observable<BaseResultBean> UpdataGoodsNum(@Body Map<String,Object> prams);

    /**
     * 我的卡包
     * @param params
     * @return
     */
    @GET("webapi/Wallet/GetWalletInfo")
    Observable<BaseResultBean> GetWalletInfo(@QueryMap Map<String,Object> params);


    /**
     * 获取资金明细
     * @param params
     * @return
     */
    @GET("UserMoney/GetUserMoneyLog")
    Observable<BaseResultBean<WithdrawBean>> GetSupplierMoneyLog(@QueryMap Map<String,Object> params);

    /**
     * 积分明细
     * @param params
     * @return
     */
    @GET("UserMoney/Getintegrallog")
    Observable<BaseResultBean<List<AccountDetailsBean>>> Getintegrallog(@QueryMap Map<String,Object> params);

    /**
     * 待返余额
     * @param params
     * @return
     */
    @GET("UserMoney/Getbargainuserlog")
    Observable<BaseResultBean<List<AccountDetailsBean>>> Getbargainuserlog(@QueryMap Map<String,Object> params);

    /**
     * 客服资料
     * @param params
     * @return
     */
    @GET("webapi/Helper/GetServerTel")
    Observable<BaseResultBean> GetServerTel(@QueryMap Map<String,Object> params);

    /**
     * 获取区县信息
     * @param params
     * @return
     */
    @GET("webapi/local/getareas")
    Observable<BaseResultBean<List<CityBean>>> Getareas(@QueryMap Map<String,Object> params);

    /**
     * 商家入驻获取验证码
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("SupplierEnter/SmsCode")
    Observable<BaseResultBean> PostSms(@FieldMap Map<String,Object> params);

    /**
     * 商家入驻登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("SupplierEnter/PhoneLogin")
    Observable<BaseResultBean> MerchantLogin(@FieldMap Map<String,Object> params);

    /**
     * 当地入驻
     * @param params
     * @return
     */
    @POST("SupplierEnter/LocalEnter")
    Observable<BaseResultBean> MerchantLocalEnter(@Body Map<String,Object> params);

    /**
     * 个人入驻
     * @param params
     * @return
     */
    @POST("SupplierEnter/PersonageEnter")
    Observable<BaseResultBean> MerchantPersonEnter(@Body Map<String,Object> params);

    /**
     * 企业入驻
     * @param params
     * @return
     */
    @POST("SupplierEnter/EnterpriseEnter")
    Observable<BaseResultBean> MerchantCompanyEntry(@Body Map<String,Object> params);

    /**
     * 获取协议web
     * @return
     */
    @GET("webapi/SnhAgreement/GetAgreementList")
    Observable<BaseResultBean<List<AgreementBean>>> GetAgreementList();

    /**
     * 获取主营类目
     * @param params
     * @return
     */
    @GET("Shop/GetShopType")
    Observable<BaseResultBean<List<StoreClassficationBean>>> GetShopType(@QueryMap Map<String,Object> params);
    /**
     * 获取商家列表
     * @return
     */
    @GET("SuperMarket/GetSuperMarketList")
    Observable<BaseResultBean<List<SuperMarketBean>>> GetSuperMarketList(@QueryMap Map<String,Object> params);

    /**
     * 获取商超士多商品
     * @param params
     * @return
     */
    @GET("SuperMarket/GetSuperMarketCommTenant")
    Observable<BaseResultBean<SupplierBean>> GetSuperMarketCommTenant(@QueryMap Map<String,Object> params);
    @GET("food/GetSuperMarketCommTenant")
    Observable<BaseResultBean<SupplierBean>> GetSuperMarketCommTenant1(@QueryMap Map<String,Object> params);
    /**
     * 绑定微信
     * @param params
     * @return
     */
    @POST("user/WxBindAccount")
    Observable<BaseResultBean<WechatBean>> wxbind (@Body Map<String,Object> params);

    /**
     * 关注超市
     * @param paras
     * @return
     */
    @FormUrlEncoded
    @POST("SuperMarket/FavoriteShop")
    Observable<BaseResultBean<String>> FavoriteShop(@FieldMap Map<String,Object> paras);

    /**
     * 商超士多 提交订单
     * @param params
     * @return
     */
    @POST("SuperMarket/CommitOrder")
    Observable<BaseResultBean> CommitOrder(@Body Map<String,Object> params);

    /**
     * 商超士多详情页
     * @param params
     * @return
     */
    @GET("SuperMarket/GetGoodsDetail")
    Observable<BaseResultBean<SuperMarkGoodsBean>> GetGoodsDetail(@QueryMap Map<String,Object> params);

    /**
     * 获取首页banner图数据
     * @return
     */
    @GET()
    Observable<BaseResultBean<BannerBean>> GetBannerData(@Url String url);

    /**
     * 获取当地推荐商品
     * @param params
     * @return
     */
    @GET("webapi/goodslist/Localgoodsdata")
    Observable<BaseResultBean<List<MainProcuctBean>>> Localgoodsdata(@QueryMap Map<String,Object> params);

    /**
     * 获取全国商品
     * @param params
     * @return
     */
    @GET("goodslist/getData")
    Observable<BaseResultBean<List<MainProcuctBean>>> GetData(@QueryMap Map<String,Object> params);

    /**
     * 获取区县资料
     * @return
     */
    @GET("webapi/local/getareas")
    Observable<BaseResultBean<List<CityBean>>> GetAreas();

    /**
     * 获取发现数据
     * @param params
     * @return
     */
    @GET("webapi/FindGoods/Get")
    Observable<BaseResultBean<List<DiscoverBean>>> GetDiscover(@QueryMap Map<String,Object> params);

    /**
     * 获取个人数据
     * @param params
     * @return
     */
    @GET("person/personinfo")
    Observable<BaseResultBean> GetPersonInfo(@QueryMap Map<String,Object> params);

    /**
     * 搜索接口
     * @param params
     * @return
     */
    @GET("webapi/local/search")
    Observable<BaseResultBean<List<MainProcuctBean>>> Search(@QueryMap Map<String,Object> params);

    /**
     * 规格获取价格
     * @param params
     * @return
     */
    @GET("webapi/GoodsDetail/GetNormPrice")
    Observable<BaseResultBean<NormBean>> GetNormPrice(@QueryMap Map<String,Object> params);


    /**----------------------------去旅行---------------------------------**/

    /**
     * 获取抽盘数据
     * @param params
     * @return
     */
    @GET("webapi/NewPrize/GetPrizePlate")
    Observable<BaseResultBean<TravelBean>> GetPrizePool(@QueryMap Map<String,Object> params);

    /**
     * 抽奖
     * @param params
     * @return
     */
    @POST("webapi/NewPrize/NewLuckyDraw")
    Observable<BaseResultBean<TravelLuckyBean>> LuckyDraw(@Body Map<String,Object> params);

    /**
     * 获取所有中奖记录集合
     * @param params
     * @return
     */
    @GET("webapi/NewPrize/GetWinningLog")
    Observable<BaseResultBean<List<WinningLogBean>>> GetWinningLog(@QueryMap Map<String,Object> params);

    /**
     * 获取我的抽奖记录
     * @param params
     * @return
     */
    @GET("webapi/NewPrize/GetWinningLogForUser")
    Observable<BaseResultBean<List<WinningLogUserBean>>> GetWinningLogForUser(@QueryMap Map<String,Object> params);

    /**
     * 获取抽奖记录详情
     * @param params
     * @return
     */
    @GET("webapi/NewPrize/GetPrizeLogDetail")
    Observable<BaseResultBean<WinningDetailsBean>> GetPrizeLogDetail(@QueryMap Map<String,Object> params);
    /**
     * 获取店铺优惠券
     * @param params
     * @return
     */
    @GET("webapi/Coupons/GetSupplierCoupons")
    Observable<BaseResultBean<List<CouponsBean>>> GetSupplierCoupons(@QueryMap Map<String,Object> params);

    /**
     * 获取我的优惠券
     * @param params
     * @return
     */
    @GET("webapi/Coupons/GetUserCouponsList")
    Observable<BaseResultBean<MyCouponsBean>> GetUserCouponsList(@QueryMap Map<String,Object> params);
    /**
     * 领取优惠券
     * @param params
     * @return
     */
    @POST("webapi/Coupons/ReceiveCoupons")
    Observable<BaseResultBean> ReceiveCoupons(@Body Map<String,Object> params);

    /**
     * 获取用户在店铺的优惠券
     * @param params
     * @return
     */
    @GET("webapi/Coupons/GetUseCoupon")
    Observable<BaseResultBean<List<CouponsBean>>> GetUseCoupon(@QueryMap Map<String,Object> params);
    /**
     * 获取店铺banner和推荐商品
     * @param params
     * @return
     */
    @GET("shopbanner/BannerGetList")
    Observable<BaseResultBean<StoreBannerBean>> BannerGetList(@QueryMap Map<String,Object> params);

    /**
     * 店铺banner广告点击次数回调
     * @param params
     * @return
     */
    @POST("shopbanner/BannerClick")
    Observable<BaseResultBean> BannerClick(@Body Map<String,Object> params);

    /**
     * 获取某个订单是否能去薅羊毛，或者去旅行
     * @param params
     * @return
     */
    @GET("webapi/NewPrize/GetActivity")
    Observable<BaseResultBean<ShakyBean>> GetHymOrQlx(@QueryMap Map<String,Object> params);
    /**
     * 获取账号资金信息
     * @param params
     * @return
     */
    @GET("UserMoney/GetAccountMoneyForUser")
    Observable<BaseResultBean<MoneyBean>> GetAccountMoney(@QueryMap Map<String,Object> params);

    /**
     * 获取银行卡信息
     * @param params
     * @return
     */
    @GET("UserMoney/GetUserBankCards")
    Observable<BaseResultBean<List<MyBankBean>>> GetSupplierBankCards(@QueryMap Map<String,Object> params);

    /**
     * 删除银行卡
     * @param params
     * @return
     */
    @POST("UserMoney/DeleteUserBankCard")
    Observable<BaseResultBean> DeleteSupplierBankCard(@Body Map<String,Object> params);

    /**
     * 获取银行接口
     * @param params
     * @return
     */
    @GET("SupplierMoney/GetBanks")
    Observable<BaseResultBean<List<BanksBean>>> GetBanks(@QueryMap Map<String,Object> params);

    /**
     * 新增银行卡
     * @param params
     * @return
     */
    @POST("UserMoney/AddUserBankCard")
    Observable<BaseResultBean> AddSupplierBankCard(@Body Map<String,Object> params);

    /**
     * 提现
     * @param params
     * @return
     */
    @POST("UserMoney/Withdrawal")
    Observable<BaseResultBean> Withdrawal(@Body Map<String,Object> params);
    /**
     * 获取资金明细详情
     * @param params
     * @return
     */
    @GET("UserMoney/GetUserMoneyLogDetail")
    Observable<BaseResultBean<WithdrawDetailsBean>> GetSupplierMoneyDetails(@QueryMap Map<String,Object> params);
    /**------------------------------------------美食分享----------------------------------------------**/

    /**
     * 商家可预订基本信息
     * @param params
     * @return
     */
    @GET("food/subscribe_get")
    Observable<BaseResultBean<SubscribeInfoBean>> GetSubscribeInfo(@QueryMap Map<String,Object> params);

    /**
     * 预定订单信息提交
     * @param params
     * @return
     */
    @POST("food/subscribe_apply")
    Observable<BaseResultBean<Integer>> PostSubscribeInfo(@Body Map<String,Object> params);

    /**
     * 预定订单获取
     * @param params
     * @return
     */
    @GET("food/subscribe_apply_getbyid")
    Observable<BaseResultBean<SubscribeApplyInfoBean>> GetSubscribeApplyInfo(@QueryMap Map<String,Object> params);

    /**
     * 预订单提交
     * @param params
     * @return
     */
    @POST("food/SaveOrder")
    Observable<BaseResultBean> SaveOrder(@Body Map<String,Object> params);

    /**
     *预定订单列表获取
     * @param params
     * @return
     */
    @GET("food/subscribe_apply_list")
    Observable<BaseResultBean<List<FoodPredeterminelistBean>>> GetPredeterminerList(@QueryMap Map<String,Object> params);

    /**
     * 预定订单取消
     * @param params
     * @return
     */
    @POST("food/subscribe_apply_cancel")
    Observable<BaseResultBean> CanclePredeterminerOrder(@Body Map<String,Object> params);

    /**
     * 更改预订单（菜品的增减）
     * @param params
     * @return
     */
    @POST("food/goods_order_change")
    Observable<BaseResultBean> ChangePredeterminerOrder(@Body Map<String,Object> params);

    /**
     * 预定订单优惠劵更改
     * @param params
     * @return
     */
    @POST("food/couponschange")
    Observable<BaseResultBean> CouponsChange(@Body Map<String,Object> params);


    /**
     * 新增银行卡
     * @param params
     * @return
     */
    @GET("RedPacket/GetIsAvailable")
    Observable<BaseResultBean> getRedPacketType(@QueryMap Map<String,Object> params);

}
