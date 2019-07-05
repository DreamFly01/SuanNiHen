package com.fdl.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/7<p>
 * <p>changeTime：2019/5/7<p>
 * <p>version：1<p>
 */
public class TravelLuckyBean implements Parcelable   {

    public int count;
    public String PrizeLevelCode;
    public String PrizeLevelName;
    public String PrizeName;
    public String PrizeImg;
    public String ExchangeCode;
    public List<String> ExchangeAddress;

    public TravelLuckyBean() {
    }


    protected TravelLuckyBean(Parcel in) {
        count = in.readInt();
        PrizeLevelCode = in.readString();
        PrizeLevelName = in.readString();
        PrizeName = in.readString();
        PrizeImg = in.readString();
        ExchangeCode = in.readString();
        ExchangeAddress = in.createStringArrayList();
    }

    public static final Creator<TravelLuckyBean> CREATOR = new Creator<TravelLuckyBean>() {
        @Override
        public TravelLuckyBean createFromParcel(Parcel in) {
            return new TravelLuckyBean(in);
        }

        @Override
        public TravelLuckyBean[] newArray(int size) {
            return new TravelLuckyBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeString(PrizeLevelCode);
        dest.writeString(PrizeLevelName);
        dest.writeString(PrizeName);
        dest.writeString(PrizeImg);
        dest.writeString(ExchangeCode);
        dest.writeStringList(ExchangeAddress);
    }
}
