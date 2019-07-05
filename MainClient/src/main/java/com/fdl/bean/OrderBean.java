package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/27<p>
 * <p>changeTime：2018/12/27<p>
 * <p>version：1<p>
 */
public class OrderBean {
    public int AddressId;
    public String ReceiveName;
    public String ReceiveTel;
    public String AreaAddress;
    public String DetailAddress;
    public int SupplierId;
    public String ShopName;
    public String ShopLogo;
    public int GoodsId;
    public String GoodsName;
    public int Number;
    public double SalesPrice;
    public double TotalMoney;
    public double ExpressMoney;
    public String NormalInfo;
    public String GoodsImg;
    public List<OrderDataBean> supplierlist;

}
