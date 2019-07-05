package com.fdl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/7/1<p>
 * <p>changeTime：2019/7/1<p>
 * <p>version：1<p>
 */
public class OrdersData implements Parcelable {
    public int Id;
    public int GoodsId;
    public int GoodsNormId;
    public String Image;
    public double SalesPrice;
    public String UnitsTitle;
    public String GoodsName;
    public int GoodsNum;//已经确认的菜品数量
    public int GoodsNum2;//小程序 提交的数量

    public OrdersData() {
    }


    protected OrdersData(Parcel in) {
        Id = in.readInt();
        GoodsId = in.readInt();
        GoodsNum = in.readInt();
        GoodsNormId = in.readInt();
        Image = in.readString();
        SalesPrice = in.readDouble();
        UnitsTitle = in.readString();
        GoodsName = in.readString();
        GoodsNum2 = in.readInt();
    }

    public static final Creator<OrdersData> CREATOR = new Creator<OrdersData>() {
        @Override
        public OrdersData createFromParcel(Parcel in) {
            return new OrdersData(in);
        }

        @Override
        public OrdersData[] newArray(int size) {
            return new OrdersData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeInt(GoodsId);
        dest.writeInt(GoodsNum);
        dest.writeInt(GoodsNormId);
        dest.writeString(Image);
        dest.writeDouble(SalesPrice);
        dest.writeString(UnitsTitle);
        dest.writeString(GoodsName);
        dest.writeInt(GoodsNum2);
    }
}
