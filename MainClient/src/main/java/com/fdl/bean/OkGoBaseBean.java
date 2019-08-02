package com.fdl.bean;

/**
 * @author 陈自强
 * @date 2019/7/30
 */
public class OkGoBaseBean<T> {

    public String msg;
    public String code;
    public T data;

    public OkGoBaseBean(String msg, String code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
}
