package com.fdl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/29<p>
 * <p>changeTime：2018/12/29<p>
 * <p>version：1<p>
 */
public class AddressBean implements Parcelable {
    public int Id;
    public int UserId;
    public String RealName;
    public String TelPhone;
    public String AreaAddress;
    public String Address;
    public int IsDefault;
    public String AreaId;

    protected AddressBean(Parcel in) {
        Id = in.readInt();
        UserId = in.readInt();
        RealName = in.readString();
        TelPhone = in.readString();
        AreaAddress = in.readString();
        Address = in.readString();
        IsDefault = in.readInt();
        AreaId = in.readString();
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel in) {
            return new AddressBean(in);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeInt(UserId);
        dest.writeString(RealName);
        dest.writeString(TelPhone);
        dest.writeString(AreaAddress);
        dest.writeString(Address);
        dest.writeInt(IsDefault);
        dest.writeString(AreaId);
    }
}
