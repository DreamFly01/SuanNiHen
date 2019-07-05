package com.sg.cj.snh.model.request.shopcar;


import com.sg.cj.snh.model.request.BaseRequest;

/**
 * author : ${CHENJIE}
 * created at  2018/12/9 15:49
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class DeleteCartGoodsRequest extends BaseRequest {
  public DeleteCartGoodsRequest(String cartids) {
    this.cartids = cartids;
  }

  public String cartids;
}
