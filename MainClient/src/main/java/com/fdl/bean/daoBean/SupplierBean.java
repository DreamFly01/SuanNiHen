package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.snh.greendao.DaoSession;
import com.snh.greendao.CommTenantTypeDao;
import com.snh.greendao.SupplierBeanDao;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/25<p>
 * <p>changeTime：2019/1/25<p>
 * <p>version：1<p>
 */
@Entity
public class SupplierBean {
    @Id
    private long id;
    public int SupplierId;
    public String SupplierName;
    public String SupplierIcon;
    public String SupplierLevel;
    public String Address;
    public String Longitude;
    public String Latitude;
    public boolean IsFavorite;
    public String FxUrl;
    public String XCXUrl;
    public String ServiceTel;
    public String BusinessHour;
    public String ServerContent;
    @ToMany(referencedJoinProperty = "commtenantTypeId")
    public List<CommTenantType> CommTenantResultList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2046375072)
    private transient SupplierBeanDao myDao;
    @Generated(hash = 1291246630)
    public SupplierBean(long id, int SupplierId, String SupplierName, String SupplierIcon,
            String SupplierLevel, String Address, String Longitude, String Latitude, boolean IsFavorite,
            String FxUrl, String XCXUrl, String ServiceTel, String BusinessHour, String ServerContent) {
        this.id = id;
        this.SupplierId = SupplierId;
        this.SupplierName = SupplierName;
        this.SupplierIcon = SupplierIcon;
        this.SupplierLevel = SupplierLevel;
        this.Address = Address;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.IsFavorite = IsFavorite;
        this.FxUrl = FxUrl;
        this.XCXUrl = XCXUrl;
        this.ServiceTel = ServiceTel;
        this.BusinessHour = BusinessHour;
        this.ServerContent = ServerContent;
    }
    @Generated(hash = 1501921620)
    public SupplierBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getSupplierId() {
        return this.SupplierId;
    }
    public void setSupplierId(int SupplierId) {
        this.SupplierId = SupplierId;
    }
    public String getSupplierName() {
        return this.SupplierName;
    }
    public void setSupplierName(String SupplierName) {
        this.SupplierName = SupplierName;
    }
    public String getSupplierIcon() {
        return this.SupplierIcon;
    }
    public void setSupplierIcon(String SupplierIcon) {
        this.SupplierIcon = SupplierIcon;
    }
    public String getSupplierLevel() {
        return this.SupplierLevel;
    }
    public void setSupplierLevel(String SupplierLevel) {
        this.SupplierLevel = SupplierLevel;
    }
    public String getAddress() {
        return this.Address;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }
    public String getLongitude() {
        return this.Longitude;
    }
    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }
    public String getLatitude() {
        return this.Latitude;
    }
    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }
    public boolean getIsFavorite() {
        return this.IsFavorite;
    }
    public void setIsFavorite(boolean IsFavorite) {
        this.IsFavorite = IsFavorite;
    }
    public String getFxUrl() {
        return this.FxUrl;
    }
    public void setFxUrl(String FxUrl) {
        this.FxUrl = FxUrl;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2123763006)
    public List<CommTenantType> getCommTenantResultList() {
        if (CommTenantResultList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommTenantTypeDao targetDao = daoSession.getCommTenantTypeDao();
            List<CommTenantType> CommTenantResultListNew = targetDao
                    ._querySupplierBean_CommTenantResultList(id);
            synchronized (this) {
                if (CommTenantResultList == null) {
                    CommTenantResultList = CommTenantResultListNew;
                }
            }
        }
        return CommTenantResultList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 954041146)
    public synchronized void resetCommTenantResultList() {
        CommTenantResultList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 451500856)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSupplierBeanDao() : null;
    }
    public String getXCXUrl() {
        return this.XCXUrl;
    }
    public void setXCXUrl(String XCXUrl) {
        this.XCXUrl = XCXUrl;
    }
    public String getServiceTel() {
        return this.ServiceTel;
    }
    public void setServiceTel(String ServiceTel) {
        this.ServiceTel = ServiceTel;
    }
    public String getBusinessHour() {
        return this.BusinessHour;
    }
    public void setBusinessHour(String BusinessHour) {
        this.BusinessHour = BusinessHour;
    }
    public String getServerContent() {
        return this.ServerContent;
    }
    public void setServerContent(String ServerContent) {
        this.ServerContent = ServerContent;
    }

}
