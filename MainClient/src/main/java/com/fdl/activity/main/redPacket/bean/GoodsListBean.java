package com.fdl.activity.main.redPacket.bean;

/**
 * @author 陈自强
 * @date 2019/7/30
 */
public class GoodsListBean {
    /**
     * Id : 6196
     * Name : 乐事无限薯片
     * ShortInfo : null
     * Images : https://shop.snihen.com:8448/api/Source/472516
     * SalesPrice : 2.5
     */

    private int Id;
    private String Name;
    private Object ShortInfo;
    private String Images;
    private double SalesPrice;
    private int pId;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Object getShortInfo() {
        return ShortInfo;
    }

    public void setShortInfo(Object ShortInfo) {
        this.ShortInfo = ShortInfo;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String Images) {
        this.Images = Images;
    }

    public double getSalesPrice() {
        return SalesPrice;
    }

    public void setSalesPrice(double SalesPrice) {
        this.SalesPrice = SalesPrice;
    }
}
