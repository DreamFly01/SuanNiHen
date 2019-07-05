package com.sg.cj.snh.model.response.main;


import com.sg.cj.snh.model.response.BaseResponse;

import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/12/1 16:37
 * e_mail : chenjie_goodboy@163.com
 * describle :26．本地推荐商品
 */
public class LocalgoodsdataResponse extends BaseResponse {

  /**
   * currentpage : 1
   * datacount : 5
   * pagesize : 2
   * data : [{"Id":2601,"Name":"西瓜","MarketNumber":4,"GoodsImg":"http://rs.chankor.com/api/GetResources/360599","MarketPrice":15,"SalesPrice":10,"DetailUrl":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=2601"},{"Id":2716,"Name":"柠檬","MarketNumber":2,"GoodsImg":"http://rs.chankor.com/api/GetResources/362629","MarketPrice":30,"SalesPrice":20,"DetailUrl":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=2716"}]
   */

  public int currentpage;
  public int datacount;
  public int pagesize;
  public List<DataBean> data;

  public static class DataBean {
    /**
     * Id : 2601
     * Name : 西瓜
     * MarketNumber : 4
     * GoodsImg : http://rs.chankor.com/api/GetResources/360599
     * MarketPrice : 15.0
     * SalesPrice : 10.0
     * DetailUrl : http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=2601
     */

    public int Id;
    public String Name;
    public int MarketNumber;
    public String GoodsImg;
    public double MarketPrice;
    public double SalesPrice;
    public String DetailUrl;
  }
}
