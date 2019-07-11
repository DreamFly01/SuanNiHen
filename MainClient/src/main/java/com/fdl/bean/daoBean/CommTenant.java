package com.fdl.bean.daoBean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/25<p>
 * <p>changeTime：2019/1/25<p>
 * <p>version：1<p>
 */
@Entity ()
public class CommTenant implements Parcelable {
    @Id
    public Long CommTenantId;
    private int SupplierId;
    public String CommTenantName;
    public String CommTenantIcon;
    public int OnThePin;
    public double Price;
    public String UnitsTitle;
    public int total;

    private long commtenantId;
    public long Inventory;
    public int GoodsNum;
    @Generated(hash = 1845819458)
    public CommTenant(Long CommTenantId, int SupplierId, String CommTenantName,
            String CommTenantIcon, int OnThePin, double Price, String UnitsTitle,
            int total, long commtenantId, long Inventory, int GoodsNum) {
        this.CommTenantId = CommTenantId;
        this.SupplierId = SupplierId;
        this.CommTenantName = CommTenantName;
        this.CommTenantIcon = CommTenantIcon;
        this.OnThePin = OnThePin;
        this.Price = Price;
        this.UnitsTitle = UnitsTitle;
        this.total = total;
        this.commtenantId = commtenantId;
        this.Inventory = Inventory;
        this.GoodsNum = GoodsNum;
    }
    @Generated(hash = 1641007702)
    public CommTenant() {
    }

    protected CommTenant(Parcel in) {
        if (in.readByte() == 0) {
            CommTenantId = null;
        } else {
            CommTenantId = in.readLong();
        }
        SupplierId = in.readInt();
        CommTenantName = in.readString();
        CommTenantIcon = in.readString();
        OnThePin = in.readInt();
        Price = in.readDouble();
        UnitsTitle = in.readString();
        total = in.readInt();
        commtenantId = in.readLong();
        Inventory = in.readLong();
        GoodsNum = in.readInt();
    }

    public static final Creator<CommTenant> CREATOR = new Creator<CommTenant>() {
        @Override
        public CommTenant createFromParcel(Parcel in) {
            return new CommTenant(in);
        }

        @Override
        public CommTenant[] newArray(int size) {
            return new CommTenant[size];
        }
    };

    public Long getCommTenantId() {
        return this.CommTenantId;
    }
    public void setCommTenantId(Long CommTenantId) {
        this.CommTenantId = CommTenantId;
    }
    public int getSupplierId() {
        return this.SupplierId;
    }
    public void setSupplierId(int SupplierId) {
        this.SupplierId = SupplierId;
    }
    public String getCommTenantName() {
        return this.CommTenantName;
    }
    public void setCommTenantName(String CommTenantName) {
        this.CommTenantName = CommTenantName;
    }
    public String getCommTenantIcon() {
        return this.CommTenantIcon;
    }
    public void setCommTenantIcon(String CommTenantIcon) {
        this.CommTenantIcon = CommTenantIcon;
    }
    public int getOnThePin() {
        return this.OnThePin;
    }
    public void setOnThePin(int OnThePin) {
        this.OnThePin = OnThePin;
    }
    public double getPrice() {
        return this.Price;
    }
    public void setPrice(double Price) {
        this.Price = Price;
    }
    public String getUnitsTitle() {
        return this.UnitsTitle;
    }
    public void setUnitsTitle(String UnitsTitle) {
        this.UnitsTitle = UnitsTitle;
    }
    public int getTotal() {
        return this.total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public long getCommtenantId() {
        return this.commtenantId;
    }
    public void setCommtenantId(long commtenantId) {
        this.commtenantId = commtenantId;
    }
    public long getInventory() {
        return this.Inventory;
    }
    public void setInventory(long Inventory) {
        this.Inventory = Inventory;
    }
    public int getGoodsNum() {
        return this.GoodsNum;
    }
    public void setGoodsNum(int GoodsNum) {
        this.GoodsNum = GoodsNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (CommTenantId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(CommTenantId);
        }
        dest.writeInt(SupplierId);
        dest.writeString(CommTenantName);
        dest.writeString(CommTenantIcon);
        dest.writeInt(OnThePin);
        dest.writeDouble(Price);
        dest.writeString(UnitsTitle);
        dest.writeInt(total);
        dest.writeLong(commtenantId);
        dest.writeLong(Inventory);
        dest.writeInt(GoodsNum);
    }
}
