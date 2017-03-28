package br.ufba.dcc.meetly.models;

import android.content.ContentValues;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingModel extends BaseModel
{
    private static final String TAG = MeetingModel.class.getSimpleName();
    private Integer id;
    private Integer userId;
    private String title;
    private String date;
    private String time;
    private String addressState;
    private String addressCity;
    private String addressName;
    private String addressCep;
    private String addressNeighborhood;
    private String addressNumber;
    private String addressComplement;
    private String room;
    private String floor;
    private String subject;
    private String color;
    private static final String INPUT_DATE_FORMAT = "dd/MM/yyyy";
    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getSqlDate()
    {
        if(date != null) {
            try {
                SimpleDateFormat dateParser = new SimpleDateFormat(INPUT_DATE_FORMAT);
                Date dt = dateParser.parse(date);
                SimpleDateFormat dateFormatter = new SimpleDateFormat(SQL_DATE_FORMAT);
                return dateFormatter.format(dt);
            } catch (Exception e) {
                Log.e(TAG,e.getMessage());
            }
        }
        return null;
    }

    public String getDateBySqlDate()
    {
        if(date != null) {
            try {
                SimpleDateFormat dateParser = new SimpleDateFormat(SQL_DATE_FORMAT);
                Date dt = dateParser.parse(date);
                SimpleDateFormat dateFormatter = new SimpleDateFormat(INPUT_DATE_FORMAT);
                return dateFormatter.format(dt);
            } catch (Exception e) {
                Log.e(TAG,e.getMessage());
            }
        }
        return null;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getAddressState()
    {
        return addressState;
    }

    public void setAddressState(String addressState)
    {
        this.addressState = addressState;
    }

    public String getAddressCity()
    {
        return addressCity;
    }

    public void setAddressCity(String addressCity)
    {
        this.addressCity = addressCity;
    }

    public String getAddressName()
    {
        return addressName;
    }

    public void setAddressName(String addressName)
    {
        this.addressName = addressName;
    }

    public String getAddressCep()
    {
        return addressCep;
    }

    public void setAddressCep(String addressCep)
    {
        this.addressCep = addressCep;
    }

    public String getAddressNeighborhood()
    {
        return addressNeighborhood;
    }

    public void setAddressNeighborhood(String addressNeighborhood)
    {
        this.addressNeighborhood = addressNeighborhood;
    }

    public String getAddressNumber()
    {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber)
    {
        this.addressNumber = addressNumber;
    }

    public String getAddressComplement()
    {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement)
    {
        this.addressComplement = addressComplement;
    }

    public String getRoom()
    {
        return room;
    }

    public void setRoom(String room)
    {
        this.room = room;
    }

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
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
        values.put("title",title);
        values.put("subject",subject);
        values.put("user_id",userId);
        values.put("date",date);
        values.put("time",time);
        return values;
    }
}
