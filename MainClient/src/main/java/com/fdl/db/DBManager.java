package com.fdl.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/28<p>
 * <p>changeTime：2019/1/28<p>
 * <p>version：1<p>
 */
public class DBManager {
    private final static String dbName = "test_db";
    private static DBManager mInstance;
    private static MyOpenHelper openHelper;
    private static Context context;
    private String tabName;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public DBManager(Context context, String tabName) {
        this.context = context;
        this.tabName = tabName;
        openHelper = new MyOpenHelper(context, tabName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context, dbName);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    public static SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            getInstance(context);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    public static SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new MyOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }
    /**
     * 获取DaoMaster
     *
     * 判断是否存在数据库，如果没有则创建数据库
     * @return
     */
    public static DaoMaster getDaoMaster() {
        if (null == daoMaster) {
            synchronized (DBManager.class) {
                if (null == daoMaster) {
                    MyOpenHelper helper = new MyOpenHelper(context,dbName,null);
                    daoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }
        return daoMaster;
    }
    /**
     * 获取DaoSession
     * @return
     */
    public static DaoSession getDaoSession() {
        if (null == daoSession) {
            synchronized (DBManager.class) {
                daoSession = getDaoMaster().newSession();
            }
        }

        return daoSession;
    }

    public void deleSQL() {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        CommTenant commTenant = new CommTenant();
//        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
//            daoSession.delete(commTenant);
//        daoSession.delete(commTenantDao);

        SQLiteDatabase db=getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoMaster.dropAllTables(daoMaster.getDatabase(),true);
        DaoMaster.createAllTables(daoMaster.getDatabase(),true);
    }

}
