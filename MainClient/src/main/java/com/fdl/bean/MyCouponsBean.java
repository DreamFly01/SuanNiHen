package com.fdl.bean;

import com.fdl.activity.main.redPacket.bean.GoodsListBean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/10<p>
 * <p>changeTime：2019/5/10<p>
 * <p>version：1<p>
 */
public class MyCouponsBean {

   public int count;
   public List<ListBean> list;

   public static class ListBean {
      /**
       * CouponId : 145
       * ShopName : 芙蓉兴盛
       * Logo : https://shop.snihen.com:8448/api/Source/472720
       * CouponName : 商品满20减5
       * CouponWay : 1
       * ConditionValue : 20.0
       * CouponValue : 5.0
       * UseGroup : null
       * GoodsIds : null
       * UseState : 0
       * BeginDate : 2019-07-01 00:00:00:000
       * EndDate : 2019-08-31 00:00:00:000
       * UseTime :
       * IsNewPeopleReceive : 1
       * GoodsList : []
       */

      public int CouponId;
      public String ShopName;
      public String Logo;
      public String CouponName;
      public int CouponWay;
      public double ConditionValue;
      public double CouponValue;
      public String UseGroup;
      public String GoodsIds;
      public int UseState;
      public String BeginDate;
      public String EndDate;
      public String UseTime;
      public int IsNewPeopleReceive;
      public List<GoodsListBean> GoodsList;
      /**
       * SupplierId : 394
       * BusinessActivities : 1
       * ShopType : 6
       * ConditionValue : 4.0
       * CouponValue : 2.0
       * GoodsList : [{"Id":6192,"Name":"盼盼香蕉味酥 105g","ShortInfo":null,"Images":"https://shop.snihen.com:8448/api/Source/472502","SalesPrice":4}]
       */

      public int SupplierId;
      public String BusinessActivities;
      public String ShopType;
   }
}
