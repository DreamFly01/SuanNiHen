package com.fdl.requestApi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.fdl.activity.main.redPacket.bean.CouponsGoodsBean;
import com.fdl.activity.main.redPacket.bean.RedPacketBean;
import com.fdl.bean.AccountDetailsBean;
import com.fdl.bean.AddressBean;
import com.fdl.bean.AgreementBean;
import com.fdl.bean.BanksBean;
import com.fdl.bean.BannerBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.CityBean;
import com.fdl.bean.CouponsBean;
import com.fdl.bean.DiscoverBean;
import com.fdl.bean.FoodGoodsCommitBean;
import com.fdl.bean.FoodPredeterminelistBean;
import com.fdl.bean.GoodsBean;
import com.fdl.bean.IdBean;
import com.fdl.bean.MainProcuctBean;
import com.fdl.bean.MoneyBean;
import com.fdl.bean.MyCouponsBean;
import com.fdl.bean.MyOrderBean;
import com.fdl.bean.NormBean;
import com.fdl.bean.OrderBean;
import com.fdl.bean.OrderComfireBean;
import com.fdl.bean.OrderDataBean;
import com.fdl.bean.OrderDetailsBean;
import com.fdl.bean.PayWxBean;
import com.fdl.bean.ProductDetailsBean;
import com.fdl.bean.ProductFollowBean;
import com.fdl.bean.RefundOrderBean;
import com.fdl.bean.ShakyBean;
import com.fdl.bean.ShopBean;
import com.fdl.bean.ShopDetailsBean;
import com.fdl.bean.ShopGoodsType;
import com.fdl.bean.StoreBannerBean;
import com.fdl.bean.StoreClassficationBean;
import com.fdl.bean.StoreinfoBean;
import com.fdl.bean.SubscribeApplyInfoBean;
import com.fdl.bean.SubscribeInfoBean;
import com.fdl.bean.SuperMarkGoodsBean;
import com.fdl.bean.SuperMarketBean;
import com.fdl.bean.TravelBean;
import com.fdl.bean.TravelLuckyBean;
import com.fdl.bean.VersionBean;
import com.fdl.bean.WalletInfoBean;
import com.fdl.bean.WechatBean;
import com.fdl.bean.WinningDetailsBean;
import com.fdl.bean.WinningLogBean;
import com.fdl.bean.WinningLogUserBean;
import com.fdl.bean.WithdrawBean;
import com.fdl.bean.WithdrawDetailsBean;
import com.fdl.bean.WoolBean;
import com.fdl.bean.daoBean.MyBankBean;
import com.fdl.bean.daoBean.SupplierBean;
import com.fdl.db.DBManager;
import com.fdl.jpush.Logger;
import com.fdl.utils.Contans;
import com.fdl.utils.NetworkUtils;
import com.fdl.utils.SPUtils;
import com.fdl.utils.StrUtils;
import com.fdl.utils.UriUtils;
import com.google.gson.Gson;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.model.response.login.LoginResponse;

import java.io.File;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.android.BuildConfig;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RequestClient {


    /**
     * 版本更新
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetVersion(Context context, NetSubscriber<BaseResultBean<VersionBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("String", 1);
        map.put("SourceSystem", 1);
        return doRequest1(RetrofitProxy.getApiService(context, "").GetVersion(map), context, observer);
    }

    /**
     * 上传附件
     *
     * @param path
     * @param context
     * @param observer
     * @return
     */
    public static Subscription UpLoadFile(List<String> path, Context context, NetSubscriber<BaseResultBean> observer) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
//            File file = new File(UriUtils.getRealPathFromURI(context,path.get(i)));
            File file = new File(path.get(i));
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("MultipartFile", file.getName(), fileBody);
            parts.add(part);
        }
        return doRequest1(RetrofitProxy1.getApiService(context, "").UpLoadFile(parts), context, observer);
    }

    /**
     * 获取短信验证码
     *
     * @param phone
     * @param type     1 短信登录，2 注册 ，3绑定，4修改手机号码,5忘记密码
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetPhoneCode(String phone, String type, Context context, NetSubscriber<BaseResultBean> observer) {
        //2780
        Map<String, Object> map = new TreeMap<>();
        map.put("phone", phone);
        map.put("type", type);
        return doRequest1(RetrofitProxy.getApiService(context, "").GetPhoneCode(map), context, observer);
    }

    /**
     * 验证短信验证码
     *
     * @param phone
     * @param type
     * @param code
     * @param context
     * @param observer
     * @return
     */
    public static Subscription ValidateCode(String phone, String type, String code, Context context, NetSubscriber<BaseResultBean> observer) {
        //2780
        Map<String, Object> map = new TreeMap<>();
        map.put("phone", phone);
        map.put("type", type);
        map.put("code", code);
        return doRequest1(RetrofitProxy.getApiService(context, "").ValidateCode(map), context, observer);
    }

    public static Subscription updateusertel(String phone, String code, Context context, NetSubscriber<BaseResultBean> observer) {
        //2780
        Map<String, Object> map = new TreeMap<>();
        map.put("Tel", phone);
        map.put("code", code);
        map.put("Id", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").updateusertel(map), context, observer);
    }

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param nickname
     * @param code
     * @param context
     * @param observer
     * @return
     */
    public static Subscription Regist(String phone, String pwd, String nickname, String code, Context context, NetSubscriber<BaseResultBean<LoginResponse>> observer) {
        //2780
        Map<String, Object> map = new TreeMap<>();
        map.put("acount", phone);
        map.put("pwd", pwd);
//        map.put("nickname", nickname);
        map.put("code", code);
        return doRequest1(RetrofitProxy.getApiService(context, "").Regist(map), context, observer);
    }

    /**
     * 获取商品详情
     *
     * @param goodsid
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetProductDetails(String goodsid, Context context, NetSubscriber<BaseResultBean<ProductDetailsBean>> observer) {
        //2780
        Map<String, Object> map = new TreeMap<>();
        map.put("goodsId", goodsid);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        SgLog.d(goodsid + "           " + PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetProductDetails(map), context, observer);
    }

    /**
     * 添加商品到购物车
     *
     * @param SupplierId
     * @param GoodsId
     * @param Number
     * @param NormsId
     * @param NormsInfo
     * @param Weight
     * @param ExpressTemplateId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription AddProductToCar(String NormsValuesId, int SupplierId, int GoodsId, int Number, String NormsId, int ExpressTemplateId, String NormsInfo, double Weight, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("NormsValuesId", NormsValuesId);
        map.put("SupplierId", SupplierId);
        map.put("GoodsId", GoodsId);
        map.put("Number", Number);
        map.put("NormsInfo", NormsInfo);
        map.put("NormsId", NormsId);
        map.put("Weight", Weight);
        map.put("ExpressTemplateId", ExpressTemplateId);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").AddProductToCar(map), context, observer);
    }


    /**
     * 规格获取价格
     *
     * @param GoodsId
     * @param NormsId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetNormPrice(int GoodsId, String NormsId, Context context, NetSubscriber<BaseResultBean<NormBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("goodsid", GoodsId);
        map.put("normId", NormsId);
        return doRequest1(RetrofitProxy.getApiService(context, "").GetNormPrice(map), context, observer);
    }

    /**
     * 关注店铺
     *
     * @param goodsId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription IsFullow(int goodsId, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("goodsid", goodsId);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").IsFollow(map), context, observer);

    }

    /**
     * 获取订单页面数据
     *
     * @param goodsId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetOrderData(String GoodsNormName, String goodsId, String GoodsNum, String GoodsNormId, String cartids, Context context, NetSubscriber<BaseResultBean<OrderBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        if (!StrUtils.isEmpty(GoodsNormName)) {
            map.put("GoodsNormName", GoodsNormName);
        } else {
            map.put("GoodsNormName", "");
        }
        if (!StrUtils.isEmpty(goodsId)) {
            map.put("GoodsId", goodsId);
        }

        if (!StrUtils.isEmpty(GoodsNum)) {
            map.put("GoodsNum", GoodsNum);
        }

        if (!StrUtils.isEmpty(GoodsNormId)) {
            map.put("GoodsNormId", GoodsNormId);
        } else {
            map.put("GoodsNormId", "");
        }
        if (!StrUtils.isEmpty(cartids)) {
            map.put("cartids", cartids);
        }
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetOrderData(map), context, observer);
    }

    /**
     * 提交订单页面(直接下单)
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription PostOrderData(String addressid, String GoodsId, String GoodsNum, String GoodsNormId, String LeaveWord, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("addressid", addressid);
        map.put("GoodsId", GoodsId);
        map.put("GoodsNum", GoodsNum);
        if (!StrUtils.isEmpty(GoodsNormId)) {
            map.put("GoodsNormId", GoodsNormId);
        } else {
            map.put("GoodsNormId", "");
        }
        if (!StrUtils.isEmpty(LeaveWord)) {
            map.put("LeaveWord", LeaveWord);
        }

        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").PostOrderData(map), context, observer);
    }

    /**
     * 提交订单(购物车)
     *
     * @param addressid
     * @param storeinfo
     * @param context
     * @param observer
     * @return
     */
    public static Subscription PostOrder(String addressid, List<StoreinfoBean> storeinfo, Context context, NetSubscriber<BaseResultBean> observer) {
        String storeinfoStr = new Gson().toJson(storeinfo);
        Map<String, Object> map = new TreeMap<>();
        map.put("addressid", addressid);
        map.put("storeinfo", storeinfo);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        SgLog.d(new Gson().toJson(map));
        return doRequest1(RetrofitProxy.getApiService(context, "").PostOrder(map), context, observer);
    }


    /**
     * 支付订单
     *
     * @param orderno
     * @param syspaytype
     * @param otherpay   1.余额支付 2.微信  3.支付宝   4.微信和余额  5.支付宝和余额  6.货到付款
     * @param context
     * @param observer
     * @return
     */
    public static Subscription Pay(String orderno, String syspaytype, String otherpay, String subOrderNo, Context context, NetSubscriber<BaseResultBean<PayWxBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderno", orderno);
        if (!StrUtils.isEmpty(syspaytype)) {
            map.put("syspaytype", syspaytype);
        }
        if (!StrUtils.isEmpty(otherpay)) {
            map.put("otherpay", otherpay);
        }
        map.put("subOrderNo", subOrderNo);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").Pay(map), context, observer);
    }

    public static Subscription Pay1(String orderno, String syspaytype, String otherpay, String subOrderNo, Context context, NetSubscriber<BaseResultBean<String>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderno", orderno);
        if (!StrUtils.isEmpty(syspaytype)) {
            map.put("syspaytype", syspaytype);
        }
        if (!StrUtils.isEmpty(otherpay)) {
            map.put("otherpay", otherpay);
        }
        map.put("subOrderNo", subOrderNo);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").Pay1(map), context, observer);
    }

    /**
     * 获取收货地址
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetAddress(Context context, NetSubscriber<BaseResultBean<List<AddressBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetAddress(map), context, observer);
    }

    /**
     * 添加收货地址
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription PostAddress(String RealName, String TelPhone, String AreaAddress, String Address, String IsDefault, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("RealName", RealName);
        map.put("TelPhone", TelPhone);
        map.put("AreaAddress", AreaAddress);
        map.put("Address", Address);
        map.put("IsDefault", IsDefault);
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").PostAddress(map), context, observer);
    }

    /**
     * 删除收货地址
     *
     * @param id
     * @param context
     * @param observer
     * @return
     */
    public static Subscription DelAddress(String id, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("id", id);
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").DelAddress(map), context, observer);
    }

    /**
     * 修改收货地址
     *
     * @param RealName
     * @param TelPhone
     * @param AreaAddress
     * @param Address
     * @param IsDefault
     * @param context
     * @param observer
     * @return
     */
    public static Subscription FixAddress(String id, String RealName, String TelPhone, String AreaAddress, String Address, String IsDefault, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("Id", id);
        map.put("RealName", RealName);
        map.put("TelPhone", TelPhone);
        map.put("AreaAddress", AreaAddress);
        map.put("Address", Address);
        map.put("IsDefault", IsDefault);
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").FixAddress(map), context, observer);
    }


    /**
     * 修改头像
     *
     * @param WxHeadImg
     * @param context
     * @param observer
     * @return
     */
    public static Subscription FixHeardImg(String WxHeadImg, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("WxHeadImg", WxHeadImg);
        map.put("Id", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").FixHeardImg(map), context, observer);
    }

    /**
     * 用户提交反馈
     *
     * @param Starlevel
     * @param img
     * @param comment
     * @param context
     * @param observer
     * @return
     */
    public static Subscription FeedSuggestion(String Starlevel, String img, String comment, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("Starlevel", Starlevel);
        if (!StrUtils.isEmpty(img)) {

            map.put("img", img);
        }
        map.put("comment", comment);
        map.put("Id", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").FeedSuggestion(map), context, observer);
    }

    /**
     * 用户修改密码
     *
     * @param oldpwd
     * @param newpwd
     * @param context
     * @param observer
     * @return
     */
    public static Subscription FixPsw(String oldpwd, String newpwd, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("oldpwd", oldpwd);
        map.put("newpwd", newpwd);
        map.put("Id", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").FixPsw(map), context, observer);
    }

    /**
     * 用户修改支付密码
     *
     * @param Payment
     * @param context
     * @param observer
     * @return
     */
    public static Subscription FixPayPsw(String Payment, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("Payment", Payment);
        map.put("Id", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").FixPayPsw(map), context, observer);
    }

    /**
     * 获取订单列表
     *
     * @param type     订单状态 0 全部  1待付款  2待发货  3 待收货  4 待评论 5 完成  6 关闭
     * @param index
     * @param size
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetOrderList(int index, int size, int type, Context context, NetSubscriber<BaseResultBean<List<MyOrderBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("index", index);
        map.put("size", size);
        map.put("type", type);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetOrderList(map), context, observer);
    }

    /**
     * 获取购物车信息
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetBuyCar(Context context, NetSubscriber<BaseResultBean<List<OrderDataBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("uid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetBuyCar(map), context, observer);
    }


    /**
     * 提醒发货
     *
     * @param touid
     * @param orderid
     * @param Title
     * @param Content
     * @param context
     * @param observer
     * @return
     */
    public static Subscription PostMsg(String touid, String orderid, String Title, String Content, Context context, NetSubscriber<BaseResultBean<List<OrderDataBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("touid", touid);
        map.put("orderid", orderid);
        if (!StrUtils.isEmpty(Title)) {
            map.put("Title", Title);
        }
        if (!StrUtils.isEmpty(Content)) {
            map.put("Content", Content);
        }
        map.put("fromuid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").PostMsg(map), context, observer);
    }

    /**
     * 确认收货
     *
     * @param orderid
     * @param context
     * @param observer
     * @return
     */
    public static Subscription ComfireGet(String orderid, Context context, NetSubscriber<BaseResultBean<List<OrderDataBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderid", orderid);
        return doRequest1(RetrofitProxy.getApiService(context, "").ComfireGet(map), context, observer);
    }

    /**
     * 取消订单
     *
     * @param orderid
     * @param context
     * @param observer
     * @return
     */
    public static Subscription CancelOrder(String orderid, Context context, NetSubscriber<BaseResultBean<List<OrderDataBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderid", orderid);
        return doRequest1(RetrofitProxy.getApiService(context, "").CancelOrder(map), context, observer);
    }


    /**
     * 获取商品详情
     *
     * @param orderid
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetOrderDetail(String orderid, Context context, NetSubscriber<BaseResultBean<OrderDetailsBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderid", orderid);
        return doRequest1(RetrofitProxy.getApiService(context, "").GetOrderDetail(map), context, observer);
    }

    /**
     * 获取售后订单列表
     *
     * @param index
     * @param size
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetRefundOrderList(int index, int size, Context context, NetSubscriber<BaseResultBean<List<RefundOrderBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("index", index);
        map.put("size", size);
        map.put("uid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetRefundOrderList(map), context, observer);
    }


    /**
     * 获取售后订单详情
     *
     * @param orderno
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetRefundOrderDetail(String orderno, Context context, NetSubscriber<BaseResultBean<RefundOrderBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderno", orderno);
        map.put("uid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetRefundOrderDetail(map), context, observer);
    }

    /**
     * 获取用户资金信息
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetMoney(Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();

        map.put("uid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetMoney(map), context, observer);
    }

    /**
     * 获取羊毛列表
     *
     * @param type
     * @param index
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetHymList(int type, int index, Context context, NetSubscriber<BaseResultBean<List<WoolBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("type", type);
        map.put("index", index);
        map.put("size", 10);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetHymList(map), context, observer);
    }


    /**
     * 获取羊毛详情
     *
     * @param id
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetHymDetails(int id, Context context, NetSubscriber<BaseResultBean<WoolBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("id", id);
        return doRequest1(RetrofitProxy.getApiService(context, "").GetHymDetails(map), context, observer);
    }

    /**
     * 获取薅羊毛id
     *
     * @param id
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetHymOrder(String id, Context context, NetSubscriber<BaseResultBean<List<WoolBean.HymBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderno", id);
        return doRequest1(RetrofitProxy.getApiService(context, "").GetHymOrder(map), context, observer);
    }

    /**
     * 提交评论
     *
     * @param OrderNo
     * @param GoodsId
     * @param Starlevel
     * @param img
     * @param comment
     * @param context
     * @param observer
     * @return
     */
    public static Subscription PostComment(String OrderNo, String GoodsId, String Starlevel, String img, String comment, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("OrderNo", OrderNo);
        map.put("GoodsId", GoodsId);
        map.put("Starlevel", Starlevel);
        if (!StrUtils.isEmpty(img)) {
            map.put("img", img);
        }
        map.put("comment", comment);
        map.put("UId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").PostComment(map), context, observer);
    }

    /**
     * 获取商铺详情
     *
     * @param storeid
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetShopInfo(String storeid, Context context, NetSubscriber<BaseResultBean<ShopDetailsBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("storeid", storeid);
        if (!StrUtils.isEmpty(PartyApp.getAppComponent().getDataManager().getId() + "")) {
            map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        }
        return doRequest1(RetrofitProxy.getApiService(context, "").GetShopInfo(map), context, observer);
    }

    /**
     * 获取店铺商品
     *
     * @param storeid
     * @param index
     * @param order
     * @param sort
     * @param goodsname
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetShopProduct(String storeid, int index, int order, int sort, String goodsname, Context context, NetSubscriber<BaseResultBean<List<GoodsBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("storeid", storeid);
        map.put("index", index);
        map.put("order", order);
        map.put("sort", sort);
        if (StrUtils.isEmpty(goodsname)) {
            map.put("goodsname", "");
        } else {
            map.put("goodsname", goodsname);
        }
        map.put("size", 10);
        if (!StrUtils.isEmpty(PartyApp.getAppComponent().getDataManager().getId() + "")) {
            map.put("UId", PartyApp.getAppComponent().getDataManager().getId());
        }
        return doRequest1(RetrofitProxy.getApiService(context, "").GetShopProduct(map), context, observer);
    }

    /**
     * 关注/取消关注店铺
     *
     * @param shopid
     * @param context
     * @param observer
     * @return
     */
    public static Subscription FollowShop(String shopid, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("shopid", shopid);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId() + "");
        return doRequest1(RetrofitProxy.getApiService(context, "").FollowShop(map), context, observer);
    }

    /**
     * 获取收商品铺列表
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetProductFollow(int index, Context context, NetSubscriber<BaseResultBean<List<ProductFollowBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("size", 20);
        map.put("index", index);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetProductFollow(map), context, observer);
    }

    /**
     * 删除收藏的商品
     *
     * @param ids
     * @param context
     * @param observer
     * @return
     */

    public static Subscription DeleteProductFollow(String ids, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("ids", ids);
        return doRequest1(RetrofitProxy.getApiService(context, "").DeleteProductFollow(map), context, observer);
    }


    /**
     * 获取店铺关注列表
     *
     * @param index
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetShopFollow(int index, Context context, NetSubscriber<BaseResultBean<List<ShopBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("size", 20);
        map.put("index", index);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetShopFollow(map), context, observer);
    }

    /**
     * 删除店铺关注
     *
     * @param ids
     * @param context
     * @param observer
     * @return
     */
    public static Subscription DeleteShopFollow(String ids, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("ids", ids);
        return doRequest1(RetrofitProxy.getApiService(context, "").DeleteShopFollow(map), context, observer);
    }


    /**
     * 获取浏览历史列表
     *
     * @param index
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetBrowsHistory(int index, Context context, NetSubscriber<BaseResultBean<List<GoodsBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("size", 15);
        map.put("index", index);
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.getApiService(context, "").GetBrowsHistory(map), context, observer);
    }

    /**
     * 删除浏览历史记录
     *
     * @param ids
     * @param context
     * @param observer
     * @return
     */
    public static Subscription DeleteBrowsHistory(String ids, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("ids", ids);
        return doRequest1(RetrofitProxy.getApiService(context, "").DeleteBrowsHistory(map), context, observer);
    }

    /**
     * 删除购物车商品
     *
     * @param cartids
     * @param context
     * @param observer
     * @return
     */
    public static Subscription DeleteCarGoods(String cartids, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("cartids", cartids);
//        RequestBody body = new
//        RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(bean))?
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        DeleteCarGoods(map),
                context, observer);
    }

    /**
     * 购物车商品增减操作
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription UpdataGoodsNum(int Id, int num, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("Id", Id);
        map.put("num", num);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        UpdataGoodsNum(map),
                context, observer);
    }

    /**
     * 获取我的卡包信息
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetWalletInfo(Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("uid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetWalletInfo(map),
                context, observer);
    }

    /**
     * 积分明细
     *
     * @param index
     * @param context
     * @param observer
     * @return
     */
    public static Subscription Getintegrallog(int index, Context context, NetSubscriber<BaseResultBean<List<AccountDetailsBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("size", 20);
        map.put("index", index);
        map.put("uid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        Getintegrallog(map),
                context, observer);
    }

    /**
     * 待返金额明细
     *
     * @param index
     * @param context
     * @param observer
     * @return
     */
    public static Subscription Getbargainuserlog(int index, Context context, NetSubscriber<BaseResultBean<List<AccountDetailsBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("size", 20);
        map.put("index", index);
        map.put("type", 0);
        map.put("uid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        Getbargainuserlog(map),
                context, observer);
    }

    /**
     * 获取资金明细详情
     *
     * @param logId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetSupplierMoneyDetails(String logId, Context context, NetSubscriber<BaseResultBean<WithdrawDetailsBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("logId", logId);

        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .GetSupplierMoneyDetails(map),
                context, observer);
    }

    /**
     * 商家可预订基本信息
     *
     * @param SupplierId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetSubscribeInfo(int SupplierId, Context context, NetSubscriber<BaseResultBean<SubscribeInfoBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("SupplierId", SupplierId);
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .GetSubscribeInfo(map),
                context, observer);
    }

    /**
     * 提交预定订单
     *
     * @param SupplierId
     * @param AdultCount
     * @param ChildCount
     * @param SeatType
     * @param DinnerTime
     * @param context
     * @param observer
     * @return
     */
    public static Subscription PostSubscribeInfo(int SupplierId, int AdultCount, int ChildCount, int SeatType, String DinnerTime, Context context, NetSubscriber<BaseResultBean<Integer>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("SupplierId", SupplierId);
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("AdultCount", AdultCount);
        map.put("ChildCount", ChildCount);
        map.put("SeatType", SeatType);
        map.put("DinnerTime", DinnerTime);
        map.put("Name", "李四");
        map.put("Phone", "13723890933");
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .PostSubscribeInfo(map),
                context, observer);
    }

    /**
     * 获取预定订单信息
     *
     * @param applyId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetSubscribeApplyInfo(int applyId, Context context, NetSubscriber<BaseResultBean<SubscribeApplyInfoBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("applyId", applyId);

        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .GetSubscribeApplyInfo(map),
                context, observer);
    }

    /**
     * 预定订单提交
     *
     * @param map
     * @param context
     * @param observer
     * @return
     */
    public static Subscription SaveOrder(Map<String, Object> map, Context context, NetSubscriber<BaseResultBean> observer) {
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .SaveOrder(map),
                context, observer);
    }

    /**
     * 预定订单列表获取
     *
     * @param SupplierId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetPredeterminerList(int SupplierId, Context context, NetSubscriber<BaseResultBean<List<FoodPredeterminelistBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        map.put("SupplierId", SupplierId);
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .GetPredeterminerList(map),
                context, observer);
    }

    /**
     * 预定订单取消
     *
     * @param applyId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription CanclePredeterminerOrder(int applyId, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("applyId", applyId);
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .CanclePredeterminerOrder(map),
                context, observer);
    }

    /**
     * 更改预订单（菜品的增减）
     *
     * @param OrderNo
     * @param isComfirm
     * @param OITEMS
     * @param context
     * @param observer
     * @return
     */
    public static Subscription ChangePredeterminerOrder(String OrderNo, int isComfirm, List<FoodGoodsCommitBean> OITEMS, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("OrderNo", OrderNo);
        map.put("isComfirm", isComfirm);//1为商家和发起人本人添加，无需确认，直接进入订单0为分享出去的好友参与添加，需发起人和商家确认
        map.put("OITEMS", OITEMS);
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .ChangePredeterminerOrder(map),
                context, observer);
    }

    /**
     * 预定订单优惠劵更改
     *
     * @param OrderNo
     * @param couponid
     * @param context
     * @param observer
     * @return
     */
    public static Subscription CouponsChange(String OrderNo, int couponid, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderno", OrderNo);
        map.put("couponid", couponid);
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .CouponsChange(map),
                context, observer);
    }

    /**
     * 获取客服数据
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetServerTel(Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetServerTel(map),
                context, observer);
    }

    /**
     * 获取区县资料
     *
     * @param name
     * @param context
     * @param observer
     * @return
     */
    public static Subscription Getareas(String name, Context context, NetSubscriber<BaseResultBean<List<CityBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        if (!StrUtils.isEmpty(name)) {
            map.put("name", name);
        }
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        Getareas(map),
                context, observer);
    }

    /**
     * 商家入驻获取短信验证码
     *
     * @param PhoneNumber
     * @param context
     * @param observer
     * @return
     */
    public static Subscription PostSms(String PhoneNumber, Context context, NetSubscriber<BaseResultBean<List<CityBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("PhoneNumber", PhoneNumber);
        map.put("Type", 1);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        PostSms(map),
                context, observer);
    }

    /**
     * 商家入驻登录
     *
     * @param PhoneNumber
     * @param Code
     * @param context
     * @param observer
     * @return
     */
    public static Subscription MerchantLogin(String PhoneNumber, String Code, Context context, NetSubscriber<BaseResultBean<List<CityBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("PhoneNumber", PhoneNumber);
        map.put("Code", Code);
        map.put("Type", 1);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        MerchantLogin(map),
                context, observer);
    }

    /**
     * 当地入驻
     *
     * @param map
     * @param context
     * @param observer
     * @return
     */
    public static Subscription MerchantLocalEnter(Map<String, Object> map, Context context, NetSubscriber<BaseResultBean> observer) {
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        MerchantLocalEnter(map),
                context, observer);
    }

    /**
     * 个人入驻
     *
     * @param map
     * @param context
     * @param observer
     * @return
     */
    public static Subscription MerchantPersonEnter(Map<String, Object> map, Context context, NetSubscriber<BaseResultBean> observer) {
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        MerchantPersonEnter(map),
                context, observer);
    }

    /**
     * 企业入驻
     *
     * @param map
     * @param context
     * @param observer
     * @return
     */
    public static Subscription MerchantCompanyEntry(Map<String, Object> map, Context context, NetSubscriber<BaseResultBean> observer) {
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        MerchantCompanyEntry(map),
                context, observer);
    }

    /**
     * 获取协议
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetAgreementList(Context context, NetSubscriber<BaseResultBean<List<AgreementBean>>> observer) {
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetAgreementList(),
                context, observer);
    }

    /**
     * 获取主营类目
     *
     * @param type
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetShopType(String type, Context context, NetSubscriber<BaseResultBean<List<StoreClassficationBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("type", type);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetShopType(map),
                context, observer);
    }

    /**
     * @param context
     * @param observer
     * @return
     */
    public static Subscription wxbind(String openid, String account, String code, int typeCode, Context context, NetSubscriber<BaseResultBean<WechatBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("openid", openid);
        map.put("phone", account);
        map.put("code", code);
        map.put("typecode", typeCode);
//        map.put()
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        wxbind(map),
                context, observer);
    }

    /**
     * 关注超市
     *
     * @param supplierId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription FavoriteShop(String supplierId, Context context, NetSubscriber<BaseResultBean<String>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("supplierId", supplierId);
        map.put("userId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        FavoriteShop(map),
                context, observer);
    }

    /**
     * 商超士多 提交订单
     *
     * @param ArriveTime
     * @param Phone
     * @param SupplierId
     * @param Balance
     * @param OrderPrice
     * @param Remark
     * @param context
     * @param observer
     * @return
     */
    public static Subscription CommitOrder(int CouponId, String ArriveTime, String Phone, String SupplierId, String Balance, String OrderPrice, String Remark, List<Map<String, Object>> CommTenantIdList, Context context, NetSubscriber<BaseResultBean<String>> observer) {
        Map<String, Object> map = new TreeMap<>();
        if (!StrUtils.isEmpty(Balance)) {
            map.put("Balance", Balance);
        } else {
            map.put("Balance", "");
        }
        if (!StrUtils.isEmpty(Remark)) {
            map.put("Remark", Remark);
        } else {
            map.put("Remark", "");
        }
        map.put("ArriveTime", ArriveTime);
        map.put("Phone", Phone);
        map.put("SupplierId", SupplierId);
        map.put("OrderPrice", OrderPrice);
        map.put("CommTenantIdList", CommTenantIdList);
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        if (CouponId != 0) {
            map.put("CouponId", CouponId);
        }
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        CommitOrder(map),
                context, observer);
    }

    /**
     * 获取商超士多详情页
     *
     * @param goodsId
     * @param PageIndex
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetGoodsDetail(Long goodsId, int PageIndex, Context context, NetSubscriber<BaseResultBean<SuperMarkGoodsBean>> observer) {
        Map<String, Object> map = new TreeMap<>();

        if (PartyApp.getAppComponent().getDataManager().getId() != 0) {
            map.put("userid", PartyApp.getAppComponent().getDataManager().getId());
        }
        map.put("goodsId", goodsId);
        map.put("PageIndex", PageIndex);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetGoodsDetail(map),
                context, observer);
    }

    /**
     * 获取商家列表
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetSuperMarketList(int PageIndex, String addressLevel, String addressId, String Longitude, String Latitude, int type, Context context, NetSubscriber<BaseResultBean<List<SuperMarketBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("PageIndex", PageIndex);
        map.put("PageSize", 10);
//        map.put("Provinces", Provinces);
//        map.put("City", City);
//        map.put("Areas", Areas);
        map.put("addressLevel", addressLevel);
        map.put("addressId", addressId);
        map.put("Longitude", Longitude);
        map.put("Latitude", Latitude);
        map.put("shopTypeId", type);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetSuperMarketList(map),
                context, observer);
    }

    /**
     * 获取商超士多商品列表
     *
     * @param SellerId
     * @param Condition
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetSuperMarketCommTenant(int SellerId, String Condition, Context context, NetSubscriber<BaseResultBean<SupplierBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        if (!StrUtils.isEmpty(PartyApp.getAppComponent().getDataManager().getId() + "")) {
            map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        }
        map.put("SupplierId", SellerId);
//        map.put("Sort", Sort);
        if (!StrUtils.isEmpty(Condition)) {
            map.put("Condition", Condition);
        }
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetSuperMarketCommTenant(map),
                context, observer);
    }

    public static Subscription GetSuperMarketCommTenant1(int SellerId, String Condition, Context context, NetSubscriber<BaseResultBean<SupplierBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        if (!StrUtils.isEmpty(PartyApp.getAppComponent().getDataManager().getId() + "")) {
            map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        }
        map.put("SupplierId", SellerId);
//        map.put("Sort", Sort);
        if (!StrUtils.isEmpty(Condition)) {
            map.put("Condition", Condition);
        }
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetSuperMarketCommTenant1(map),
                context, observer);
    }


    /**
     * 获取banner数据
     *
     * @param area
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetBannerData(String area, Context context, NetSubscriber<BaseResultBean<BannerBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        if (!"全国".equals(area)) {
            map.put("area", area);
        }

        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetBannerData(WarpGetParams(context, "webapi/local/getld?", map)),
                context, observer);
    }

    /**
     * 获取当地推荐商品
     *
     * @param index
     * @param area
     * @param context
     * @param observer
     * @return
     */
    public static Subscription Localgoodsdata(int index, String area, String name, Context context, NetSubscriber<BaseResultBean<List<MainProcuctBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("area", area);
        map.put("index", index);
        map.put("size", 24);
        if (!StrUtils.isEmpty(name)) {
            map.put("name", name);
        }
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        Localgoodsdata(map),
                context, observer);
    }

    /**
     * 全国商品
     *
     * @param index
     * @param name
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetData(int index, String name, Context context, NetSubscriber<BaseResultBean<List<MainProcuctBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("index", index);
        map.put("size", 24);
        if (!StrUtils.isEmpty(name)) {
            map.put("name", name);
        }
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetData(map),
                context, observer);
    }

    /**
     * 下载区县资料
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetAreas(Context context, NetSubscriber<BaseResultBean<List<CityBean>>> observer) {
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetAreas(),
                context, observer);
    }

    /**
     * 获取发现数据
     *
     * @param index
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetDiscover(int index, Context context, NetSubscriber<BaseResultBean<List<DiscoverBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("index", index);
        map.put("size", 10);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetDiscover(map),
                context, observer);
    }

    /**
     * 获取个人信息
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetPersonInfo(Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("uid", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetPersonInfo(map),
                context, observer);
    }


    /**
     * 搜索
     *
     * @param index
     * @param keyword
     * @param context
     * @param observer
     * @return
     */
    public static Subscription Search(int index, String keyword, int type, int AreaId, Context context, NetSubscriber<BaseResultBean<List<MainProcuctBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("Index", index);
        map.put("size", 12);
        if (!StrUtils.isEmpty(keyword)) {
            map.put("keyword", keyword);
        }
        map.put("AreaId", AreaId);
        map.put("type", type);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        Search(map),
                context, observer);
    }

    /**
     * 获取抽盘数据
     *
     * @param context
     * @param observer
     * @return
     */

    public static Subscription GetPrizePool(Context context, NetSubscriber<BaseResultBean<TravelBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", PartyApp.getAppComponent().getDataManager().getId());
        if ("全国".equals(SPUtils.getInstance(context).getString(Contans.LAST_CITY_ID))) {
            map.put("areaId", "0");
        } else {
            map.put("areaId", SPUtils.getInstance(context).getString(Contans.LAST_CITY_ID));
        }
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetPrizePool(map),
                context, observer);
    }

    /**
     * 获取抽奖结果
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription LuckyDraw(Context context, NetSubscriber<BaseResultBean<TravelLuckyBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", PartyApp.getAppComponent().getDataManager().getId());
        if ("全国".equals(SPUtils.getInstance(context).getString(Contans.LAST_CITY_ID))) {
            map.put("areaId", "");
        } else {
            map.put("areaId", SPUtils.getInstance(context).getString(Contans.LAST_CITY_ID));
        }
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        LuckyDraw(map),
                context, observer);
    }

    /**
     * 获取全部中奖记录
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetWinningLog(Context context, NetSubscriber<BaseResultBean<List<WinningLogBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetWinningLog(map),
                context, observer);
    }


    /**
     * 获取我的中奖记录
     *
     * @param pageIndex
     * @param Status
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetWinningLogForUser(int pageIndex, int Status, Context context, NetSubscriber<BaseResultBean<List<WinningLogUserBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 20);
        map.put("Status", Status);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetWinningLogForUser(map),
                context, observer);
    }

    /**
     * 获取抽奖详情
     *
     * @param logId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetPrizeLogDetail(int logId, Context context, NetSubscriber<BaseResultBean<WinningDetailsBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("logId", logId);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetPrizeLogDetail(map),
                context, observer);
    }

    /**
     * 获取店铺优惠券
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetSupplierCoupons(int storeId, Context context, NetSubscriber<BaseResultBean<List<CouponsBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
//        map.put("SupplierId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("SupplierId", storeId);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetSupplierCoupons(map),
                context, observer);
    }

    /**
     * 获取我的优惠券
     *
     * @param usestate
     * @param pageIndex
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetUserCouponsList(int usestate, int pageIndex, Context context, NetSubscriber<BaseResultBean<MyCouponsBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("UserID", PartyApp.getAppComponent().getDataManager().getId());
        map.put("usestate", usestate);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 20);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetUserCouponsList(map),
                context, observer);
    }

    /**
     * 领取优惠券
     *
     * @param CouponId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription ReceiveCoupons(int CouponId, int SupplierId, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
//        map.put("SupplierId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("CouponId", CouponId);
        map.put("SupplierId", SupplierId);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        ReceiveCoupons(map),
                context, observer);
    }

    /**
     * 获取用户在店铺的优惠券
     *
     * @param SupplierId
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetUseCoupon(int SupplierId, Context context, NetSubscriber<BaseResultBean<List<CouponsBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("SupplierId", SupplierId);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetUseCoupon(map),
                context, observer);
    }

    /**
     * 获取店铺banner和商品推荐
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription BannerGetList(int id, Context context, NetSubscriber<BaseResultBean<StoreBannerBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
//        map.put("SupplierId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("supplierid", id);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        BannerGetList(map),
                context, observer);
    }

    /**
     * 店铺banner广告点击次数回调
     *
     * @param id
     * @param context
     * @param observer
     * @return
     */
    public static Subscription BannerClick(int id, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("id", id);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        BannerClick(map),
                context, observer);
    }

    /**
     * 获取活动类型
     *
     * @param orderNo
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetHymOrQlx(String orderNo, Context context, NetSubscriber<BaseResultBean<ShakyBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("orderNo", orderNo);
        return doRequest1(RetrofitProxy.
                        getApiService(context, "").
                        GetHymOrQlx(map),
                context, observer);
    }


    /**------------------------------账号资金----------------------------------------------**/

    /**
     * 获取资金
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetAccountMoney(Context context, NetSubscriber<BaseResultBean<MoneyBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", PartyApp.getAppComponent().getDataManager().getId());

        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .GetAccountMoney(map),
                context, observer);
    }

    /**
     * 获取银行卡信息
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetSupplierBankCards(Context context, NetSubscriber<BaseResultBean<List<MyBankBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .GetSupplierBankCards(map),
                context, observer);
    }

    /**
     * 删除银行卡
     *
     * @param BankCardIds
     * @param context
     * @param observer
     * @return
     */
    public static Subscription DeleteSupplierBankCard(int BankCardIds, Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
//        map.put("supplierId", DBManager.getInstance(context).getUseId());
        map.put("BankCardIds", BankCardIds);
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .DeleteSupplierBankCard(map),
                context, observer);
    }

    /**
     * 获取银行接口
     *
     * @param BankCardIds
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetBanks(String BankCardIds, Context context, NetSubscriber<BaseResultBean<List<BanksBean>>> observer) {
        Map<String, Object> map = new TreeMap<>();
//        map.put("supplierId", DBManager.getInstance(context).getUseId());
        if (!StrUtils.isEmpty(BankCardIds)) {
            map.put("id", BankCardIds);
        }
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .GetBanks(map),
                context, observer);
    }

    /**
     * 商户添加银行卡
     *
     * @param map
     * @param context
     * @param observer
     * @return
     */
    public static Subscription AddSupplierBankCard(Map<String, Object> map, Context context, NetSubscriber<BaseResultBean> observer) {
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .AddSupplierBankCard(map),
                context, observer);
    }

    /**
     * 提现
     *
     * @param map
     * @param context
     * @param observer
     * @return
     */
    public static Subscription Withdrawal(Map<String, Object> map, Context context, NetSubscriber<BaseResultBean> observer) {
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .Withdrawal(map),
                context, observer);
    }

    /**
     * 获取资金明细
     *
     * @param data
     * @param type     默认0 0全部 1收入 2支出 3提现
     * @param index
     * @param context
     * @param observer
     * @return
     */
    public static Subscription GetSupplierMoneyLog(String data, int type, int index,
                                                   Context context, NetSubscriber<BaseResultBean<WithdrawBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", PartyApp.getAppComponent().getDataManager().getId());
        map.put("date", data);
        map.put("type", type);
        map.put("pageIndex", index);
        map.put("pageSize", 20);
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .GetSupplierMoneyLog(map),
                context, observer);
    }

    /**
     * 判断用户是否可以领取红包
     *
     * @param context
     * @param observer
     * @return
     */
    public static Subscription getRedEnvelopeType(Context context, NetSubscriber<BaseResultBean> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .getRedPacketType(map),
                context, observer);
    }


    /**
     * 领取礼包
     *
     * @param addressID 区县ID
     * @param way       礼包类型 int 可不传 4平台 3全国 2供应商指定商品 1本地
     * @param context
     * @param observer
     * @return
     */
    public static Subscription getRedPacket(NetSubscriber<BaseResultBean<RedPacketBean>> observer) {
        return getRedPacket(-1, observer);
    }

    public static Subscription getRedPacket(int way, NetSubscriber<BaseResultBean<RedPacketBean>> observer) {
        Map<String, Object> map = new TreeMap<>();
        map.put("UserId", PartyApp.getAppComponent().getDataManager().getId());
        String addressID = SPUtils.getInstance(observer.getContext()).getString(Contans.CITY_ID);
        if (!TextUtils.isEmpty(addressID)) {
            map.put("AddressID", Integer.parseInt(addressID));
        }
        if (way != -1) {
            map.put("Way", way);
        }
        Logger.i("red value map",map.toString());
        return doRequest1(RetrofitProxy
                        .getApiService(observer.getContext(), "")
                        .getRedPacket(map),
                observer.getContext(), observer);
    }


    /**
     * 获取优惠券商品列表
     * @param context
     * @param observer
     * @return
     */
    public static Subscription getCouponsGoodsList(Context context,Map<String, Object> map, NetSubscriber<BaseResultBean<CouponsGoodsBean>> observer) {
        return doRequest1(RetrofitProxy
                        .getApiService(context, "")
                        .getCouponsGoodsList(map),
                context, observer);
    }

    /**
     * 包装get请求参数加密
     *
     * @param map
     * @return
     */

    static String parmas;

    public static String WarpGetParams(Context context, String url, Map<String, Object> map) {
        Gson gson = new Gson();
        String str = gson.toJson(map);
        String str1 = str.replace("{", "");
        String str2 = str1.replace("}", "");
        String str3 = str2.replace(",", "&");
        String str4 = str3.replace(":", "=");
        String str5 = str4.replace("\"", "");
        parmas = str5;
        try {
//            parmas = AESEncryption.encrypt(str5.toString());

            Log.e(HttpLogger.LOGKYE, str5.toString());
            Log.e(HttpLogger.LOGKYE, parmas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url + parmas;
    }

    /***
     * 包装请求后再发起请求
     *
     * @param observable 请求
     * @param context    上下文
     * @param observer   观察者回调
     * @param <T>
     * @return
     */

    private static <T> Subscription doRequest1(Observable observable, Context context, NetSubscriber<T> observer) {
        return observable
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new DealErroHttpFunc1(context, null))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * <p/>
     * 5001 返回的数据为null
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
//    private static class HttpFurtherProcessingFunc<T> implements Func1<BaseResultBean<T>, T> {
//
//        //代表自己写的测试数据
//        private BaseResultBean<T> testDate;
//
//        public HttpFurtherProcessingFunc(BaseResultBean<T> testDate) {
//            this.testDate = testDate;
//        }
//
//        @Override
//        public T call(BaseResultBean<T> httpResult) {
//            if (BuildConfig.DEBUG) {
//
//                if (testDate == null) {
//
//                    if (httpResult == null) {
//                        return (T) new BaseResultBean("测试数据和网络数据都为Null!", -99, "");
//                    } else {
////                        if (httpResult.Code != RequestApi.OK_CODE) {
////
////                            System.out.println("error:" + httpResult.Message + "code:" + httpResult.Code);
////
////                            return (T) new BaseResultBean(httpResult.Message, httpResult.Code, httpResult.Data);
////                        } else {
////                            return httpResult.Data == null ? (T) new BaseResultBean("请求成功!", httpResult.Code, httpResult.Data) : httpResult.Data;
////                        }
//                    }
//
//
//                } else {
//
////                    if (testDate.Code != RequestApi.OK_CODE) {
////                        return (T) new BaseResultBean(testDate.Message, testDate.Code, "");
////                    }
////                    return testDate.Data == null ? (T) new BaseResultBean("请求成功!", testDate.Code, "") : testDate.Data;
//                }
//
//            } else {
//
////                Release版本
//                if (null != httpResult) {
//                    if (httpResult.Code != RequestApi.OK_CODE) {
//                        return (T) new BaseResultBean(httpResult.Message, httpResult.Code, httpResult.Data);
//                    }
//                    return httpResult.Data;
//                } else {
//                    return (T) new BaseResultBean("请求出现错误(5001)!", -99, "");
//                }
//
//            }
//
//    }


    /**
     * 用来统一处理测试数据和错误处理
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private static class DealErroHttpFunc1<T extends BaseResultBean> implements Func1<Throwable, T> {

        private T testResult;
        private Context context;

        public DealErroHttpFunc1(Context context, T t) {
            testResult = t;
            this.context = context;
        }

        @Override
        public T call(Throwable throwable) {

            if (BuildConfig.DEBUG) {
//                Log.e("httpErro:", throwable.getMessage());
                //在开发模式下，测试数据默认无视网络情况
                return testResult == null ? createException(throwable) : testResult;
            } else {
                //Release版本
                return createException(throwable);
            }
        }

        private T createException(Throwable throwable) {

            if (!NetworkUtils.hasNetWork(context)) {
                if (throwable instanceof SocketTimeoutException) {
                    return (T) new BaseResultBean("您的网络不给力！请重试！", -99 + "", "");
                }
                if (throwable instanceof UnknownHostException || throwable instanceof HttpException) {
                    return (T) new BaseResultBean("在与服务器通讯过程中发生未知异常！", -99 + "", "");
                }
                return (T) new BaseResultBean(throwable.getMessage() + "", -99 + "", "");

            } else {
                return (T) new BaseResultBean("当前无法访问网络！", -99 + "", "");

            }

        }
    }

}
