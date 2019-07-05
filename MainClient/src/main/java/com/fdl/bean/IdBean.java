package com.fdl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/16<p>
 * <p>changeTime：2019/1/16<p>
 * <p>version：1<p>
 */
public class IdBean implements Parcelable {
    public int id;
    public String carId;

    public IdBean() {
    }

    protected IdBean(Parcel in) {
        id = in.readInt();
        carId = in.readString();
    }

    public static final Creator<IdBean> CREATOR = new Creator<IdBean>() {
        @Override
        public IdBean createFromParcel(Parcel in) {
            return new IdBean(in);
        }

        @Override
        public IdBean[] newArray(int size) {
            return new IdBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(carId);
    }
}
