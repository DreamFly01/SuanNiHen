package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/16<p>
 * <p>changeTime：2019/2/16<p>
 * <p>version：1<p>
 */
@Entity()
public class Tipsbean {
    public String Title;

    @Generated(hash = 548257913)
    public Tipsbean(String Title) {
        this.Title = Title;
    }

    @Generated(hash = 998733238)
    public Tipsbean() {
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }
}
