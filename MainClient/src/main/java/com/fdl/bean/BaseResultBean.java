package com.fdl.bean;

public class BaseResultBean<T> {
    public String msg;
    public String code;
    public T data;

    public String TotalOrderNo;//订单编号
    public double TotalMoney;//应付金额
    public double Balance;//余额
    public double Integral;//积分
    public String filepath;//更改图片之后的地址
    public String money;
    public double minTxMoney;
    public double dfmoney;
    public String tel;
    public String email;
    public int topay;
    public int tosendproduct;
    public int toreceiveproduct;
    public int toreview;
    public int careshop;
    public int caregoods;
    public BaseResultBean() {
    }

    public BaseResultBean(String error, String code, T data) {
        this.msg = error;
        this.code = code;
        this.data = data;
    }
}
