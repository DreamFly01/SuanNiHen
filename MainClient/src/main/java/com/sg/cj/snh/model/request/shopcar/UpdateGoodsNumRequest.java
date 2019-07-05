package com.sg.cj.snh.model.request.shopcar;


import com.sg.cj.snh.model.request.BaseRequest;

/**
 * author : ${CHENJIE}
 * created at  2018/12/9 15:38
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class UpdateGoodsNumRequest extends BaseRequest {


  public UpdateGoodsNumRequest(int id, int num) {
    Id = id;
    this.num = num;
  }

  public int Id;
  public int num;
}
