package com.fdl.bean;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/18<p>
 * <p>changeTime：2019/1/18<p>
 * <p>version：1<p>
 */
public class AccountDetailsBean{
    public double Money;
    public String Reason;
    public long CreateTime;
    public String OrderNo;
    public double BargainPrice;
    public String GooodsName;
    public String GooodsImg;
    public int LogId;
    public int UserId;
    public int ReturnState;//0待返 1已返  如果为1 不显示返现日期
    public long DTime;//返现日期
    public int BargainsType;//0 帮好友薅羊毛 1首次分享薅羊毛
    public String Title;
}
