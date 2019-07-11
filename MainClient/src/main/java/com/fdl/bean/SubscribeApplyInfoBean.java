package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/7/1<p>
 * <p>changeTime：2019/7/1<p>
 * <p>version：1<p>
 */
public class SubscribeApplyInfoBean {
    public SupData SUP;
    public FoodData FOOD;
    public List<OrdersData> ORDERS;
    public Coupons COUPON;

    public class SupData {
        public String ShopName;
        public int StarLevel;
        public String Logo;
        public String ContactsTel;
        public String Province;
        public String City;
        public String Area;
        public String Address;

    }

    public class FoodData {
        public int IsPaid;
        public int SubscribePrice;
        public int AdultCount;
        public int ChildCount;
        public String DinnerTime;
        public int SeatType;
        public int Status;
        public String Phone;
        public String UName;
        public String UCode;
        public String SHAREURL;
        public String OrderNo;
        public String LDinnerTime;
    }
    public class Coupons{
        public int CouponId;
        public String CouponName;
        public double ConditionValue;
        public double CouponValue;
        public String CouponTypes;
        public int CouponWay;
    }
}
