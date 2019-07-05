package com.sg.cj.snh.model.request.login;


import com.sg.cj.snh.model.request.BaseRequest;

/**
 * author : ${CHENJIE}
 * created at  2018/12/10 09:23
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WxauthRequest extends BaseRequest {

  public WxauthRequest(String openid) {
    this.openid = openid;
  }

  public String openid;
}
