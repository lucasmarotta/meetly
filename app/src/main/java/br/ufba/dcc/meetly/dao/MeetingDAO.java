package br.ufba.dcc.meetly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.models.UserModel;

/**
 * Created by StormTrooper on 3/27/2017.
 */

public class MeetingDAO extends BaseDAO
{
    public String SQL_CREATE_ENTRIES;
    public String SQL_DELETE_ENTRIES;

    public MeetingDAO(Context context)
    {
        super(context);
    }


    @Override
    protected MeetingModel getModel(Cursor c)
    {
        MeetingModel meeting = new MeetingModel();
        meeting.setId(c.getInt(c.getColumnIndex("id")));
        meeting.setTitle(c.getString(c.getColumnIndex("title")));
        meeting.setSubject(c.getString(c.getColumnIndex("subject")));
        meeting.setDate(c.getString(c.getColumnIndex("date")));
        meeting.setTime(c.getString(c.getColumnIndex("time")));
        return meeting;
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
                "\ttitle TEXT,\n" +
                "\tsubject TEXT,\n" +
                "\tdate TEXT,\n" +
                "\ttime TEXT\n";


        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+tableName+";";

    }
}
