package br.ufba.dcc.meetly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.common.base.CaseFormat;

import br.ufba.dcc.meetly.helper.DatabaseHelper;
import br.ufba.dcc.meetly.models.BaseModel;

public abstract class BaseDAO
{
    protected DatabaseHelper dbHelper;
    protected String tableName;
    protected String SQL_GET_MODEL_BY_PRiMARY_KEY = "SELECT * FROM ? WHERE id = ?";

    public BaseDAO(Context context)
    {
        this(context,null);
    }

    public BaseDAO(Context context, String tableName)
    {
        setTableName(tableName);
        dbHelper = new DatabaseHelper(context);
    }

    public boolean store(BaseModel model)
    {
        return store(model,model.getContentValues());
    }

    public boolean store(BaseModel model, ContentValues values)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insert(tableName,null,values);
        return (result != -1);
    }

    public boolean delete(BaseModel model)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(tableName,"id="+model.getPrimaryKey().toString(),null);
        return (result > 0);
    }

    public boolean update(BaseModel model)
    {
        return update(model,model.getContentValues());
    }

    public boolean update(BaseModel model, ContentValues values)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.update(tableName,values,"id="+model.getPrimaryKey().toString(),null);
        return (result > 0);
    }

    public BaseModel getByPrimaryKey(Integer primaryKey)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(SQL_GET_MODEL_BY_PRiMARY_KEY,new String[] {String.valueOf(tableName),String.valueOf(primaryKey)});
        if(c.moveToFirst()) {
            return getModel(c);
        }
        return null;
    }

    public void truncate()
    {
        dbHelper.truncate(tableName);
    }

    protected abstract BaseModel getModel(Cursor c);

    public String getTableName()
    {
        return tableName;
    }

    private void setTableName(String tableName) {
        if (tableName == null) {
            tableName = this.getClass().getSimpleName().replace("DAO", "");
            this.tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tableName);
        }
    }
}
