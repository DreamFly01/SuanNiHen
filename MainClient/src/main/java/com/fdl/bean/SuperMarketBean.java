package com.fdl.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/24<p>
 * <p>changeTime：2019/1/24<p>
 * <p>version：1<p>
 */
@Entity
public class SuperMarketBean implements MultiItemEntity {
    @Id
    public Long superMarketId;
    public int SupplierId;
    public String SupplierName;
    public String Icon;
    public String Address;
    public String Longitude;
    public String Latitude;
    public double Evaluate;
    public String Distance;

    public static final int NETDATE = 1;
    public static final int CATCHDATE = 2;
    public static final int NETDATE_TITLE = 3;
    public static final int CATCHDATE_TITLE = 4;
    public int itemType;
    public int shopType;


    public SuperMarketBean(int itemType) {
        this.itemType = itemType;
    }

    @Generated(hash = 68447239)
    public SuperMarketBean(Long superMarketId, int SupplierId, String SupplierName,
            String Icon, String Address, String Longitude, String Latitude,
            double Evaluate, String Distance, int itemType, int shopType) {
        this.superMarketId = superMarketId;
        this.SupplierId = SupplierId;
        this.SupplierName = SupplierName;
        this.Icon = Icon;
        this.Address = Address;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Evaluate = Evaluate;
        this.Distance = Distance;
        this.itemType = itemType;
        this.shopType = shopType;
    }

    @Generated(hash = 950114449)
    public SuperMarketBean() {
    }
    
    @Override
    public int getItemType() {
        return itemType;
    }
    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Long getSuperMarketId() {
        return this.superMarketId;
    }

    public void setSuperMarketId(Long superMarketId) {
        this.superMarketId = superMarketId;
    }

    public int getSupplierId() {
        return this.SupplierId;
    }

    public void setSupplierId(int SupplierId) {
        this.SupplierId = SupplierId;
    }

    public String getSupplierName() {
        return this.SupplierName;
    }

    public void setSupplierName(String SupplierName) {
        this.SupplierName = SupplierName;
    }

    public String getIcon() {
        return this.Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public double getEvaluate() {
        return this.Evaluate;
    }

    public void setEvaluate(double Evaluate) {
        this.Evaluate = Evaluate;
    }

    public String getDistance() {
        return this.Distance;
    }

    public void setDistance(String Distance) {
        this.Distance = Distance;
    }

    public int getShopType() {
        return this.shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }
}
