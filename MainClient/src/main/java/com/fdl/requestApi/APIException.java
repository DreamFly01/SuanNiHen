package com.fdl.requestApi;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/10/23<p>
 * <p>changeTime：2018/10/23<p>
 * <p>version：1<p>
 */
public class APIException extends RuntimeException {

    public int code;
    public String msg;
    public String url;

    public APIException(String erroMsg) {
        this.msg = erroMsg;
    }

    public APIException(String erroMsg,int code,String url) {
        this.code =code;
        this.msg = erroMsg;
        this.url = url;
    }
    public APIException(String erroMsg,int code) {
        this.code =code;
        this.msg = erroMsg;
    }
}
