package br.ufba.dcc.meetly.models;

import android.content.ContentValues;

public class TagModel extends BaseModel
{
    private Integer id;
    private Integer userId;
    private String name;
    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
        values.put("userId",userId);
        values.put("name",name);
        values.put("color",color);
        return values;
    }
}
