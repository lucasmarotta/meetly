package br.ufba.dcc.meetly.models;

import android.content.ContentValues;

public class MeetingUserModel extends BaseModel
{
    private Integer id;
    private Integer meetingId;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Integer getPrimaryKey()
    {
        return id;
    }

    @Override
    public void setPrimaryKey(Integer primaryKey)
    {
        setId((Integer) primaryKey);
    }

    @Override
    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put("meetingId",meetingId);
        values.put("userId",userId);
        return values;
    }
}
