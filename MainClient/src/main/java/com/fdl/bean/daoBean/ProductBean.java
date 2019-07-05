package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/12<p>
 * <p>changeTime：2019/2/12<p>
 * <p>version：1<p>
 */
@Entity()
public class ProductBean {
    @Id
    public Long productId;
    public int Id;
    public String Name;
    public int MarketNumber;
    public String GoodsImg;
    public double MarketPrice;
    public double SalesPrice;
    public String DetailUrl;
    @Generated(hash = 1015628512)
    public ProductBean(Long productId, int Id, String Name, int MarketNumber,
            String GoodsImg, double MarketPrice, double SalesPrice,
            String DetailUrl) {
        this.productId = productId;
        this.Id = Id;
        this.Name = Name;
        this.MarketNumber = MarketNumber;
        this.GoodsImg = GoodsImg;
        this.MarketPrice = MarketPrice;
        this.SalesPrice = SalesPrice;
        this.DetailUrl = DetailUrl;
    }
    @Generated(hash = 669380001)
    public ProductBean() {
    }
    public Long getProductId() {
        return this.productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
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
