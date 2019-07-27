package com.fdl.utils;


import com.sg.cj.snh.BuildConfig;

/**
 * <p>desc：用户信息以及配置信息等常量<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/6<p>
 * <p>changeTime：2018/12/6<p>
 * <p>version：1<p>
 */
public class Contans {
    public static final String HOST_TEST = "http://test.snihen.com/api/";//测试环境
    public static final String HOST = "https://shop.snihen.com/api/";//正式环境
    public static final String T_HOST = "http://test.snihen.com:8089/api/";
    public static final String API_HOST = BuildConfig.SERVER_DEBUG ? HOST_TEST : HOST;
    public static boolean debug = true;
    //用户信息
    public static final String USER_INFO = "my_sp";
    public static final String LIST_BANNER_DATA = "bannerData";
    public static final String IM_ACCOUNT = "imAccount";
    public static final String IM_TOKEN = "imToken";
    public static final String DISTRICT = "district";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String CITY = "city";
    public static final String LOCAL_DISCRI = "localDis";
    public static final String LAST_CITY = "lastCity";
    public static final String CITY_ID = "cityId";
    public static final String CITY_LEVEL = "cityLevel";
    public static final String LAST_CITY_ID = "lastCityID";
    public static final String LATELY_CITY = "latelyCity";
    public static final String LATELY_CITY_ID = "latelyId";
    public static final String PROVINCE = "province";

    public static final String LEVEL = "level";
    public static final String ADDRESS_ID = "addressId";
    public static final String LATELY_LEVEL = "latelyLevel";
    public static final String LATELY_ADDRESS_ID = "latelyAddressId";

    static final String SP_COOKIES = "cookies";
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

    public static final String APK_SUFFIXS_STR = ".apk";
    //是否存在地址表
    public static final String ADDRESS = "haveAddress_1";
    public static final String IS_FRIST = "isFrist";

    public static final String BUGLY_ID = "068910702c";
}
