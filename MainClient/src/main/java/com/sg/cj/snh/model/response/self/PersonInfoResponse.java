package com.sg.cj.snh.model.response.self;


import com.sg.cj.snh.model.response.BaseResponse;

/**
 * author : ${CHENJIE}
 * created at  2018/11/8 09:58
 * e_mail : chenjie_goodboy@163.com
 * describle : 9．个人中心提示
 */
public class PersonInfoResponse extends BaseResponse{

  /**
   * topay : 0
   * tosendproduct : 0
   * toreceiveproduct : 0
   * toreview : 0
   * careshop : 0
   * caregoods : 0
   */

  //待付款订单数
  public int topay;
  //待发货订单数
  public int tosendproduct;
  //待收货订单数
  public int toreceiveproduct;
  //待评价订单数
  public int toreview;
  //关注的店铺数
  public int careshop;
  //关注的商品数
  public int caregoods;
}
