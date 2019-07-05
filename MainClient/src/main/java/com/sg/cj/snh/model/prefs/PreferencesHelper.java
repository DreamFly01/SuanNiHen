package com.sg.cj.snh.model.prefs;


import java.util.List;
import java.util.Set;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 14:43
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public interface PreferencesHelper {
    //登陆相关
    int getId();

    void setId(int Id);

    int getGradeId();

    void setGradeId(int GradeId);

    String getWxNickName();

    void setWxNickName(String WxNickName);

    String getWxHeadImg();

    void setWxHeadImg(String WxHeadImg);

    String getUnionID();

    void setUnionID(String UnionID);

    String getPayment();

    void setPayment(String Payment);

    String getTel();

    void setTel(String Tel);

    float getBalanceOne();

    void setBalanceOne(float BalanceOne);

    float getIntegral();

    void setIntegral(float Integral);

    String getWxOpenId();

    void setWxOpenId(String WxOpenId);

    //登陆相关

    boolean getLaunchFirst();

    void setLaunchFirst(boolean isFirst);


    Set<String> getCookie();

    void setCookie(Set<String> cookies);

    String getCookieString();

    void setCookieString(String cookie);

    String getOpenid();

    void setOpenid(String openid);

    void cleanData();
}
