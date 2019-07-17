package com.sg.cj.snh.constants;


import com.fdl.utils.Contans;
import com.sg.cj.common.base.App;

import java.io.File;

/**
 * @author : chenjie
 * creat at 2018/11/26 08:39
 * @Description:
 */
public class Constants {
    public static final boolean TEST = true;
    public static final String SUCCESS_CODE = "01";
    public static final String ERROR_MSG = "请求出错";
    public static final String WEIXIN_APP_ID = "wx223ecb204beddad4";
    public static final String WEIXIN_App_Secret = "74ad3540ba046d74776784987667d072";
    //================= HTTP ====================
    public static final int LOAD_PAFE_SIZE = 20;
    // public static final String BASE_IP = "http://shop.hnyunshang.com/";
//  public static final String BASE_IP = "https://shop.snihen.com/";//正式环境
    public static final String BASE_IP = "http://test.snihen.com/";//测试环境
    public static final String BASE_HOST =Contans.API_HOST;
    //去支付：http://shop.hnyunshang.com/Wx_User/SNH_ShopOrders?CartIds=570%2C568&SupplierIds=93%2C
    public static final String QZF = BASE_IP + "Wx_User/SNH_ShopOrders?CartIds=@@&SupplierIds=##";
    //商品收藏：
    public static final String SPSC = BASE_IP + "Wx_User/Personal/goodsfavorite";
    //店铺关注：
    public static final String DPGZ = BASE_IP + "Wx_User/Personal/shopfavorite";
    //首页搜索：
    public static final String MAIN_SEARCH = BASE_IP + "Wx_User/SNH_MainPage/Search";
    //首页附近：
    public static final String MAIN_FJ = BASE_IP + "Wx_User/SNH_MainPage/Index";
    //首页更多：
    public static final String MAIN_GD = BASE_IP + "Wx_User/LocalPage/lcoalsubmissionGd";
    //个人设置：
    public static final String SETTING = BASE_IP + "Wx_User/Personal/updatePersonal";
    //退款售后：
    public static final String TKSH = BASE_IP + "Wx_User/ReturnOrder/orderlist";
    //历史浏览：
    public static final String LSLL = BASE_IP + "Wx_User/Personal/Commodityvisit";
    //全部订单：
    public static final String QBDD = BASE_IP + "Wx_User/Personal/Online";

    //待付款订单：
    public static final String DFKDD = BASE_IP + "Wx_User/Personal/Online?type=2";

    //待发货订单：
    public static final String DFHDD = BASE_IP + "Wx_User/Personal/Online?type=3";

    //待收货订单：
    public static final String DSHDD = BASE_IP + "Wx_User/Personal/Online?type=4";

    //待评价订单：
    public static final String DPJDD = BASE_IP + "Wx_User/Wx_User/Personal/Online?type=5";

    //薅羊毛：
    public static final String HYM = BASE_IP + "Wx_User/BargainS/Mybargain";

    //收货地址：
    public static final String SHDZ = BASE_IP + "Wx_User/Personal_Notice/address";

    //我的卡包：
    public static final String WDKB = BASE_IP + "Wx_User/personal_Bank/Balance";

    //优惠券：
    public static final String YHQ = BASE_IP + "Wx_User/Coupons/MyCoupons";

    //带你去旅行：
    public static final String DNQLX = BASE_IP + "Wx_User/Scratch";

    //客服：
    public static final String KF = BASE_IP + "Wx_User/hp_kf";

    //商家登录：
    public static final String SJDL = BASE_IP + "Wx_seller/Wx_SellerLogin/Index";

    //软件许可使用协议：
    public static final String SYXY = BASE_IP + "Wx_User/About/UseAgreement";

    //算你狠商城平台服务协议：
    public static final String FWXY = BASE_IP + "Wx_User/About/ServerAgreement";

    //法律声明和隐私权政策：
    public static final String YSZC = BASE_IP + "Wx_User/About/Secret";

    //商家入驻：
    public static final String SJRZ = BASE_IP + "MobileEnter/settled_phone";


    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";


    //================= PREFERENCE ====================

    public static final String SP_COOKIES = "cookies";
    public static final String SP_COOKIES_STRING = "cookies_string";
    public static final String OPENID = "openid";
    public static final String SP_LAUNCH_FIRST = "launch_first";
    public static final String SP_ID = "id";
    public static final String SP_GradeId = "GradeId";
    public static final String SP_WxNickName = "WxNickName";
    public static final String SP_WxHeadImg = "WxHeadImg";
    public static final String SP_UnionID = "UnionID";
    public static final String SP_Payment = "Payment";

    public static final String SP_Tel = "Tel";
    public static final String SP_BalanceOne = "BalanceOne";
    public static final String SP_Integral = "Integral";
    public static final String SP_WxOpenId = "WxOpenId";

}
