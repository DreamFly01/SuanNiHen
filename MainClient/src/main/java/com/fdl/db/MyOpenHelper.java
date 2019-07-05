package com.fdl.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fdl.bean.daoBean.CommTenantType;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.snh.greendao.AearBeanDao;
import com.snh.greendao.BannerData1Dao;
import com.snh.greendao.BannerDataDao;
import com.snh.greendao.CommTenantDao;
import com.snh.greendao.CommTenantTypeDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.MainProductBeanDao;
import com.snh.greendao.ProductBeanDao;
import com.snh.greendao.SupplierBeanDao;
import com.snh.greendao.TipsDao;
import com.snh.greendao.TipsbeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/3/27<p>
 * <p>changeTime：2019/3/27<p>
 * <p>version：1<p>
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
        db.beginTransaction();
        try {
//            db.execSQL(DbConstants.CREATE_HTTP_RESPONSE_TABLE_UNIQUE_INDEX.toString());
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db,ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db,ifExists);
            }
        },MainProductBeanDao.class, BannerDataDao.class, BannerData1Dao.class
                ,AearBeanDao.class,CommTenantDao.class, CommTenantTypeDao.class,
                ProductBeanDao.class, SupplierBeanDao.class, TipsDao.class, TipsbeanDao.class);
    }
}
