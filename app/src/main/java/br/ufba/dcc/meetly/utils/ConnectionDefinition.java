package br.ufba.dcc.meetly.utils;

public class ConnectionDefinition
{
    private String driver;
    private String url;
    private String user;
    private String pass;

    public ConnectionDefinition(String driver, String url, String user, String pass)
    {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "ConnectionDefinition{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
