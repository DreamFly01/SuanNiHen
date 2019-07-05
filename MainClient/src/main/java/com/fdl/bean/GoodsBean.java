package com.fdl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class GoodsBean implements Parcelable {
    public int Id;
    public int OrderId;
    public int OrderNo;
    public String Name;
    public int SupplierId;
    public int GoodsId;
    public int Number;
    public String GoodsImg;
    public String NormsInfo;
    public int NormsId;
    public double SalesPrice;
    public int BargainsId;
    public int AccountUserID;
    public String DetailUrl;
    public int MarketNumber;
    public double MarketPrice;
    public String CoverImgId;
    public String GoodsName;
    public int GoodId;
    public String NormalInfo;
    public boolean isChose;
    public String ShopType;

    public GoodsBean(){

    }

    protected GoodsBean(Parcel in) {
        Id = in.readInt();
        OrderId = in.readInt();
        OrderNo = in.readInt();
        Name = in.readString();
        SupplierId = in.readInt();
        GoodsId = in.readInt();
        Number = in.readInt();
        GoodsImg = in.readString();
        NormsInfo = in.readString();
        NormsId = in.readInt();
        SalesPrice = in.readDouble();
        BargainsId = in.readInt();
        AccountUserID = in.readInt();
        DetailUrl = in.readString();
        MarketNumber = in.readInt();
        MarketPrice = in.readDouble();
        CoverImgId = in.readString();
        GoodsName = in.readString();
        GoodId = in.readInt();
        NormalInfo = in.readString();
        isChose = in.readByte() != 0;
        ShopType = in.readString();
    }

    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel in) {
            return new GoodsBean(in);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeInt(OrderId);
        dest.writeInt(OrderNo);
        dest.writeString(Name);
        dest.writeInt(SupplierId);
        dest.writeInt(GoodsId);
        dest.writeInt(Number);
        dest.writeString(GoodsImg);
        dest.writeString(NormsInfo);
        dest.writeInt(NormsId);
        dest.writeDouble(SalesPrice);
        dest.writeInt(BargainsId);
        dest.writeInt(AccountUserID);
        dest.writeString(DetailUrl);
        dest.writeInt(MarketNumber);
        dest.writeDouble(MarketPrice);
        dest.writeString(CoverImgId);
        dest.writeString(GoodsName);
        dest.writeInt(GoodId);
        dest.writeString(NormalInfo);
        dest.writeByte((byte) (isChose ? 1 : 0));
        dest.writeString(ShopType);
    }
}
