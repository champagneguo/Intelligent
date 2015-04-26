package com.intelligent.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.intelligent.greendao.guandian;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table GUANDIAN.
*/
public class guandianDao extends AbstractDao<guandian, Long> {

    public static final String TABLENAME = "GUANDIAN";

    /**
     * Properties of entity guandian.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Guandiancode = new Property(0, Long.class, "guandiancode", true, "GUANDIANCODE");
        public final static Property Guanlei = new Property(1, String.class, "guanlei", false, "GUANLEI");
        public final static Property Guanxianqidianhao = new Property(2, String.class, "guanxianqidianhao", false, "GUANXIANQIDIANHAO");
        public final static Property Tezheng = new Property(3, String.class, "tezheng", false, "TEZHENG");
        public final static Property Fushuwu = new Property(4, String.class, "fushuwu", false, "FUSHUWU");
        public final static Property Dianleixing = new Property(5, String.class, "dianleixing", false, "DIANLEIXING");
        public final static Property Jingleixing = new Property(6, String.class, "jingleixing", false, "JINGLEIXING");
        public final static Property Daolumingcheng = new Property(7, String.class, "daolumingcheng", false, "DAOLUMINGCHENG");
        public final static Property Jingdu = new Property(8, String.class, "jingdu", false, "JINGDU");
        public final static Property Weidu = new Property(9, String.class, "weidu", false, "WEIDU");
        public final static Property Dimiangaocheng = new Property(10, String.class, "dimiangaocheng", false, "DIMIANGAOCHENG");
        public final static Property Guandiangaocheng = new Property(11, String.class, "guandiangaocheng", false, "GUANDIANGAOCHENG");
        public final static Property Biaoqiancode = new Property(12, String.class, "biaoqiancode", false, "BIAOQIANCODE");
        public final static Property Bangdingshijian = new Property(13, String.class, "bangdingshijian", false, "BANGDINGSHIJIAN");
        public final static Property Beizhu = new Property(14, String.class, "beizhu", false, "BEIZHU");
    };


    public guandianDao(DaoConfig config) {
        super(config);
    }
    
    public guandianDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'GUANDIAN' (" + //
                "'GUANDIANCODE' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: guandiancode
                "'GUANLEI' TEXT," + // 1: guanlei
                "'GUANXIANQIDIANHAO' TEXT," + // 2: guanxianqidianhao
                "'TEZHENG' TEXT," + // 3: tezheng
                "'FUSHUWU' TEXT," + // 4: fushuwu
                "'DIANLEIXING' TEXT," + // 5: dianleixing
                "'JINGLEIXING' TEXT," + // 6: jingleixing
                "'DAOLUMINGCHENG' TEXT," + // 7: daolumingcheng
                "'JINGDU' TEXT," + // 8: jingdu
                "'WEIDU' TEXT," + // 9: weidu
                "'DIMIANGAOCHENG' TEXT," + // 10: dimiangaocheng
                "'GUANDIANGAOCHENG' TEXT," + // 11: guandiangaocheng
                "'BIAOQIANCODE' TEXT," + // 12: biaoqiancode
                "'BANGDINGSHIJIAN' TEXT," + // 13: bangdingshijian
                "'BEIZHU' TEXT);"); // 14: beizhu
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GUANDIAN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, guandian entity) {
        stmt.clearBindings();
 
        Long guandiancode = entity.getGuandiancode();
        if (guandiancode != null) {
            stmt.bindLong(1, guandiancode);
        }
 
        String guanlei = entity.getGuanlei();
        if (guanlei != null) {
            stmt.bindString(2, guanlei);
        }
 
        String guanxianqidianhao = entity.getGuanxianqidianhao();
        if (guanxianqidianhao != null) {
            stmt.bindString(3, guanxianqidianhao);
        }
 
        String tezheng = entity.getTezheng();
        if (tezheng != null) {
            stmt.bindString(4, tezheng);
        }
 
        String fushuwu = entity.getFushuwu();
        if (fushuwu != null) {
            stmt.bindString(5, fushuwu);
        }
 
        String dianleixing = entity.getDianleixing();
        if (dianleixing != null) {
            stmt.bindString(6, dianleixing);
        }
 
        String jingleixing = entity.getJingleixing();
        if (jingleixing != null) {
            stmt.bindString(7, jingleixing);
        }
 
        String daolumingcheng = entity.getDaolumingcheng();
        if (daolumingcheng != null) {
            stmt.bindString(8, daolumingcheng);
        }
 
        String jingdu = entity.getJingdu();
        if (jingdu != null) {
            stmt.bindString(9, jingdu);
        }
 
        String weidu = entity.getWeidu();
        if (weidu != null) {
            stmt.bindString(10, weidu);
        }
 
        String dimiangaocheng = entity.getDimiangaocheng();
        if (dimiangaocheng != null) {
            stmt.bindString(11, dimiangaocheng);
        }
 
        String guandiangaocheng = entity.getGuandiangaocheng();
        if (guandiangaocheng != null) {
            stmt.bindString(12, guandiangaocheng);
        }
 
        String biaoqiancode = entity.getBiaoqiancode();
        if (biaoqiancode != null) {
            stmt.bindString(13, biaoqiancode);
        }
 
        String bangdingshijian = entity.getBangdingshijian();
        if (bangdingshijian != null) {
            stmt.bindString(14, bangdingshijian);
        }
 
        String beizhu = entity.getBeizhu();
        if (beizhu != null) {
            stmt.bindString(15, beizhu);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public guandian readEntity(Cursor cursor, int offset) {
        guandian entity = new guandian( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // guandiancode
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // guanlei
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // guanxianqidianhao
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // tezheng
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // fushuwu
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // dianleixing
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // jingleixing
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // daolumingcheng
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // jingdu
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // weidu
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // dimiangaocheng
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // guandiangaocheng
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // biaoqiancode
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // bangdingshijian
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // beizhu
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, guandian entity, int offset) {
        entity.setGuandiancode(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGuanlei(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGuanxianqidianhao(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTezheng(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFushuwu(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDianleixing(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setJingleixing(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDaolumingcheng(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setJingdu(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setWeidu(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDimiangaocheng(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setGuandiangaocheng(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setBiaoqiancode(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setBangdingshijian(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setBeizhu(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(guandian entity, long rowId) {
        entity.setGuandiancode(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(guandian entity) {
        if(entity != null) {
            return entity.getGuandiancode();
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