package br.ufba.dcc.meetly.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "meetly";
    private static final int DB_VERSION = 1;
    private static final List<String> TABLES = Arrays.asList("user","meeting","meeting_user","tag","user_meeting_tag");
    private static final String SQL_TRUNCATE_TABLE = "DROP TABLE IF EXISTS ?; VACUUM;";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ?;";

    private static final String SQL_CREATE_USER = "CREATE TABLE IF NOT EXISTS user (\n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\temail TEXT NOT NULL,\n" +
            "\tname TEXT NOT NULL,\n" +
            "\tpass TEXT NOT NULL,\n" +
            "\tphone TEXT,\n" +
            "\tcompany TEXT,\n" +
            "\trole TEXT,\n" +
            "\tCONSTRAINT ux_user UNIQUE (email));";
    private static final String SQL_CREATE_MEETING = "CREATE TABLE IF NOT EXISTS meeting(\n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tuser_id INTEGER NOT NULL,\n" +
            "\ttitle TEXT NOT NULL,\n" +
            "\tsubject TEXT,\n" +
            "\tdate TEXT NOT NULL,\n" +
            "\ttime TEXT NOT NULL,\n" +
            "\taddress_state TEXT NOT NULL,\n" +
            "\taddress_city TEXT NOT NULL,\n" +
            "\taddress_name TEXT NOT NULL,\n" +
            "\taddress_cep TEXT NOT NULL,\n" +
            "\taddress_neighborhood TEXT,\n" +
            "\taddress_number TEXT,\n" +
            "\taddress_complement TEXT,\n" +
            "\troom TEXT,\n" +
            "\tfloor INTEGER,\n" +
            "\tFOREIGN KEY(user_id) REFERENCES user(id));";
    private static final String SQL_CREATE_MEETING_USER = "CREATE TABLE IF NOT EXISTS meeting_user(\n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tmeeting_id INTEGER NOT NULL,\n" +
            "\tuser_id INTEGER NOT NULL,\n" +
            "\tFOREIGN KEY(meeting_id) REFERENCES meeting(id),\n" +
            "\tFOREIGN KEY(user_id) REFERENCES user(id));";
    private static final String SQL_CREATE_TAG = "CREATE TABLE IF NOT EXISTS tag(\n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tuser_id INTEGER NOT NULL,\n" +
            "\tname TEXT NOT NULL,\n" +
            "\tcolor TEXT NOT NULL,\n" +
            "\tFOREIGN KEY(user_id) REFERENCES user(id));";
    private static final String SQL_CREATE_USER_MEETING_TAG = "CREATE TABLE IF NOT EXISTS user_meeting_tag(\n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tmeeting_id INTEGER NOT NULL,\n" +
            "\tuser_id INTEGER NOT NULL,\n" +
            "\ttag_id INTEGER NOT NULL,\n" +
            "\tFOREIGN KEY(meeting_id) REFERENCES meeting(id),\n" +
            "\tFOREIGN KEY(user_id) REFERENCES user(id),\n" +
            "\tFOREIGN KEY(tag_id) REFERENCES tag(id));";

    public DatabaseHelper(Context context)
    {
        this(context,DB_VERSION,null);
    }

    public DatabaseHelper(Context context, int version)
    {
        this(context,version,null);
    }

    public DatabaseHelper(Context context, int version, String tableName)
    {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqlTable;
        for (String tb : TABLES) {
            try {
                Field f = getClass().getDeclaredField("SQL_CREATE_"+tb.toUpperCase());
                f.setAccessible(true);
                sqlTable = (String) f.get(this);
            } catch(Exception e) {
                sqlTable = null;
                Log.e(TAG,e.getMessage());
            }
            if(sqlTable != null) {
                db.execSQL(sqlTable);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        for (String tb : TABLES) {
            db.execSQL(String.valueOf(SQL_DROP_TABLE).replace("?",tb));
        }
    }

    public void truncate(String table)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.valueOf(SQL_TRUNCATE_TABLE).replace("?",table));
        onCreate(db);
    }

    public static String getSqlCreateUser()
    {
        return SQL_CREATE_USER;
    }

    public static String getSqlCreateMeeting()
    {
        return SQL_CREATE_MEETING;
    }

    public static String getSqlCreateMeetingUser()
    {
        return SQL_CREATE_MEETING_USER;
    }

    public static String getSqlCreateTag()
    {
        return SQL_CREATE_TAG;
    }

    public static String getSqlCreateUserMeetingTag()
    {
        return SQL_CREATE_USER_MEETING_TAG;
    }
}
