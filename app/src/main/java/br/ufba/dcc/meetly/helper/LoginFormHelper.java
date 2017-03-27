package br.ufba.dcc.meetly.helper;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.validator.GenericValidator;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.models.UserModel;

/**
 * Created by Lucas on 26/03/2017.
 */

public class LoginFormHelper
{
    View view;
    Context context;
    EditText email;
    EditText pass;

    public LoginFormHelper(View view)
    {
        this.view = view;
        context = view.getContext();
        init();
    }

    private void init()
    {
        email = (EditText) view.findViewById(R.id.login_form_email);
        pass = (EditText) view.findViewById(R.id.login_form_pass);
    }

    public UserModel getUser()
    {
        UserModel user = new UserModel();
        user.setEmail(email.getText().toString());
        user.setPass(pass.getText().toString());
        return user;
    }

    public boolean validateForm()
    {
        boolean validation = true;

        if(GenericValidator.isBlankOrNull(email.getText().toString())) {
            email.setError("Preencha o email");
            validation = false;
        } else if(!ValidationHelper.email(context,email.getText().toString())) {
            email.setError("Email inválido");
            validation = false;
        }

        if(!ValidationHelper.pass(context, pass.getText().toString())) {
            pass.setError("Senha inválida");
            validation = false;
        }

        return validation;
    }
}
