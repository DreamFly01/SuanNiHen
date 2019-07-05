package com.sg.cj.snh.model.request.login;


import com.sg.cj.snh.model.request.BaseRequest;
import com.sg.cj.snh.model.response.BaseResponse;

/**
 * author : ${CHENJIE}
 * created at  2018/11/27 08:38
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class RegisterRequest extends BaseRequest {


  public RegisterRequest(String account, String pwd, String nickname, String code) {
    this.acount = account;
    this.pwd = pwd;
    this.nickname = nickname;
    this.code = code;
  }

  public String acount;
  public String pwd;
  public String nickname;
  public String code;
}
