package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/3/22<p>
 * <p>changeTime：2019/3/22<p>
 * <p>version：1<p>
 */
@Entity()
public class AearBean {
    public String id;
    public String ParentID;
    public String Level;
    public String AddressName;
    public String Sort;
    public String States;
    public String Description;
    @Generated(hash = 583641590)
    public AearBean(String id, String ParentID, String Level, String AddressName,
            String Sort, String States, String Description) {
        this.id = id;
        this.ParentID = ParentID;
        this.Level = Level;
        this.AddressName = AddressName;
        this.Sort = Sort;
        this.States = States;
        this.Description = Description;
    }
    @Generated(hash = 1514124253)
    public AearBean() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getParentID() {
        return this.ParentID;
    }
    public void setParentID(String ParentID) {
        this.ParentID = ParentID;
    }
    public String getLevel() {
        return this.Level;
    }
    public void setLevel(String Level) {
        this.Level = Level;
    }
    public String getAddressName() {
        return this.AddressName;
    }
    public void setAddressName(String AddressName) {
        this.AddressName = AddressName;
    }
    public String getSort() {
        return this.Sort;
    }
    public void setSort(String Sort) {
        this.Sort = Sort;
    }
    public String getStates() {
        return this.States;
    }
    public void setStates(String States) {
        this.States = States;
    }
    public String getDescription() {
        return this.Description;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }

}
