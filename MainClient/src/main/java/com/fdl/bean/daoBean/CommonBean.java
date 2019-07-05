package com.fdl.bean.daoBean;

import com.fdl.bean.GoodsBean;

import java.util.List;

/**
 * Administrator on 2019/5/31 0031 18:01
 * jy
 * 01
 */
public class CommonBean {

    public int Id;
    public int UserId;
    public String ToTalOrderNo;
    public String OrderNo;
    public int OrderState;
    public int PayType;
    public int PayState;
    public double GoodsTotalPrice;
    public double TotalPrice;
    public double ExpressPrice;
    public String LeaveWord;
    public String ServiceTel;
    public double BalanceOne;
    public double Integral;
    public double WeixinMoney;
    public String ReceiverName;
    public String ReceiverTelPhone;
    public String ReceiverAreaAddress;
    public String ReceiverAddress;
    public String CreateTime;
    public String PayTime;
    public int ShopId;
    public String FriendPayUrl;
    public String ShopType;
    public List<GoodsBean> goodslist;
    public String subOrderNo;
    public int imgUrl;

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getToTalOrderNo() {
        return ToTalOrderNo;
    }

    public void setToTalOrderNo(String toTalOrderNo) {
        ToTalOrderNo = toTalOrderNo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public int getOrderState() {
        return OrderState;
    }

    public void setOrderState(int orderState) {
        OrderState = orderState;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int payType) {
        PayType = payType;
    }

    public int getPayState() {
        return PayState;
    }

    public void setPayState(int payState) {
        PayState = payState;
    }

    public double getGoodsTotalPrice() {
        return GoodsTotalPrice;
    }

    public void setGoodsTotalPrice(double goodsTotalPrice) {
        GoodsTotalPrice = goodsTotalPrice;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getExpressPrice() {
        return ExpressPrice;
    }

    public void setExpressPrice(double expressPrice) {
        ExpressPrice = expressPrice;
    }

    public String getLeaveWord() {
        return LeaveWord;
    }

    public void setLeaveWord(String leaveWord) {
        LeaveWord = leaveWord;
    }

    public String getServiceTel() {
        return ServiceTel;
    }

    public void setServiceTel(String serviceTel) {
        ServiceTel = serviceTel;
    }

    public double getBalanceOne() {
        return BalanceOne;
    }

    public void setBalanceOne(double balanceOne) {
        BalanceOne = balanceOne;
    }

    public double getIntegral() {
        return Integral;
    }

    public void setIntegral(double integral) {
        Integral = integral;
    }

    public double getWeixinMoney() {
        return WeixinMoney;
    }

    public void setWeixinMoney(double weixinMoney) {
        WeixinMoney = weixinMoney;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getReceiverTelPhone() {
        return ReceiverTelPhone;
    }

    public void setReceiverTelPhone(String receiverTelPhone) {
        ReceiverTelPhone = receiverTelPhone;
    }

    public String getReceiverAreaAddress() {
        return ReceiverAreaAddress;
    }

    public void setReceiverAreaAddress(String receiverAreaAddress) {
        ReceiverAreaAddress = receiverAreaAddress;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getFriendPayUrl() {
        return FriendPayUrl;
    }

    public void setFriendPayUrl(String friendPayUrl) {
        FriendPayUrl = friendPayUrl;
    }

    public String getShopType() {
        return ShopType;
    }

    public void setShopType(String shopType) {
        ShopType = shopType;
    }

    public List<GoodsBean> getGoodslist() {
        return goodslist;
    }

    public void setGoodslist(List<GoodsBean> goodslist) {
        this.goodslist = goodslist;
    }

    public String getSubOrderNo() {
        return subOrderNo;
    }

    public void setSubOrderNo(String subOrderNo) {
        this.subOrderNo = subOrderNo;
    }
}
