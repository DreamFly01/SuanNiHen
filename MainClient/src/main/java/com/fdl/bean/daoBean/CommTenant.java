package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/25<p>
 * <p>changeTime：2019/1/25<p>
 * <p>version：1<p>
 */
@Entity ()
public class CommTenant {
    @Id
    public Long CommTenantId;
    private int SupplierId;
    public String CommTenantName;
    public String CommTenantIcon;
    public int OnThePin;
    public double Price;
    public String UnitsTitle;
    public int total;

    private long commtenantId;
    public long Inventory;
    public int GoodsNum;
    @Generated(hash = 1845819458)
    public CommTenant(Long CommTenantId, int SupplierId, String CommTenantName,
            String CommTenantIcon, int OnThePin, double Price, String UnitsTitle,
            int total, long commtenantId, long Inventory, int GoodsNum) {
        this.CommTenantId = CommTenantId;
        this.SupplierId = SupplierId;
        this.CommTenantName = CommTenantName;
        this.CommTenantIcon = CommTenantIcon;
        this.OnThePin = OnThePin;
        this.Price = Price;
        this.UnitsTitle = UnitsTitle;
        this.total = total;
        this.commtenantId = commtenantId;
        this.Inventory = Inventory;
        this.GoodsNum = GoodsNum;
    }
    @Generated(hash = 1641007702)
    public CommTenant() {
    }
    public Long getCommTenantId() {
        return this.CommTenantId;
    }
    public void setCommTenantId(Long CommTenantId) {
        this.CommTenantId = CommTenantId;
    }
    public int getSupplierId() {
        return this.SupplierId;
    }
    public void setSupplierId(int SupplierId) {
        this.SupplierId = SupplierId;
    }
    public String getCommTenantName() {
        return this.CommTenantName;
    }
    public void setCommTenantName(String CommTenantName) {
        this.CommTenantName = CommTenantName;
    }
    public String getCommTenantIcon() {
        return this.CommTenantIcon;
    }
    public void setCommTenantIcon(String CommTenantIcon) {
        this.CommTenantIcon = CommTenantIcon;
    }
    public int getOnThePin() {
        return this.OnThePin;
    }
    public void setOnThePin(int OnThePin) {
        this.OnThePin = OnThePin;
    }
    public double getPrice() {
        return this.Price;
    }
    public void setPrice(double Price) {
        this.Price = Price;
    }
    public String getUnitsTitle() {
        return this.UnitsTitle;
    }
    public void setUnitsTitle(String UnitsTitle) {
        this.UnitsTitle = UnitsTitle;
    }
    public int getTotal() {
        return this.total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public long getCommtenantId() {
        return this.commtenantId;
    }
    public void setCommtenantId(long commtenantId) {
        this.commtenantId = commtenantId;
    }
    public long getInventory() {
        return this.Inventory;
    }
    public void setInventory(long Inventory) {
        this.Inventory = Inventory;
    }
    public int getGoodsNum() {
        return this.GoodsNum;
    }
    public void setGoodsNum(int GoodsNum) {
        this.GoodsNum = GoodsNum;
    }
}
