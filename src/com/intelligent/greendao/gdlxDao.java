package com.intelligent.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table GDLX.
*/
public class gdlxDao extends AbstractDao<gdlx, Long> {

    public static final String TABLENAME = "GDLX";

    /**
     * Properties of entity gdlx.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Gdlxcode = new Property(0, Long.class, "gdlxcode", true, "GDLXCODE");
        public final static Property Gdlxname = new Property(1, String.class, "gdlxname", false, "GDLXNAME");
    };


    public gdlxDao(DaoConfig config) {
        super(config);
    }
    
    public gdlxDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'GDLX' (" + //
                "'GDLXCODE' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: gdlxcode
                "'GDLXNAME' TEXT);"); // 1: gdlxname
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GDLX'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, gdlx entity) {
        stmt.clearBindings();
 
        Long gdlxcode = entity.getGdlxcode();
        if (gdlxcode != null) {
            stmt.bindLong(1, gdlxcode);
        }
 
        String gdlxname = entity.getGdlxname();
        if (gdlxname != null) {
            stmt.bindString(2, gdlxname);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public gdlx readEntity(Cursor cursor, int offset) {
        gdlx entity = new gdlx( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // gdlxcode
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // gdlxname
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, gdlx entity, int offset) {
        entity.setGdlxcode(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGdlxname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(gdlx entity, long rowId) {
        entity.setGdlxcode(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(gdlx entity) {
        if(entity != null) {
            return entity.getGdlxcode();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
