package com.intelligent.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BUMEN.
*/
public class bumenDao extends AbstractDao<bumen, Long> {

    public static final String TABLENAME = "BUMEN";

    /**
     * Properties of entity bumen.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Bumenid = new Property(0, Long.class, "bumenid", true, "BUMENID");
        public final static Property Fujiid = new Property(1, Integer.class, "fujiid", false, "FUJIID");
        public final static Property Bumenname = new Property(2, String.class, "bumenname", false, "BUMENNAME");
        public final static Property Miaoshu = new Property(3, String.class, "miaoshu", false, "MIAOSHU");
    };


    public bumenDao(DaoConfig config) {
        super(config);
    }
    
    public bumenDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BUMEN' (" + //
                "'BUMENID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: bumenid
                "'FUJIID' INTEGER," + // 1: fujiid
                "'BUMENNAME' TEXT," + // 2: bumenname
                "'MIAOSHU' TEXT);"); // 3: miaoshu
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BUMEN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, bumen entity) {
        stmt.clearBindings();
 
        Long bumenid = entity.getBumenid();
        if (bumenid != null) {
            stmt.bindLong(1, bumenid);
        }
 
        Integer fujiid = entity.getFujiid();
        if (fujiid != null) {
            stmt.bindLong(2, fujiid);
        }
 
        String bumenname = entity.getBumenname();
        if (bumenname != null) {
            stmt.bindString(3, bumenname);
        }
 
        String miaoshu = entity.getMiaoshu();
        if (miaoshu != null) {
            stmt.bindString(4, miaoshu);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public bumen readEntity(Cursor cursor, int offset) {
        bumen entity = new bumen( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // bumenid
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // fujiid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bumenname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // miaoshu
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, bumen entity, int offset) {
        entity.setBumenid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFujiid(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setBumenname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMiaoshu(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(bumen entity, long rowId) {
        entity.setBumenid(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(bumen entity) {
        if(entity != null) {
            return entity.getBumenid();
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
