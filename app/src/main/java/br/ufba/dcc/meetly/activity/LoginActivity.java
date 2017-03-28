package br.ufba.dcc.meetly.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.dao.UserDAO;
import br.ufba.dcc.meetly.helper.LoginFormHelper;
import br.ufba.dcc.meetly.helper.SessionHelper;
import br.ufba.dcc.meetly.models.UserModel;

;

public class LoginActivity extends AppCompatActivity
{

    private LoginFormHelper loginFormHelper;
    private View rootView;
    private SessionHelper session;
    private UserDAO userDAO;

    /**
     * Initialize Activity
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        session = new SessionHelper(this);

        setContentView(R.layout.activity_login);
        if(!session.isUserLogged()) {
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
                session.setValue(SessionHelper.USER_SESSION_PREF,user.getEmail());
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
