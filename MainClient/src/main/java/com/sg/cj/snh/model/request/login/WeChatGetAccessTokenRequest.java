package com.sg.cj.snh.model.request.login;


import com.sg.cj.snh.PartyApp;
import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.model.request.BaseRequest;

/**
 * author : ${CHENJIE}
 * created at  2018/12/4 21:07
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WeChatGetAccessTokenRequest extends BaseRequest {


  public WeChatGetAccessTokenRequest(String code) {
    this.code = code;
  }

  public String appid= Constants.WEIXIN_APP_ID;
  public String secret=Constants.WEIXIN_App_Secret;
  public String code;
  public String grant_type="authorization_code";


  @Override
  public String toString() {
    return "WeChatGetAccessTokenRequest{" +
        "appid='" + appid + '\'' +
        ", secret='" + secret + '\'' +
        ", code='" + code + '\'' +
        ", grant_type='" + grant_type + '\'' +
        '}';
  }
}
