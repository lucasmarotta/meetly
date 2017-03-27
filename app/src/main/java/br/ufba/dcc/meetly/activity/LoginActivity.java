package br.ufba.dcc.meetly.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.dao.UserDAO;
import br.ufba.dcc.meetly.helper.LoginFormHelper;
import br.ufba.dcc.meetly.models.UserModel;

;

public class LoginActivity extends AppCompatActivity
{

    private LoginFormHelper loginFormHelper;
    private View rootView;
    private UserDAO userDAO;

    /**
     * Initialize Activity
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(!checkSession()) {
            rootView = findViewById(R.id.activity_login);
            userDAO = new UserDAO(this);
            loginFormHelper = new LoginFormHelper(rootView);
        } else {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Handle the login action
     * @param v Login view
     */
    public void loginAction(View v)
    {
        if(loginFormHelper.validateForm()) {
            UserModel user = loginFormHelper.getUser();
            if(userDAO.checkUserAuth(user)) {

                SharedPreferences.Editor editor = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE).edit();
                editor.putString("email",user.getEmail());
                editor.apply();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            } else {
                showDialog("Aviso","Email e/ou incorretos.");
            }
        } else {
            showDialog("Aviso","Existem erros no formulário que requer sua atenção.");
        }
    }

    /**
     * Handle the no account text action
     * @param v Login view
     */
    public void noAccountAction(View v)
    {
        Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    public void showDialog(String title, String msg)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public boolean checkSession()
    {
        SharedPreferences sharedPref = this.getSharedPreferences(this.getPackageName(),Context.MODE_PRIVATE);
        String email = sharedPref.getString("email",null);
        return (email != null);
    }


    /*private void initConnection()
    {
        ConnectionDefinition connDef = new ConnectionDefinition("com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.0.1:3306/meetly","root","root");
        ConnectionFactory.addConnectionDefinition("meetly",connDef);
        initConnection = true;

        System.out.println(ConnectionFactory.getConnectionDefinition("meetly"));

        AsyncTask<Void,Void,Void> testTask = new AsyncTask()<Void,Void,Void> {
            @Override
            protected void doInBackground()
            {
                Connection conn = ConnectionFactory.getConnectionByAlias("meetly");
            }
        };
    }*/

}
