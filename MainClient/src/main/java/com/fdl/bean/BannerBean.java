package com.fdl.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/12<p>
 * <p>changeTime：2019/2/12<p>
 * <p>version：1<p>
 */
public class BannerBean {
    public Long _id;
    public List<BannerData> banner;
    public List<Tips> localsub;
    public List<BannerData1> fours;
    public class BannerData{
        public int Id;
        public int AdminId;
        public String Href;
        public String Title;
        public String ImageUrl;
        public int OrderBy;
        public boolean Default;
        public boolean Audit;
        public int Type;//新增返回  1 店铺广告 2商品广告 3跳转页面广告
        public int SupplierId;
        public int GoodsId;
        public int BusinessActivities;//新增返回   1本地 2 3全国
        public int ShopType;//新增返回   6商超士多

    }
    public class Tips{
        public int Id;
        public int AdminId;
        public String Front;
        public String Title;
        public int Defaults;
        public boolean Audit;
        public String localUrl;
        public String CreateTime;
    }
    public class BannerData1{
        public Long Id;
        public int AdminId;
        public String Href;
        public String ImageUrl;
        public int OrderBy;
        public boolean Default;
        public boolean Audit;

        public int Type;//新增返回  1 店铺广告 2商品广告 3跳转页面广告
        public int SupplierId;
        public int GoodsId;
        public int BusinessActivities;//新增返回   1本地 2 3全国
        public int ShopType;//新增返回   6商超士多
    }
}
