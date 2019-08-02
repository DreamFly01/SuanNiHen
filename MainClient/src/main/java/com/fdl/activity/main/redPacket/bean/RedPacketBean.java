package com.fdl.activity.main.redPacket.bean;


import java.util.List;

/**
 * @author 陈自强
 * @date 2019/7/29
 */
public class RedPacketBean  {

    private int couponCount;
    private double redPacketPrice;
    private List<RedPacketListBean> list;

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public double getRedPacketPrice() {
        return redPacketPrice;
    }

    public void setRedPacketPrice(double redPacketPrice) {
        this.redPacketPrice = redPacketPrice;
    }

    public List<RedPacketListBean> getList() {
        return list;
    }

    public void setList(List<RedPacketListBean> redPackList) {
        this.list = redPackList;
    }

}
