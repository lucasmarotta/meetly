package br.ufba.dcc.meetly.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.models.UserModel;

public class MeetingDAO extends BaseDAO
{
    private static final String GET_ACTIVE_MEETINGS = "SELECT m.*, t.color AS color\n" +
            "FROM meeting AS m\n" +
            "LEFT JOIN user_meeting_tag AS umt ON m.user_id = umt.user_id AND m.id = umt.meeting_id\n" +
            "LEFT JOIN tag AS t ON umt.tag_id = t.id\n" +
            "WHERE (m.user_id = ? OR m.guest_id = ?) AND\n" +
            "m.date || ' ' || m.time >= strftime('%Y-%m-%d %H:%M', CURRENT_TIMESTAMP, 'localtime')\n" +
            "ORDER BY date, time";

    private static final String GET_ARCHIVED_MEETINGS = "SELECT m.*, t.color AS color\n" +
            "FROM meeting AS m\n" +
            "LEFT JOIN user_meeting_tag AS umt ON m.user_id = umt.user_id AND m.id = umt.meeting_id\n" +
            "LEFT JOIN tag AS t ON umt.tag_id = t.id\n" +
            "WHERE (m.user_id = ? OR m.guest_id = ?) AND\n" +
            "m.date || ' ' || m.time < strftime('%Y-%m-%d %H:%M', CURRENT_TIMESTAMP, 'localtime')\n" +
            "ORDER BY date DESC, time DESC";

    private static final String GET_ACTIVE_MEETINGS_FROM_TODAY = "SELECT m.*, t.color AS color\n" +
            "FROM meeting AS m\n" +
            "LEFT JOIN user_meeting_tag AS umt ON m.user_id = umt.user_id AND m.id = umt.meeting_id\n" +
            "LEFT JOIN tag AS t ON umt.tag_id = t.id\n" +
            "WHERE (m.user_id = ? OR m.guest_id = ?) AND\n" +
            "m.date || ' ' || m.time >= strftime('%Y-%m-%d %H:%M', CURRENT_TIMESTAMP, 'localtime') AND\n" +
            "m.date = strftime('%Y-%m-%d', CURRENT_TIMESTAMP, 'localtime')\n" +
            "ORDER BY date, time";

    private static final String GET_ARCHIVED_MEETINGS_FROM_TODAY = "SELECT m.*, t.color AS color\n" +
            "FROM meeting AS m\n" +
            "LEFT JOIN user_meeting_tag AS umt ON m.user_id = umt.user_id AND m.id = umt.meeting_id\n" +
            "LEFT JOIN tag AS t ON umt.tag_id = t.id\n" +
            "WHERE (m.user_id = ? OR m.guest_id = ?) AND\n" +
            "m.date || ' ' || m.time < strftime('%Y-%m-%d %H:%M', CURRENT_TIMESTAMP, 'localtime') AND\n" +
            "m.date = strftime('%Y-%m-%d', CURRENT_TIMESTAMP, 'localtime')\n" +
            "ORDER BY date DESC, time DESC";


    public MeetingDAO(Context context)
    {
        super(context);
    }

    public ArrayList<MeetingModel> getActiveMeetings(UserModel user)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ACTIVE_MEETINGS, new String[] {String.valueOf(user.getId()), String.valueOf(user.getId())});
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        while(c.moveToNext())
        {
            MeetingModel meeting = getModel(c);
            meeting.setColor(c.getString(c.getColumnIndex("color")));
            meetingItems.add(meeting);
        }
        return meetingItems;
    }

    public ArrayList<MeetingModel> getActiveMeetings()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ACTIVE_MEETINGS_FROM_TODAY,null);
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        while(c.moveToNext())
        {
            MeetingModel meeting = getModel(c);
            meeting.setColor(c.getString(c.getColumnIndex("color")));
            meetingItems.add(meeting);
        }
        return meetingItems;
    }

    public ArrayList<MeetingModel> getArchivedMeetings()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ARCHIVED_MEETINGS, null);
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        while(c.moveToNext())
        {
            MeetingModel meeting = getModel(c);
            meeting.setColor(c.getString(c.getColumnIndex("color")));
            meetingItems.add(meeting);
        }
        return meetingItems;
    }

    public ArrayList<MeetingModel> getArchivedMeetings(UserModel user)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ARCHIVED_MEETINGS, new String[] {String.valueOf(user.getId()), String.valueOf(user.getId())});
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        while(c.moveToNext())
        {
            MeetingModel meeting = getModel(c);
            meeting.setColor(c.getString(c.getColumnIndex("color")));
            meetingItems.add(meeting);
        }
        return meetingItems;
    }

    public ArrayList<MeetingModel> getActiveMeetingsFromToday()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ACTIVE_MEETINGS_FROM_TODAY,null);
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        while(c.moveToNext())
        {
            MeetingModel meeting = getModel(c);
            meeting.setColor(c.getString(c.getColumnIndex("color")));
            meetingItems.add(meeting);
        }
        return meetingItems;
    }

    public ArrayList<MeetingModel> getActiveMeetingsFromToday(UserModel user)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ACTIVE_MEETINGS_FROM_TODAY, new String[] {String.valueOf(user.getId()), String.valueOf(user.getId())});
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        while(c.moveToNext())
        {
            MeetingModel meeting = getModel(c);
            meeting.setColor(c.getString(c.getColumnIndex("color")));
            meetingItems.add(meeting);
        }
        return meetingItems;
    }

    public ArrayList<MeetingModel> getArchivedMeetingsFromToday()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ARCHIVED_MEETINGS_FROM_TODAY,null);
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        while(c.moveToNext())
        {
            MeetingModel meeting = getModel(c);
            meeting.setColor(c.getString(c.getColumnIndex("color")));
            meetingItems.add(meeting);
        }
        return meetingItems;
    }

    public ArrayList<MeetingModel> getArchivedMeetingsFromToday(UserModel user)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ARCHIVED_MEETINGS_FROM_TODAY,new String[] {String.valueOf(user.getId()), String.valueOf(user.getId())});
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        while(c.moveToNext())
        {
            MeetingModel meeting = getModel(c);
            meeting.setColor(c.getString(c.getColumnIndex("color")));
            meetingItems.add(meeting);
        }
        return meetingItems;
    }

    @Override
    protected MeetingModel getModel(Cursor c)
    {
        MeetingModel meeting = new MeetingModel();
        meeting.setId(c.getInt(c.getColumnIndex("id")));
        meeting.setTitle(c.getString(c.getColumnIndex("title")));
        meeting.setDate(c.getString(c.getColumnIndex("date")));
        meeting.setTime(c.getString(c.getColumnIndex("time")));
        meeting.setAddressState(c.getString(c.getColumnIndex("address_state")));
        meeting.setAddressName(c.getString(c.getColumnIndex("address_name")));
        meeting.setAddressCity(c.getString(c.getColumnIndex("address_city")));
        meeting.setAddressCep(c.getString(c.getColumnIndex("address_cep")));
        meeting.setAddressNeighborhood(c.getString(c.getColumnIndex("address_neighborhood")));
        meeting.setAddressComplement(c.getString(c.getColumnIndex("address_complement")));
        meeting.setAddressNumber(c.getString(c.getColumnIndex("address_number")));
        meeting.setFloor(c.getString(c.getColumnIndex("floor")));
        meeting.setRoom(c.getString(c.getColumnIndex("room")));
        meeting.setSubject(c.getString(c.getColumnIndex("subject")));
        meeting.setUserId(c.getInt(c.getColumnIndex("user_id")));
        meeting.setGuestId(c.getInt(c.getColumnIndex("guest_id")));
        //System.out.println(meeting);
        return meeting;
    }
}
