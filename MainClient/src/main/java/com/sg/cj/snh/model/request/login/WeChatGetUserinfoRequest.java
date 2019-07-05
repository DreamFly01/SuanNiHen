package com.sg.cj.snh.model.request.login;


import com.sg.cj.snh.constants.Constants;
import com.sg.cj.snh.model.request.BaseRequest;

/**
 * author : ${CHENJIE}
 * created at  2018/12/4 21:07
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class WeChatGetUserinfoRequest extends BaseRequest {


  public WeChatGetUserinfoRequest(String access_token, String openid) {
    this.access_token = access_token;
    this.openid = openid;
  }

  public String access_token;
  public String openid;




}
