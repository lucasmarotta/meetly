package br.ufba.dcc.meetly.dao;

import android.content.Context;
import android.database.Cursor;

import br.ufba.dcc.meetly.models.MeetingModel;

public class MeetingDAO extends BaseDAO
{

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
}
