package com.fdl.updata;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/8<p>
 * <p>changeTime：2018/12/8<p>
 * <p>version：1<p>
 */
public interface RequestResponseListener {
    void inProgress(long bytesRead, long contentLength, boolean done);
    void onStart();
    void onRequestFailed(Exception e);
}
