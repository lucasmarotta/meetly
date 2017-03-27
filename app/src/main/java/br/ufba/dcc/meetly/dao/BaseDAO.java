package br.ufba.dcc.meetly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.common.base.CaseFormat;

import br.ufba.dcc.meetly.models.BaseModel;

public abstract class BaseDAO extends SQLiteOpenHelper
{
    private static final String DB_NAME = "meetly";
    private static final int DB_VERSION = 1;
    protected String tableName;
    protected String SQL_GET_USER_BY_PRiMARY_KEY;
    protected String SQL_TRUNCATE_ENTRIES;


    public BaseDAO(Context context)
    {
        this(context,DB_VERSION,null);
    }

    public BaseDAO(Context context, int version)
    {
        this(context,version,null);
    }

    public BaseDAO(Context context, int version, String tableName)
    {
        super(context, DB_NAME, null, version);
        this.setTableName(tableName);
        this.setSQLVars();
        SQL_GET_USER_BY_PRiMARY_KEY = "SELECT * FROM "+this.tableName+" WHERE id = ?";
        SQL_TRUNCATE_ENTRIES = "DROP TABLE IF EXISTS "+this.tableName+"; VACUUM;";
    }

    public String getTableName()
    {
        return tableName;
    }

    public boolean store(BaseModel model)
    {
        return store(model,model.getContentValues());
    }

    public boolean store(BaseModel model, ContentValues values)
    {
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(tableName,null,values);
        return (result != -1);
    }

    public boolean delete(BaseModel model)
    {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(tableName,"id="+model.getPrimaryKey().toString(),null);
        return (result > 0);
    }

    public boolean update(BaseModel model)
    {
        return update(model,model.getContentValues());
    }

    public boolean update(BaseModel model, ContentValues values)
    {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.update(tableName,values,"id="+model.getPrimaryKey().toString(),null);
        return (result > 0);
    }

    public BaseModel getByPrimaryKey(Integer primaryKey)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(SQL_GET_USER_BY_PRiMARY_KEY,new String[] {String.valueOf(primaryKey)});
        if(c.moveToFirst()) {
            return getModel(c);
        }
        return null;
    }

    @Override
    public abstract void onCreate(SQLiteDatabase db);

    @Override
    public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    public void truncate()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_TRUNCATE_ENTRIES);
        onCreate(db);
    }

    protected abstract void setSQLVars();

    protected abstract BaseModel getModel(Cursor c);

    private void setTableName(String tableName) {
        if (tableName == null) {
            tableName = this.getClass().getSimpleName().replace("DAO", "");
            this.tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tableName);
        }
    }
}
