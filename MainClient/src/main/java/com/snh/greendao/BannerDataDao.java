package com.snh.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fdl.bean.daoBean.BannerData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BANNER_DATA".
*/
public class BannerDataDao extends AbstractDao<BannerData, Void> {

    public static final String TABLENAME = "BANNER_DATA";

    /**
     * Properties of entity BannerData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, int.class, "Id", false, "ID");
        public final static Property AdminId = new Property(1, int.class, "AdminId", false, "ADMIN_ID");
        public final static Property Href = new Property(2, String.class, "Href", false, "HREF");
        public final static Property Title = new Property(3, String.class, "Title", false, "TITLE");
        public final static Property ImageUrl = new Property(4, String.class, "ImageUrl", false, "IMAGE_URL");
        public final static Property OrderBy = new Property(5, int.class, "OrderBy", false, "ORDER_BY");
        public final static Property Default = new Property(6, boolean.class, "Default", false, "DEFAULT");
        public final static Property Audit = new Property(7, boolean.class, "Audit", false, "AUDIT");
    }


    public BannerDataDao(DaoConfig config) {
        super(config);
    }
    
    public BannerDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BANNER_DATA\" (" + //
                "\"ID\" INTEGER NOT NULL ," + // 0: Id
                "\"ADMIN_ID\" INTEGER NOT NULL ," + // 1: AdminId
                "\"HREF\" TEXT," + // 2: Href
                "\"TITLE\" TEXT," + // 3: Title
                "\"IMAGE_URL\" TEXT," + // 4: ImageUrl
                "\"ORDER_BY\" INTEGER NOT NULL ," + // 5: OrderBy
                "\"DEFAULT\" INTEGER NOT NULL ," + // 6: Default
                "\"AUDIT\" INTEGER NOT NULL );"); // 7: Audit
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BANNER_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BannerData entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getAdminId());
 
        String Href = entity.getHref();
        if (Href != null) {
            stmt.bindString(3, Href);
        }
 
        String Title = entity.getTitle();
        if (Title != null) {
            stmt.bindString(4, Title);
        }
 
        String ImageUrl = entity.getImageUrl();
        if (ImageUrl != null) {
            stmt.bindString(5, ImageUrl);
        }
        stmt.bindLong(6, entity.getOrderBy());
        stmt.bindLong(7, entity.getDefault() ? 1L: 0L);
        stmt.bindLong(8, entity.getAudit() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BannerData entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getAdminId());
 
        String Href = entity.getHref();
        if (Href != null) {
            stmt.bindString(3, Href);
        }
 
        String Title = entity.getTitle();
        if (Title != null) {
            stmt.bindString(4, Title);
        }
 
        String ImageUrl = entity.getImageUrl();
        if (ImageUrl != null) {
            stmt.bindString(5, ImageUrl);
        }
        stmt.bindLong(6, entity.getOrderBy());
        stmt.bindLong(7, entity.getDefault() ? 1L: 0L);
        stmt.bindLong(8, entity.getAudit() ? 1L: 0L);
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public BannerData readEntity(Cursor cursor, int offset) {
        BannerData entity = new BannerData( //
            cursor.getInt(offset + 0), // Id
            cursor.getInt(offset + 1), // AdminId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Href
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Title
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // ImageUrl
            cursor.getInt(offset + 5), // OrderBy
            cursor.getShort(offset + 6) != 0, // Default
            cursor.getShort(offset + 7) != 0 // Audit
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BannerData entity, int offset) {
        entity.setId(cursor.getInt(offset + 0));
        entity.setAdminId(cursor.getInt(offset + 1));
        entity.setHref(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImageUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOrderBy(cursor.getInt(offset + 5));
        entity.setDefault(cursor.getShort(offset + 6) != 0);
        entity.setAudit(cursor.getShort(offset + 7) != 0);
     }
    
    @Override
    protected final Void updateKeyAfterInsert(BannerData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(BannerData entity) {
        return null;
    }

    @Override
    public boolean hasKey(BannerData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}