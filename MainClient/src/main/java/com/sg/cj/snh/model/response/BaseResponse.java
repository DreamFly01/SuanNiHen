package com.sg.cj.snh.model.response;


/**
 * author : ${CHENJIE}
 * created at  2018/11/26 21:16
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class BaseResponse {
  private String code;
  private String msg;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
