package br.ufba.dcc.meetly.utils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

public class ConnectionFactory
{
    private static HashMap<String,ConnectionDefinition> conns = new HashMap<String,ConnectionDefinition>();
    private static final String TAG = ConnectionFactory.class.getSimpleName();

    /**
     * Map a ConnectionDefinitio to a alias
     * @param alias connection alias
     * @param connDef connection definition
     */
    public static void addConnectionDefinition(String alias, ConnectionDefinition connDef)
    {
        if(!conns.containsKey(alias)) {
            conns.put(alias,connDef);
        }
    }

    /**
     * Map or replacce a ConnectionDefinition to a alias
     * @param alias connection alias
     * @param connDef connection definition
     */
    public static void setConnectionDefinition(String alias, ConnectionDefinition connDef)
    {
        conns.put(alias,connDef);
    }

    /**
     * Get Connection Definition by alias
     * @param alias connection alias
     * @return ConnectionDefinition
     */
    public static ConnectionDefinition getConnectionDefinition(String alias)
    {
        return conns.get(alias);
    }

    /**
     * Unset a ConnectionDefinition by a alias
     * @param alias connection alias
     */
    public static void unsetConnectionDefinition(String alias)
    {
        if(conns.containsKey(alias)) {
            conns.remove(alias);
        }
    }

    /**
     * Get a Connection by alias
     * @param alias connection alias
     * @return Connection
     */
    public static Connection getConnectionByAlias(String alias)
    {
        if(conns.containsKey(alias))
        {
            ConnectionDefinition connDef = conns.get(alias);
            try {
                Class.forName(connDef.getDriver()).newInstance();
                return DriverManager.getConnection(connDef.getUrl(),connDef.getUser(),connDef.getPass());
            } catch(Exception e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage());
            }
        }
        return null;
    }


}
