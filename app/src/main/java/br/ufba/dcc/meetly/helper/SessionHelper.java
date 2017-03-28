package br.ufba.dcc.meetly.helper;

import android.content.Context;
import android.content.SharedPreferences;

import br.ufba.dcc.meetly.dao.UserDAO;
import br.ufba.dcc.meetly.models.UserModel;

public class SessionHelper
{
    private SharedPreferences sharedPref;
    private Context context;
    private String prefName;
    private UserDAO userDAO;
    public static  final String USER_SESSION_PREF = "session.email";

    public SessionHelper(Context context)
    {
        this.context = context;
        prefName = getClass().getPackage().getName();
        userDAO = new UserDAO(context);
        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public void setValue(String pair, String value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit();
        editor.putString(pair,value);
        editor.apply();
    }

    public String getValue(String pair)
    {
        return sharedPref.getString(pair,null);
    }

    public UserModel getSessionUser()
    {
        String sessionEmail = getValue(USER_SESSION_PREF);
        if(sessionEmail != null) {
            return userDAO.getUserByEmail(sessionEmail);
        }
        return null;
    }

    public boolean isUserLogged()
    {
        return (getValue(USER_SESSION_PREF) != null);
    }

}
