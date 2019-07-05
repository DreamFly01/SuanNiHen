package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/18<p>
 * <p>changeTime：2019/2/18<p>
 * <p>version：1<p>
 */
@Entity()
public class MainProductBean {
    public int Id;
    public String Name;
    public int MarketNumber;
    public String GoodsImg;
    public double MarketPrice;
    public double SalesPrice;
    public String DetailUrl;
    @Generated(hash = 1115215015)
    public MainProductBean(int Id, String Name, int MarketNumber, String GoodsImg,
            double MarketPrice, double SalesPrice, String DetailUrl) {
        this.Id = Id;
        this.Name = Name;
        this.MarketNumber = MarketNumber;
        this.GoodsImg = GoodsImg;
        this.MarketPrice = MarketPrice;
        this.SalesPrice = SalesPrice;
        this.DetailUrl = DetailUrl;
    }
    @Generated(hash = 1716816472)
    public MainProductBean() {
    }
    public int getId() {
        return this.Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getName() {
        return this.Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public int getMarketNumber() {
        return this.MarketNumber;
    }
    public void setMarketNumber(int MarketNumber) {
        this.MarketNumber = MarketNumber;
    }
    public String getGoodsImg() {
        return this.GoodsImg;
    }
    public void setGoodsImg(String GoodsImg) {
        this.GoodsImg = GoodsImg;
    }
    public double getMarketPrice() {
        return this.MarketPrice;
    }
    public void setMarketPrice(double MarketPrice) {
        this.MarketPrice = MarketPrice;
    }
    public double getSalesPrice() {
        return this.SalesPrice;
    }
    public void setSalesPrice(double SalesPrice) {
        this.SalesPrice = SalesPrice;
    }
    public String getDetailUrl() {
        return this.DetailUrl;
    }
    public void setDetailUrl(String DetailUrl) {
        this.DetailUrl = DetailUrl;
    }
}
