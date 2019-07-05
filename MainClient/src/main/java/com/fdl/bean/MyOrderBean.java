package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/5<p>
 * <p>changeTime：2019/1/5<p>
 * <p>version：1<p>
 */
public class MyOrderBean {
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
    public String ServiceTel;
    public double BalanceOne;
    public double Integral;
    public double WeixinMoney;
    public String ReceiverName;
    public String ReceiverTelPhone;
    public String ReceiverAreaAddress;
    public String ReceiverAddress;
    public String CreateTime;
    public String PayTime;
    public int ShopId;
    public String FriendPayUrl;
    public String ShopType;
    public List<GoodsBean> goodslist;
    public String subOrderNo;

}
