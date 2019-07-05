package com.fdl.updata;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/8<p>
 * <p>changeTime：2018/12/8<p>
 * <p>version：1<p>
 */
public interface IProgressLisenr {
    /***
     * @param bytesRead     已读取字节数
     * @param contentLength 总的字节数
     */
    void inProgress(long bytesRead, long contentLength);
}
