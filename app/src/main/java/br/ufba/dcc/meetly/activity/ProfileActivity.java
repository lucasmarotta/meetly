package br.ufba.dcc.meetly.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.dao.UserDAO;
import br.ufba.dcc.meetly.helper.UserFormHelper;
import br.ufba.dcc.meetly.models.UserModel;

public class ProfileActivity extends AppCompatActivity
{
    private UserModel user = null;
    private View rootView;
    private UserFormHelper userFormHelper;
    private UserDAO userDAO;
    private String profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        rootView = findViewById(R.id.activity_profile);
        userFormHelper = new UserFormHelper(rootView);
        userDAO = new UserDAO(this);
        setSessionUser();
        /*Intent intent = getIntent();
        if(intent != null)
        {
            user = (UserModel) intent.getSerializableExtra("user");
            if(user != null) {
                userFormHelper.setUser(user);
            }
        }*/
    }

    /**
     * Context Menu options listener
     * @param menu context menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options_profile, menu);
        return true;
    }

    /**
     * Handle the drawer navigation for save profiles
     * @param item item from navigation drawer
     */
    public void saveProfileAction(MenuItem item)
    {
        boolean newUser = (user == null);

        if(userFormHelper.validateForm(newUser)) {
            if(newUser) {
                user = userFormHelper.getUser();
                if(!userDAO.isUserExists(user)) {
                    if(userDAO.store(user)) {
                        Toast.makeText(this, "Usuário Casdastrado!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Não foi possível cadastrar o usuário. Tente mais tarde", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                    startActivity(intent);
                } else {
                    showDialog("Aviso","Email já está sendo usado por outro usuário");
                }
            } else {
                Integer id = user.getId();
                user = userFormHelper.getUser();
                user.setId(id);
                if(profileEmail != null) {
                    if(profileEmail.equalsIgnoreCase(user.getEmail()) || !userDAO.isUserExists(user)) {
                        if(userDAO.update(user)) {
                            if(!profileEmail.equalsIgnoreCase(user.getEmail())) {
                                SharedPreferences.Editor editor = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE).edit();
                                editor.putString("email",user.getEmail());
                                editor.apply();
                                profileEmail = user.getEmail();
                            }
                            Toast.makeText(this, "Usuário atualizado!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro ao atualizar o usuário. Tente mais tarde", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                        startActivity(intent);
                    } else {
                        showDialog("Aviso","Email já está sendo usado por outro usuário");
                    }
                } else {
                    Toast.makeText(this, "Erro ao atualizar o usuário. Tente mais tarde", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

        } else {
            showDialog("Aviso","Existem erros no formulário que requer sua atenção.");
        }
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

    private void setSessionUser()
    {
        SharedPreferences sharedPref = this.getSharedPreferences(this.getPackageName(),Context.MODE_PRIVATE);
        this.profileEmail = sharedPref.getString("email",null);
        if(profileEmail != null) {
            user = userDAO.getUserByEmail(profileEmail);
            userFormHelper.setUser(user);
        } else {
            user = null;
        }
    }

    public String getProfileEmail()
    {
        return profileEmail;
    }
}
