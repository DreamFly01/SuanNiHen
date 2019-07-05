package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/10<p>
 * <p>changeTime：2019/1/10<p>
 * <p>version：1<p>
 */
public class ShopDetailsBean {
    public int Id;
    public String ShopName;
    public String ShopLogo;
    public String Level;
    public boolean IsFollow;
    public int FollowNum;
    public String ShopUrl;
    public List<GoodsBean> goodsList;
    public String XCXUrl;
}
