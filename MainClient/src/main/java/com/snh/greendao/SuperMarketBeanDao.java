package com.snh.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fdl.bean.SuperMarketBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SUPER_MARKET_BEAN".
*/
public class SuperMarketBeanDao extends AbstractDao<SuperMarketBean, Long> {

    public static final String TABLENAME = "SUPER_MARKET_BEAN";

    /**
     * Properties of entity SuperMarketBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SuperMarketId = new Property(0, Long.class, "superMarketId", true, "_id");
        public final static Property SupplierId = new Property(1, int.class, "SupplierId", false, "SUPPLIER_ID");
        public final static Property SupplierName = new Property(2, String.class, "SupplierName", false, "SUPPLIER_NAME");
        public final static Property Icon = new Property(3, String.class, "Icon", false, "ICON");
        public final static Property Address = new Property(4, String.class, "Address", false, "ADDRESS");
        public final static Property Longitude = new Property(5, String.class, "Longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(6, String.class, "Latitude", false, "LATITUDE");
        public final static Property Evaluate = new Property(7, double.class, "Evaluate", false, "EVALUATE");
        public final static Property Distance = new Property(8, String.class, "Distance", false, "DISTANCE");
        public final static Property ItemType = new Property(9, int.class, "itemType", false, "ITEM_TYPE");
        public final static Property ShopType = new Property(10, int.class, "shopType", false, "SHOP_TYPE");
    }


    public SuperMarketBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SuperMarketBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SUPER_MARKET_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: superMarketId
                "\"SUPPLIER_ID\" INTEGER NOT NULL ," + // 1: SupplierId
                "\"SUPPLIER_NAME\" TEXT," + // 2: SupplierName
                "\"ICON\" TEXT," + // 3: Icon
                "\"ADDRESS\" TEXT," + // 4: Address
                "\"LONGITUDE\" TEXT," + // 5: Longitude
                "\"LATITUDE\" TEXT," + // 6: Latitude
                "\"EVALUATE\" REAL NOT NULL ," + // 7: Evaluate
                "\"DISTANCE\" TEXT," + // 8: Distance
                "\"ITEM_TYPE\" INTEGER NOT NULL ," + // 9: itemType
                "\"SHOP_TYPE\" INTEGER NOT NULL );"); // 10: shopType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SUPER_MARKET_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SuperMarketBean entity) {
        stmt.clearBindings();
 
        Long superMarketId = entity.getSuperMarketId();
        if (superMarketId != null) {
            stmt.bindLong(1, superMarketId);
        }
        stmt.bindLong(2, entity.getSupplierId());
 
        String SupplierName = entity.getSupplierName();
        if (SupplierName != null) {
            stmt.bindString(3, SupplierName);
        }
 
        String Icon = entity.getIcon();
        if (Icon != null) {
            stmt.bindString(4, Icon);
        }
 
        String Address = entity.getAddress();
        if (Address != null) {
            stmt.bindString(5, Address);
        }
 
        String Longitude = entity.getLongitude();
        if (Longitude != null) {
            stmt.bindString(6, Longitude);
        }
 
        String Latitude = entity.getLatitude();
        if (Latitude != null) {
            stmt.bindString(7, Latitude);
        }
        stmt.bindDouble(8, entity.getEvaluate());
 
        String Distance = entity.getDistance();
        if (Distance != null) {
            stmt.bindString(9, Distance);
        }
        stmt.bindLong(10, entity.getItemType());
        stmt.bindLong(11, entity.getShopType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SuperMarketBean entity) {
        stmt.clearBindings();
 
        Long superMarketId = entity.getSuperMarketId();
        if (superMarketId != null) {
            stmt.bindLong(1, superMarketId);
        }
        stmt.bindLong(2, entity.getSupplierId());
 
        String SupplierName = entity.getSupplierName();
        if (SupplierName != null) {
            stmt.bindString(3, SupplierName);
        }
 
        String Icon = entity.getIcon();
        if (Icon != null) {
            stmt.bindString(4, Icon);
        }
 
        String Address = entity.getAddress();
        if (Address != null) {
            stmt.bindString(5, Address);
        }
 
        String Longitude = entity.getLongitude();
        if (Longitude != null) {
            stmt.bindString(6, Longitude);
        }
 
        String Latitude = entity.getLatitude();
        if (Latitude != null) {
            stmt.bindString(7, Latitude);
        }
        stmt.bindDouble(8, entity.getEvaluate());
 
        String Distance = entity.getDistance();
        if (Distance != null) {
            stmt.bindString(9, Distance);
        }
        stmt.bindLong(10, entity.getItemType());
        stmt.bindLong(11, entity.getShopType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SuperMarketBean readEntity(Cursor cursor, int offset) {
        SuperMarketBean entity = new SuperMarketBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // superMarketId
            cursor.getInt(offset + 1), // SupplierId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // SupplierName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Icon
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Address
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Longitude
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // Latitude
            cursor.getDouble(offset + 7), // Evaluate
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // Distance
            cursor.getInt(offset + 9), // itemType
            cursor.getInt(offset + 10) // shopType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SuperMarketBean entity, int offset) {
        entity.setSuperMarketId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSupplierId(cursor.getInt(offset + 1));
        entity.setSupplierName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIcon(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLongitude(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLatitude(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setEvaluate(cursor.getDouble(offset + 7));
        entity.setDistance(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setItemType(cursor.getInt(offset + 9));
        entity.setShopType(cursor.getInt(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SuperMarketBean entity, long rowId) {
        entity.setSuperMarketId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SuperMarketBean entity) {
        if(entity != null) {
            return entity.getSuperMarketId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SuperMarketBean entity) {
        return entity.getSuperMarketId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}