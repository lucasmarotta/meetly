package br.ufba.dcc.meetly.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.ufba.dcc.meetly.R;

public class LoginActivity extends AppCompatActivity
{
    /**
     * Initialize Activity
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Handle the login action
     * @param v Login view
     */
    public void loginAction(View v)
    {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * Handle the no account text action
     * @param v Login view
     */
    public void noAccountAction(View v)
    {

    }
}
