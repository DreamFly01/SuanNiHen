package com.fdl.bean;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/16<p>
 * <p>changeTime：2019/4/16<p>
 * <p>version：1<p>
 */
public class WithdrawListBean {
    public int Id;
    public int SupplierId;
    public String OrderNo;
    public String Icon;
    public double Money;
    public long Date;
    public String Title;
    public int Type; //类型【1销售收入/2提现/3提现驳回/4平台服务费/5经验值抵扣/6提货扣除】
}
