package br.ufba.dcc.meetly.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.ufba.dcc.meetly.models.MeetingModel;

public class MeetingDAO extends BaseDAO
{
    private static final String GET_ACTIVE_MEETINGS = "SELECT m.*, t.color AS color\n" +
            "FROM meeting AS m\n" +
            "LEFT JOIN user_meeting_tag AS umt ON m.user_id = umt.user_id AND m.id = umt.meeting_id\n" +
            "LEFT JOIN tag AS t ON umt.tag_id = t.id\n" +
            "WHERE m.date || ' ' || m.time >= strftime('%Y-%m-%d %H:%M', CURRENT_TIMESTAMP)";

    public MeetingDAO(Context context)
    {
        super(context);
    }

    public ArrayList<MeetingModel> getActiveMeetings()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(GET_ACTIVE_MEETINGS,null);
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
        meeting.setSubject(c.getString(c.getColumnIndex("subject")));
        meeting.setDate(c.getString(c.getColumnIndex("date")));
        meeting.setTime(c.getString(c.getColumnIndex("time")));
        meeting.setAddressState(c.getString(c.getColumnIndex("address_name")));
        meeting.setAddressCity(c.getString(c.getColumnIndex("address_city")));
        meeting.setAddressCep(c.getString(c.getColumnIndex("address_cep")));
        meeting.setUserId(c.getInt(c.getColumnIndex("user_id")));
        meeting.setAddressNeighborhood(c.getString(c.getColumnIndex("address_neighborhood")));
        meeting.setAddressComplement(c.getString(c.getColumnIndex("address_complement")));
        meeting.setAddressNumber(c.getString(c.getColumnIndex("address_number")));
        meeting.setFloor(c.getString(c.getColumnIndex("floor")));
        meeting.setRoom(c.getString(c.getColumnIndex("room")));
        return meeting;
    }
}
