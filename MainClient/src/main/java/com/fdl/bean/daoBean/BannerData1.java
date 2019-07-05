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
public class BannerData1 {
    @Id(autoincrement = true)
    public Long Id;
    public int AdminId;
    public String Href;
    public String ImageUrl;
    public int OrderBy;
    public boolean Default;
    public boolean Audit;
    @Generated(hash = 1158865019)
    public BannerData1(Long Id, int AdminId, String Href, String ImageUrl,
            int OrderBy, boolean Default, boolean Audit) {
        this.Id = Id;
        this.AdminId = AdminId;
        this.Href = Href;
        this.ImageUrl = ImageUrl;
        this.OrderBy = OrderBy;
        this.Default = Default;
        this.Audit = Audit;
    }
    @Generated(hash = 1162178929)
    public BannerData1() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public int getAdminId() {
        return this.AdminId;
    }
    public void setAdminId(int AdminId) {
        this.AdminId = AdminId;
    }
    public String getHref() {
        return this.Href;
    }
    public void setHref(String Href) {
        this.Href = Href;
    }
    public String getImageUrl() {
        return this.ImageUrl;
    }
    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }
    public int getOrderBy() {
        return this.OrderBy;
    }
    public void setOrderBy(int OrderBy) {
        this.OrderBy = OrderBy;
    }
    public boolean getDefault() {
        return this.Default;
    }
    public void setDefault(boolean Default) {
        this.Default = Default;
    }
    public boolean getAudit() {
        return this.Audit;
    }
    public void setAudit(boolean Audit) {
        this.Audit = Audit;
    }
  
}
