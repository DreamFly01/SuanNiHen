package com.sg.cj.snh.model.response;


/**
 * author : ${CHENJIE}
 * created at  2018/10/26 16:10
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class HttpResponse<T> {

  private String code;
  private String msg;

  private T data;

  public T getResults() {
    return data;
  }

  public void setResults(T results) {
    this.data = results;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return msg;
  }

  public void setMessage(String message) {
    this.msg = message;
  }
}