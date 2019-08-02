package com.fdl.activity.main.redPacket.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 陈自强
 * @date 2019/7/30
 */
public class RedPacketListBean {

    /**
     * SupplierID : null
     * Price : 20.0
     * FullPrice : 40.0
     * GoodsList : []
     */

    @SerializedName("SupplierID")
    public Object SupplierIDX;
    @SerializedName("Price")
    public double PriceX;
    @SerializedName("FullPrice")
    public double FullPriceX;
    /**
     * ID : 145
     * SupplierID : 121
     * Name : 商品满20减5
     * Price : 5
     * FullPrice : 20
     * Way : 1
     * GoodsIds : null
     * GoodsList : []
     * ShopName : 芙蓉兴盛
     * ShopLogo : https://shop.snihen.com:8448/api/Source/472720
     * BusinessActivities : 1
     * ShopType : 6
     * BeginTime : 2019-07-01 00:00:00:000
     * EndTime : 2019-08-31 00:00:00:000
     * CreateTime : 2019-07-27 03:37:37:000
     */

    private int ID;
    private int SupplierID;
    private String Name;
    private int Price;
    private int FullPrice;
    private int Way;
    private String GoodsIds;
    private String ShopName;
    private String ShopLogo;
    private String BusinessActivities;
    private String ShopType;
    private String BeginTime;
    private String EndTime;
    private String CreateTime;
    private boolean showGoods;
    private List<GoodsListBean> GoodsList;

    public boolean isShowGoods() {
        return showGoods;
    }

    public void setShowGoods(boolean showGoods) {
        this.showGoods = showGoods;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(int SupplierID) {
        this.SupplierID = SupplierID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getFullPrice() {
        return FullPrice;
    }

    public void setFullPrice(int FullPrice) {
        this.FullPrice = FullPrice;
    }

    public int getWay() {
        return Way;
    }

    public void setWay(int Way) {
        this.Way = Way;
    }

    public String getGoodsIds() {
        return GoodsIds;
    }

    public void setGoodsIds(String GoodsIds) {
        this.GoodsIds = GoodsIds;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getShopLogo() {
        return ShopLogo;
    }

    public void setShopLogo(String ShopLogo) {
        this.ShopLogo = ShopLogo;
    }

    public String getBusinessActivities() {
        return BusinessActivities;
    }

    public void setBusinessActivities(String BusinessActivities) {
        this.BusinessActivities = BusinessActivities;
    }

    public String getShopType() {
        return ShopType;
    }

    public void setShopType(String ShopType) {
        this.ShopType = ShopType;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String BeginTime) {
        this.BeginTime = BeginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public List<GoodsListBean> getGoodsList() {
        return GoodsList;
    }

    public void setGoodsList(List<GoodsListBean> GoodsList) {
        this.GoodsList = GoodsList;
    }

}
