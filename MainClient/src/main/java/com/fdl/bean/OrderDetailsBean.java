package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/7<p>
 * <p>changeTime：2019/1/7<p>
 * <p>version：1<p>
 */
public class OrderDetailsBean {
    public int Id;
    public int UserId;
    public String ToTalOrderNo;
    public String OrderNo;
    public int OrderState;
    public int PayType;
    public int PayState;
    public double GoodsTotalPrice;
    public double TotalPrice;
    public double ExpressPrice;
    public String LeaveWord;
    public double BalanceOne;
    public double Integral;
    public double WeixinMoney;
    public String ReceiverName;
    public String ReceiverTelPhone;
    public String ReceiverAreaAddress;
    public String ReceiverAddress;
    public List<GoodsBean> goodslist;
    public String FriendPayUrl;
    public String CreateTime;
    public String ShopType;
    public String ServiceTel;
    public String PayTime;
    public String SupplierAddress;
    public String SupplierName;
    public String CouponValue;
    public int ShopId;
}
