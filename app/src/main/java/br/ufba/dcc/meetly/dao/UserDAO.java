package br.ufba.dcc.meetly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import br.ufba.dcc.meetly.models.UserModel;

public class UserDAO extends BaseDAO
{
    private static final String SQL_USER_EXISTS = "SELECT id FROM user WHERE email = ?";
    private static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";

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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(SQL_USER_EXISTS,new String[] {String.valueOf(model.getEmail())});
        boolean result = c.moveToFirst();
        c.close();
        return result;
    }

    public UserModel getUserByEmail(String email)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
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
}
