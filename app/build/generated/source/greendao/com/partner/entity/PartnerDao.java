package com.partner.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PARTNER".
*/
public class PartnerDao extends AbstractDao<Partner, Long> {

    public static final String TABLENAME = "PARTNER";

    /**
     * Properties of entity Partner.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, String.class, "type", false, "TYPE");
        public final static Property Date = new Property(2, String.class, "date", false, "DATE");
        public final static Property Time = new Property(3, String.class, "time", false, "TIME");
        public final static Property Sleep = new Property(4, String.class, "sleep", false, "SLEEP");
        public final static Property Lightsleep = new Property(5, String.class, "lightsleep", false, "LIGHTSLEEP");
        public final static Property Sleeping = new Property(6, String.class, "sleeping", false, "SLEEPING");
        public final static Property Awake = new Property(7, String.class, "awake", false, "AWAKE");
    }


    public PartnerDao(DaoConfig config) {
        super(config);
    }
    
    public PartnerDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PARTNER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TYPE\" TEXT," + // 1: type
                "\"DATE\" TEXT," + // 2: date
                "\"TIME\" TEXT," + // 3: time
                "\"SLEEP\" TEXT," + // 4: sleep
                "\"LIGHTSLEEP\" TEXT," + // 5: lightsleep
                "\"SLEEPING\" TEXT," + // 6: sleeping
                "\"AWAKE\" TEXT);"); // 7: awake
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PARTNER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Partner entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(2, type);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(3, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
 
        String sleep = entity.getSleep();
        if (sleep != null) {
            stmt.bindString(5, sleep);
        }
 
        String lightsleep = entity.getLightsleep();
        if (lightsleep != null) {
            stmt.bindString(6, lightsleep);
        }
 
        String sleeping = entity.getSleeping();
        if (sleeping != null) {
            stmt.bindString(7, sleeping);
        }
 
        String awake = entity.getAwake();
        if (awake != null) {
            stmt.bindString(8, awake);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Partner entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(2, type);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(3, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
 
        String sleep = entity.getSleep();
        if (sleep != null) {
            stmt.bindString(5, sleep);
        }
 
        String lightsleep = entity.getLightsleep();
        if (lightsleep != null) {
            stmt.bindString(6, lightsleep);
        }
 
        String sleeping = entity.getSleeping();
        if (sleeping != null) {
            stmt.bindString(7, sleeping);
        }
 
        String awake = entity.getAwake();
        if (awake != null) {
            stmt.bindString(8, awake);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Partner readEntity(Cursor cursor, int offset) {
        Partner entity = new Partner( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // type
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // date
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // time
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // sleep
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // lightsleep
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sleeping
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // awake
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Partner entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSleep(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLightsleep(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSleeping(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAwake(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Partner entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Partner entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Partner entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
