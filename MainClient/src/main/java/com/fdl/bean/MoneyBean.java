package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/2<p>
 * <p>changeTime：2019/4/2<p>
 * <p>version：1<p>
 */
public class MoneyBean {
    public int SupplierId;
    public String SupplierName;
    public double HavaMoney;
    public double PayMoney;
    public double ActivityPayMoney;
    public double PromotePayMoney;
    public double WithdrawDepositMoney;
    public double FreezeMoney;
    public double MinMoney;
    public double MaxMoney;
    public double Rate;
    public double AdMoney;
    public List<CaptailsBean> MoneyDetail;

}
