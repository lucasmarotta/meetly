package br.ufba.dcc.meetly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import br.ufba.dcc.meetly.models.UserModel;

/**
 * Created by Lucas on 26/03/2017.
 */

public class UserDAO extends BaseDAO
{

    public String SQL_CREATE_ENTRIES;
    public String SQL_DELETE_ENTRIES;
    public String SQL_USER_EXISTS;
    public String SQL_GET_USER_BY_EMAIL;

    public UserDAO(Context context)
    {
        super(context);
    }

    /* só existe para fazer o hash da senha */
    public boolean store(UserModel model)
    {
        String pass = new String(Hex.encodeHex(DigestUtils.md5(model.getPass())));
        ContentValues values = model.getContentValues();
        values.put("pass",pass);
        boolean result = super.store(model,values);
        model.setPass("");
        return result;
    }

    public boolean update(UserModel model)
    {
        boolean result = false;
        if(model.getPass().isEmpty()) {
            result = super.update(model);
        } else {
            String pass = DigestUtils.md5Hex(model.getPass());
            ContentValues values = model.getContentValues();
            values.put("pass",pass);
            result = super.update(model,values);
        }
        model.setPass("");
        return result;
    }

    public boolean isUserExists(UserModel model)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(SQL_USER_EXISTS,new String[] {String.valueOf(model.getEmail())});
        boolean result = c.moveToFirst();
        c.close();
        return result;
    }

    public UserModel getUserByEmail(String email)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(SQL_GET_USER_BY_EMAIL, new String[] {String.valueOf(email)});
        if(c.moveToFirst()) {
            return getModel(c);
        }
        return null;
    }

    public boolean checkUserAuth(UserModel model)
    {
        String pass = new String(Hex.encodeHex(DigestUtils.md5(model.getPass())));
        UserModel dbUser = getUserByEmail(model.getEmail());
        if(dbUser != null && dbUser.getPass() != null && dbUser.getPass().equals(pass)) {
            return true;
        }
        return false;
    }


    @Override
    protected UserModel getModel(Cursor c)
    {
        UserModel user = new UserModel();
        user.setId(c.getInt(c.getColumnIndex("id")));
        user.setEmail(c.getString(c.getColumnIndex("email")));
        user.setPass(c.getString(c.getColumnIndex("pass")));
        user.setName(c.getString(c.getColumnIndex("name")));
        user.setPhone(c.getString(c.getColumnIndex("phone")));
        user.setCompany(c.getString(c.getColumnIndex("company")));
        user.setRole(c.getString(c.getColumnIndex("role")));
        return user;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    protected void setSQLVars()
    {
        SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS "+tableName+" (\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\temail TEXT NOT NULL,\n" +
                "\tname TEXT NOT NULL,\n" +
                "\tpass TEXT NOT NULL,\n" +
                "\tphone TEXT,\n" +
                "\tcompany TEXT,\n" +
                "\trole TEXT,\n" +
                "\tCONSTRAINT ux_"+tableName+" UNIQUE (email));";

        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+tableName+";";
        SQL_USER_EXISTS = "SELECT email FROM "+tableName+" WHERE email = ?";
        SQL_GET_USER_BY_EMAIL = "SELECT * FROM "+tableName+" WHERE email = ?";
    }
}
