package com.fdl.bean.daoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/16<p>
 * <p>changeTime：2019/2/16<p>
 * <p>version：1<p>
 */
@Entity()
public class BannerBean {
    @Id
    public Long _id;
//    public List<BannerData> banner;
//    public List<Tips> localsub;
//    public List<BannerData1> fours;

    @Generated(hash = 58715259)
    public BannerBean(Long _id) {
        this._id = _id;
    }

    @Generated(hash = 2832759)
    public BannerBean() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
