package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class WoolBean {
    public int Id;
    public int SupplierId;
    public int GoodsId;
    public String OrderNo;
    public int Number;
    public String NormsId;
    public int UserId;
    public String NormsInfo;
    public double BargainPrice;
    public long EndCutDownTime;
    public int IsOver;
    public int State;
    public double SalesPrice;
    public double MarketPrice;
    public double Cheap;
    public int CutDownTime;
    public String GoodsName;
    public String Unit;
    public String NowTime;
    public String CoverImg;
    public String Describe;
    public String RollImg;
    public String detailUrl;
    public String XCXUrl;

    public List<HymBean> userlist;
    public class HymBean{
        public int Id;
        public int BargainId;
        public int UserId;
        public double BargainPrice;
        public String OrderNo;
        public int ReturnState;
        public double BK_BargainPrice;
        public int BargainsType;
        public String WxNickName;
        public String WxHeadImg;
    }
}
