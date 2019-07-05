package com.sg.cj.snh.model.response.findgoods;


import com.sg.cj.snh.model.response.BaseResponse;

import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/11/29 08:56
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class FindGoodsResponse extends BaseResponse{

  /**
   * data : [{"Id":121,"GoodsUrl":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=2280","SupperId":119,"SupplierName":"家誉电器","SupplierImg":null,"StoreUrl":"http://shop.hnyunshang.com/Wx_User/Supplier/Index?storeid=119","CoverImg":"http://rs.chankor.com/api/GetResources/356960","Text":"科王垃圾处理器","Content":"http://shop.hnyunshang.com/Video/1541399096.mp4","Audit":0,"fbTime":"2018-08-11 16:06:09"},{"Id":124,"GoodsUrl":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5294","SupperId":37,"SupplierName":"Sdry 港风","SupplierImg":null,"StoreUrl":"http://shop.hnyunshang.com/Wx_User/Supplier/Index?storeid=37","CoverImg":"http://rs.chankor.com/api/GetResources/442323","Text":"模特","Content":"http://shop.hnyunshang.com/Video/1542950628.mp4","Audit":0,"fbTime":"2018-11-23 13:24:03"}]
   */

  public List<DataBean> data;

  public static class DataBean {
    /**
     * Id : 121
     * GoodsUrl : http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=2280
     * SupperId : 119
     * SupplierName : 家誉电器
     * SupplierImg : null
     * StoreUrl : http://shop.hnyunshang.com/Wx_User/Supplier/Index?storeid=119
     * CoverImg : http://rs.chankor.com/api/GetResources/356960
     * Text : 科王垃圾处理器
     * Content : http://shop.hnyunshang.com/Video/1541399096.mp4
     * Audit : 0
     * fbTime : 2018-08-11 16:06:09
     */

    public int Id;
    public String GoodsUrl;
    public int SupperId;
    public String SupplierName;
    public String SupplierImg;
    public String StoreUrl;
    public String CoverImg;
    public String Text;
    public String Content;
    public int Audit;
    public String fbTime;
  }
}
