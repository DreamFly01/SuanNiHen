package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/8<p>
 * <p>changeTime：2019/1/8<p>
 * <p>version：1<p>
 */
public class RefundOrderBean {
    public int orderId;
    public int SupplierId;
    public int UserId;
    public String  CustomerNo;
    public String ApplyTime;
    public String OrderNo;
    public String returnReason;
    public double returnMoney;
    public String returnRemark;
    public String img1;
    public String img2;
    public String img3;

    public String ApplyPsn;
    public String ApplyTel;
    public String OrderGoodsId;
    public String Express;
    public String ExpressNo;
    public String State;
    public String Flag;
    public String CreateTime;

    public List<SerBean> seriallist;
    public List<GoodsBean> goodslist;

    public class SerBean{
        public int Id;
        public String OrderNo;
        public int State;
        public String OperaPsn;
        public String Commit;
        public int ReturnOrderID;
        public String CreateTime;

    }

}
