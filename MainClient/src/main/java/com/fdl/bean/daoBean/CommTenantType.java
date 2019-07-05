package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.snh.greendao.DaoSession;
import com.snh.greendao.CommTenantDao;
import com.snh.greendao.CommTenantTypeDao;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/25<p>
 * <p>changeTime：2019/1/25<p>
 * <p>version：1<p>
 */
@Entity
public class CommTenantType {
    @Id()
    private long id;
    public int TypeId;
    public String TypeName;
    @ToMany (referencedJoinProperty = "commtenantId")
    public List<CommTenant> CommTenantList;
    public long commtenantTypeId;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2089896205)
    private transient CommTenantTypeDao myDao;
    @Generated(hash = 623826271)
    public CommTenantType(long id, int TypeId, String TypeName,
            long commtenantTypeId) {
        this.id = id;
        this.TypeId = TypeId;
        this.TypeName = TypeName;
        this.commtenantTypeId = commtenantTypeId;
    }
    @Generated(hash = 1989752040)
    public CommTenantType() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getTypeId() {
        return this.TypeId;
    }
    public void setTypeId(int TypeId) {
        this.TypeId = TypeId;
    }
    public String getTypeName() {
        return this.TypeName;
    }
    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }
    public long getCommtenantTypeId() {
        return this.commtenantTypeId;
    }
    public void setCommtenantTypeId(long commtenantTypeId) {
        this.commtenantTypeId = commtenantTypeId;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 703512074)
    public List<CommTenant> getCommTenantList() {
        if (CommTenantList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommTenantDao targetDao = daoSession.getCommTenantDao();
            List<CommTenant> CommTenantListNew = targetDao
                    ._queryCommTenantType_CommTenantList(id);
            synchronized (this) {
                if (CommTenantList == null) {
                    CommTenantList = CommTenantListNew;
                }
            }
        }
        return CommTenantList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1167341989)
    public synchronized void resetCommTenantList() {
        CommTenantList = null;
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
    @Generated(hash = 610855053)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommTenantTypeDao() : null;
    }
   
}
