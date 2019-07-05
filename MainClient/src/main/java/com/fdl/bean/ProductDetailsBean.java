package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/24<p>
 * <p>changeTime：2018/12/24<p>
 * <p>version：1<p>
 */
public class ProductDetailsBean {
    public String RollImages; //滚动图
    public double MarketPrice; //市场价
    public double SalesPrice;//销售价
    public int MarketNumber;
    public int FollowNum;
    public List<Suk> SKU;
    public int Inventory;
    public double Weight;//重量
    public int SupplierId;//店铺id
    public int ExpressTemplateId;//快递模版id
    public boolean IsFollow;//是否关注店铺
    public String DetailUrl;
    public String CoverImgId;
    public String XCXUrl;
    public String Name;
    public double MonthSaleNumber;//月销量
    public double ExpressMoney;//运费

    public Shop shop;
    public String Describe;//商品详情string页面

    public class Shop {
        public int Id;
        public String ShopName;//店铺名字
        public String ShopLogo;//店铺logo url
        public int Level;//店铺等级
        public boolean IsFollow;//用户是否关注此店铺
        public int FollowNum;//该店铺被关注总数
        public String ShopUrl;
        public String ShopType;
        public String XCXUrl;
    }

    public class Suk {
        public ShopNormsNamesView ShopNormsNamesView;
        public List<ShopNormsValuesListView> ShopNormsValuesListView;

    }

    public class ShopNormsNamesView {
        public String NormName;
    }

    public class ShopNormsValuesListView {
        public String NormValue;
        public int Id;//规格id
        public int SupplierId;
        public int ShopNormNameId;//商品id
    }
}
