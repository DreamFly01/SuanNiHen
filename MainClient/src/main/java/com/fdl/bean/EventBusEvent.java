package com.fdl.bean;

/**
 * Administrator on 2019/5/31 0031 16:48
 * jy
 * 48
 */
public class EventBusEvent<T> {
    private int code;
    private T data;

    public EventBusEvent(int code) {
        this.code = code;
    }

    public EventBusEvent(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
