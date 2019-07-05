package com.sg.cj.snh.model.request.login;


import com.sg.cj.snh.model.request.BaseRequest;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 15:03
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class LoginRequest extends BaseRequest{


  public LoginRequest(String acount, String pwd) {
    this.acount = acount;
    this.pwd = pwd;
  }

  public String acount;
  public String pwd;


  @Override
  public String toString() {
    return "LoginRequest{" +
        "acount='" + acount + '\'' +
        ", pwd='" + pwd + '\'' +
        '}';
  }
}
