package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/16<p>
 * <p>changeTime：2019/2/16<p>
 * <p>version：1<p>
 */
@Entity()
public class Tips {
    @Id
    public Long tipsId;
    public int AdminId;
    public String Front;
    public String Title;
    public int Defaults;
    public boolean Audit;
    public String localUrl;
    public String CreateTime;
    @Generated(hash = 1763437413)
    public Tips(Long tipsId, int AdminId, String Front, String Title, int Defaults,
            boolean Audit, String localUrl, String CreateTime) {
        this.tipsId = tipsId;
        this.AdminId = AdminId;
        this.Front = Front;
        this.Title = Title;
        this.Defaults = Defaults;
        this.Audit = Audit;
        this.localUrl = localUrl;
        this.CreateTime = CreateTime;
    }
    @Generated(hash = 1707636270)
    public Tips() {
    }
    public Long getTipsId() {
        return this.tipsId;
    }
    public void setTipsId(Long tipsId) {
        this.tipsId = tipsId;
    }
    public int getAdminId() {
        return this.AdminId;
    }
    public void setAdminId(int AdminId) {
        this.AdminId = AdminId;
    }
    public String getFront() {
        return this.Front;
    }
    public void setFront(String Front) {
        this.Front = Front;
    }
    public String getTitle() {
        return this.Title;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }
    public int getDefaults() {
        return this.Defaults;
    }
    public void setDefaults(int Defaults) {
        this.Defaults = Defaults;
    }
    public boolean getAudit() {
        return this.Audit;
    }
    public void setAudit(boolean Audit) {
        this.Audit = Audit;
    }
    public String getLocalUrl() {
        return this.localUrl;
    }
    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }
    public String getCreateTime() {
        return this.CreateTime;
    }
    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
   
}
