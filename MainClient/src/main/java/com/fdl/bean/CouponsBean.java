package com.fdl.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/9<p>
 * <p>changeTime：2019/5/9<p>
 * <p>version：1<p>
 */
public class CouponsBean implements MultiItemEntity,Parcelable {
    public int CouponId;
    public String CouponName;
    public double CouponValue;
    public double ConditionValue;
    public int CouponTypes;
    public String BeginDate;
    public String EndDate;

    public int SendPart;
    public String ShowImg;
    public String LinkUrl;
    public int ID;
    public int IsBack;

    public int SupplierId;
    public int UserId;
    public int UseState;
    public String OrderNo;

    public int CouponWay;
    public int MerchantsId;
    public String MerchantsName;
    public String GoodsIds;
    public String GoodsNames;
    public String GoodsNames2;
    public static final int PRODUCT = 1;
    public static final int COUPONS = 2;
    public static final int PRODUCT_TITLE = 3;
    public static final int COUPONS_TITLE = 4;
    public int itemType;

    public boolean isSelect;
    public CouponsBean(int itemType) {
        this.itemType = itemType;
    }

    protected CouponsBean(Parcel in) {
        CouponId = in.readInt();
        CouponName = in.readString();
        CouponValue = in.readDouble();
        ConditionValue = in.readDouble();
        CouponTypes = in.readInt();
        BeginDate = in.readString();
        EndDate = in.readString();
        SendPart = in.readInt();
        ShowImg = in.readString();
        LinkUrl = in.readString();
        ID = in.readInt();
        IsBack = in.readInt();
        SupplierId = in.readInt();
        UserId = in.readInt();
        UseState = in.readInt();
        OrderNo = in.readString();
        CouponWay = in.readInt();
        MerchantsId = in.readInt();
        MerchantsName = in.readString();
        GoodsIds = in.readString();
        GoodsNames = in.readString();
        GoodsNames2 = in.readString();
        itemType = in.readInt();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<CouponsBean> CREATOR = new Creator<CouponsBean>() {
        @Override
        public CouponsBean createFromParcel(Parcel in) {
            return new CouponsBean(in);
        }

        @Override
        public CouponsBean[] newArray(int size) {
            return new CouponsBean[size];
        }
    };

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(CouponId);
        dest.writeString(CouponName);
        dest.writeDouble(CouponValue);
        dest.writeDouble(ConditionValue);
        dest.writeInt(CouponTypes);
        dest.writeString(BeginDate);
        dest.writeString(EndDate);
        dest.writeInt(SendPart);
        dest.writeString(ShowImg);
        dest.writeString(LinkUrl);
        dest.writeInt(ID);
        dest.writeInt(IsBack);
        dest.writeInt(SupplierId);
        dest.writeInt(UserId);
        dest.writeInt(UseState);
        dest.writeString(OrderNo);
        dest.writeInt(CouponWay);
        dest.writeInt(MerchantsId);
        dest.writeString(MerchantsName);
        dest.writeString(GoodsIds);
        dest.writeString(GoodsNames);
        dest.writeString(GoodsNames2);
        dest.writeInt(itemType);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }
}
