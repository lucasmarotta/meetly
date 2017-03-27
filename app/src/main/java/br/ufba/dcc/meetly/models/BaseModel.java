package br.ufba.dcc.meetly.models;

import android.content.ContentValues;

import java.io.Serializable;

public abstract class BaseModel implements Serializable
{
    public abstract Integer getPrimaryKey();

    public abstract void setPrimaryKey(Integer primaryKey);

    public abstract ContentValues getContentValues();
}
