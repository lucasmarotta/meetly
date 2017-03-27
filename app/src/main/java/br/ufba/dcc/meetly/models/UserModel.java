package br.ufba.dcc.meetly.models;

import android.content.ContentValues;

import java.io.Serializable;


public class UserModel extends BaseModel implements Serializable
{
    private Integer id;
    private String email;
    private String name;
    private String pass;
    private String phone;
    private String company;
    private String role;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = pass;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
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
        values.put("email", email);
        values.put("name", name);
        values.put("phone", phone);
        values.put("company", company);
        values.put("role", role);
        return values;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
