package com.fdl.activity.supermarket;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/7/26<p>
 * <p>changeTime：2019/7/26<p>
 * <p>version：1<p>
 */
public class StoreDetailsUpDateEvent {
    private boolean isUpdateDb;

    public boolean isUpdateDb() {
        return isUpdateDb;
    }

    public void setUpdateDb(boolean updateDb) {
        isUpdateDb = updateDb;
    }
}
