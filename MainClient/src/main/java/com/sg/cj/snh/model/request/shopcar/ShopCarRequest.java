package com.sg.cj.snh.model.request.shopcar;


import com.sg.cj.snh.model.request.BaseRequest;

/**
 * author : ${CHENJIE}
 * created at  2018/12/6 17:01
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class ShopCarRequest extends BaseRequest {

  public ShopCarRequest(String uid) {
    this.uid = uid;
  }

  public String uid;
}
