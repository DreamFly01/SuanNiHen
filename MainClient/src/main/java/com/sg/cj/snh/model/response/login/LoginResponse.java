package com.sg.cj.snh.model.response.login;


import com.sg.cj.snh.model.response.BaseResponse;

/**
 * author : ${CHENJIE}
 * created at  2018/11/26 22:39
 * e_mail : chenjie_goodboy@163.com
 * describle : 短信登录返回
 */
public class LoginResponse {



    /**
     * Id : 738
     * GradeId : 1
     * WxNickName : 老板
     * WxHeadImg : http://shop.hnyunshang.com//Content/SNH_login/images/logo_2.png
     * UnionID : null
     * Payment : null
     * Tel : null
     * BalanceOne : 0.0
     * Integral : 0.0
     * WxOpenId : null
     */

    public int Id;
    public int GradeId;
    public String WxNickName;
    public String WxHeadImg;
    public String UnionID;
    public String Payment;
    public String Tel;
    public float BalanceOne;
    public float Integral;
    public String WxOpenId;

    public ImData nimResult;
    public class ImData{
        public String Token;
        public String Accid;
    }
}
