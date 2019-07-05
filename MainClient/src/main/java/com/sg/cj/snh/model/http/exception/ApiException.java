package com.sg.cj.snh.model.http.exception;


/**
 * author : ${CHENJIE}
 * created at  2018/10/26 15:57
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class ApiException extends Exception{

  private String code;

  public ApiException(String msg) {
    super(msg);
  }

  public ApiException(String msg, String  code) {
    super(msg);
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}

