package com.fdl.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/4<p>
 * <p>changeTime：2019/1/4<p>
 * <p>version：1<p>
 */
public class OrderDataBean implements Parcelable {
    public int Id;
    public String carId;
    public String SupplierName;
    public String SupplierLogo;
    public String SupplierUrl;
    public List<GoodsBean> goodsList;
    public List<GoodsBean> goodslist;
    public int SupplierId;
    public String ShopName;
    public String ShopLogo;
    public double ExpressMoney;
    public double TotalMoney;
    public OrderDataBean() {
    }

    protected OrderDataBean(Parcel in) {
        Id = in.readInt();
        SupplierName = in.readString();
        SupplierLogo = in.readString();
        SupplierUrl = in.readString();
        goodsList = in.createTypedArrayList(GoodsBean.CREATOR);
        ExpressMoney = in.readDouble();
    }

    public static final Creator<OrderDataBean> CREATOR = new Creator<OrderDataBean>() {
        @Override
        public OrderDataBean createFromParcel(Parcel in) {
            return new OrderDataBean(in);
        }

        @Override
        public OrderDataBean[] newArray(int size) {
            return new OrderDataBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(SupplierName);
        dest.writeString(SupplierLogo);
        dest.writeString(SupplierUrl);
        dest.writeTypedList(goodsList);
        dest.writeDouble(ExpressMoney);
    }
}
