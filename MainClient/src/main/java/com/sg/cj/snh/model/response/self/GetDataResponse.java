package com.sg.cj.snh.model.response.self;


import com.sg.cj.snh.model.response.BaseResponse;

import java.util.List;

/**
 * author : ${CHENJIE}
 * created at  2018/11/8 09:58
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class GetDataResponse extends BaseResponse{


  /**
   * code : 01
   * CurrentPage : 1
   * DataCount : 3539
   * PageSize : 2
   * data : [{"Id":5312,"Collating":0,"SupplierId":39,"Name":"棉袄男2018新款冬装宽松工装外套男韩版潮流bf短款棉衣面包服ins","ShortInfo":".","CategoryId":36,"LabelId":"-1","MarketNumber":0,"Weight":500,"CoverImgId":"http://rs.chankor.com/api/GetResources/442612","RollImages":"http://rs.chankor.com/api/GetResources/442613,http://rs.chankor.com/api/GetResources/442615,http://rs.chankor.com/api/GetResources/442616,http://rs.chankor.com/api/GetResources/442617,http://rs.chankor.com/api/GetResources/442618","MarketPrice":178,"SalesPrice":169,"CostPrice":145,"PV":0,"Inventory":1000,"WarningInventory":10,"Describe":null,"IsNorm":true,"IsPutaway":true,"IsOnSale":true,"IsSpecialGoods":true,"IsB":false,"UnitsTitle":"件","GoodsCode":"115","IsAuditing":1,"AuditReason":"","ExpressTemplateId":0,"Cheap":160,"CutDownTime":168,"LowPrice":0.3,"HighPrice":1,"IsBargainGoods":false,"IsLimitKill":0,"ActiviesId":null,"Precies":null,"joinNumber":null,"GetMaxNumber":null,"LimitAudit":null,"LimitReason":null,"CutDownPercentages":30,"CutDownCouponID":null,"ShareEnvelope":2,"EmipiricalVale":0,"DetailUrl":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5312"},{"Id":5308,"Collating":0,"SupplierId":92,"Name":"2018冬装新款韩版过膝棉服女中长款宽松bf加厚学生棉衣外套","ShortInfo":".","CategoryId":37,"LabelId":"-1","MarketNumber":0,"Weight":500,"CoverImgId":"http://rs.chankor.com/api/GetResources/442542","RollImages":"http://rs.chankor.com/api/GetResources/442543,http://rs.chankor.com/api/GetResources/442544,http://rs.chankor.com/api/GetResources/442545,http://rs.chankor.com/api/GetResources/442546","MarketPrice":289,"SalesPrice":268,"CostPrice":258,"PV":0,"Inventory":1000,"WarningInventory":10,"Describe":null,"IsNorm":true,"IsPutaway":true,"IsOnSale":true,"IsSpecialGoods":true,"IsB":false,"UnitsTitle":"件","GoodsCode":"003","IsAuditing":1,"AuditReason":"","ExpressTemplateId":0,"Cheap":268,"CutDownTime":168,"LowPrice":0,"HighPrice":0,"IsBargainGoods":false,"IsLimitKill":0,"ActiviesId":null,"Precies":null,"joinNumber":null,"GetMaxNumber":null,"LimitAudit":null,"LimitReason":null,"CutDownPercentages":30,"CutDownCouponID":null,"ShareEnvelope":2,"EmipiricalVale":268,"DetailUrl":"http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5308"}]
   */

  public int CurrentPage;
  public int DataCount;
  public int PageSize;
  public List<DataBean> data;

  public static class DataBean {
    /**
     * Id : 5312
     * Collating : 0
     * SupplierId : 39
     * Name : 棉袄男2018新款冬装宽松工装外套男韩版潮流bf短款棉衣面包服ins
     * ShortInfo : .
     * CategoryId : 36
     * LabelId : -1
     * MarketNumber : 0
     * Weight : 500.0
     * CoverImgId : http://rs.chankor.com/api/GetResources/442612
     * RollImages : http://rs.chankor.com/api/GetResources/442613,http://rs.chankor.com/api/GetResources/442615,http://rs.chankor.com/api/GetResources/442616,http://rs.chankor.com/api/GetResources/442617,http://rs.chankor.com/api/GetResources/442618
     * MarketPrice : 178.0
     * SalesPrice : 169.0
     * CostPrice : 145.0
     * PV : 0.0
     * Inventory : 1000
     * WarningInventory : 10
     * Describe : null
     * IsNorm : true
     * IsPutaway : true
     * IsOnSale : true
     * IsSpecialGoods : true
     * IsB : false
     * UnitsTitle : 件
     * GoodsCode : 115
     * IsAuditing : 1
     * AuditReason :
     * ExpressTemplateId : 0
     * Cheap : 160.0
     * CutDownTime : 168
     * LowPrice : 0.3
     * HighPrice : 1.0
     * IsBargainGoods : false
     * IsLimitKill : 0
     * ActiviesId : null
     * Precies : null
     * joinNumber : null
     * GetMaxNumber : null
     * LimitAudit : null
     * LimitReason : null
     * CutDownPercentages : 30.0
     * CutDownCouponID : null
     * ShareEnvelope : 2.0
     * EmipiricalVale : 0.0
     * DetailUrl : http://shop.hnyunshang.com/Wx_User/SNH_Goods/GoodsDetail?GoodsId=5312
     */

    public int Id;
    public int Collating;
    public int SupplierId;
    public String Name;
    public String ShortInfo;
    public int CategoryId;
    public String LabelId;
    public int MarketNumber;
    public double Weight;
    public String CoverImgId;
    public String RollImages;
    public double MarketPrice;
    public double SalesPrice;
    public double CostPrice;
    public double PV;
    public int Inventory;
    public int WarningInventory;
    public Object Describe;
    public boolean IsNorm;
    public boolean IsPutaway;
    public boolean IsOnSale;
    public boolean IsSpecialGoods;
    public boolean IsB;
    public String UnitsTitle;
    public String GoodsCode;
    public int IsAuditing;
    public String AuditReason;
    public int ExpressTemplateId;
    public double Cheap;
    public int CutDownTime;
    public double LowPrice;
    public double HighPrice;
    public boolean IsBargainGoods;
    public int IsLimitKill;
    public Object ActiviesId;
    public Object Precies;
    public Object joinNumber;
    public Object GetMaxNumber;
    public Object LimitAudit;
    public Object LimitReason;
    public double CutDownPercentages;
    public Object CutDownCouponID;
    public double ShareEnvelope;
    public double EmipiricalVale;
    public String DetailUrl;
  }
}
