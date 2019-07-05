package com.fdl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/7/2<p>
 * <p>changeTime：2019/7/2<p>
 * <p>version：1<p>
 */
public class FoodPredeterminelistBean implements Parcelable {
    public String ShopName;
    public String Logo;
    public String ContactsTel;
    public int AdultCount;
    public int ChildCount;
    public String DinnerTime;
    public int Id;


    protected FoodPredeterminelistBean(Parcel in) {
        ShopName = in.readString();
        Logo = in.readString();
        ContactsTel = in.readString();
        AdultCount = in.readInt();
        ChildCount = in.readInt();
        DinnerTime = in.readString();
        Id = in.readInt();
    }

    public static final Creator<FoodPredeterminelistBean> CREATOR = new Creator<FoodPredeterminelistBean>() {
        @Override
        public FoodPredeterminelistBean createFromParcel(Parcel in) {
            return new FoodPredeterminelistBean(in);
        }

        @Override
        public FoodPredeterminelistBean[] newArray(int size) {
            return new FoodPredeterminelistBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ShopName);
        dest.writeString(Logo);
        dest.writeString(ContactsTel);
        dest.writeInt(AdultCount);
        dest.writeInt(ChildCount);
        dest.writeString(DinnerTime);
        dest.writeInt(Id);
    }
}
