package br.ufba.dcc.meetly.helper;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.validator.GenericValidator;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.models.UserModel;

public class UserFormHelper
{
    private View view;
    private Context context;
    private EditText email;
    private EditText name;
    private EditText pass;
    private EditText confirmationPass;
    private EditText phone;
    private EditText company;
    private EditText role;

    public UserFormHelper(View view)
    {
        this.view = view;
        context = view.getContext();
        init();
    }

    private void init()
    {
        email = (EditText) view.findViewById(R.id.profile_email);
        pass = (EditText) view.findViewById(R.id.profile_pass);
        confirmationPass = (EditText) view.findViewById(R.id.profile_pass_confirmation);
        name = (EditText) view.findViewById(R.id.profile_name);
        phone = (EditText) view.findViewById(R.id.profile_phone);
        company = (EditText) view.findViewById(R.id.profile_company);
        role = (EditText) view.findViewById(R.id.profile_role);
    }

    public UserModel getUser()
    {
        UserModel user = new UserModel();
        user.setEmail(email.getText().toString());
        user.setPass(pass.getText().toString());
        user.setName(name.getText().toString());
        user.setPhone(phone.getText().toString());
        user.setCompany(company.getText().toString());
        user.setRole(role.getText().toString());
        return user;
    }

    public void setUser(UserModel user)
    {
        email.setText(user.getEmail());
        name.setText(user.getName());
        phone.setText(user.getPhone());
        company.setText(user.getCompany());
        role.setText(user.getRole());
    }

    public boolean validateForm(boolean newUser)
    {
        boolean validation = true;

        if(GenericValidator.isBlankOrNull(email.getText().toString())) {
            email.setError("Email é obrigatório");
            validation = false;
        } else if(!ValidationHelper.email(context,email.getText().toString())) {
            email.setError("Email inválido");
            validation = false;
        }

        if(newUser) {
            if(GenericValidator.isBlankOrNull(pass.getText().toString())) {
                pass.setError("Senha é obrigatória");
                validation = false;
            } else if(!ValidationHelper.pass(context, pass.getText().toString())) {
                pass.setError("Senha inválida");
                validation = false;
            } else if(!ValidationHelper.confirmationPass(pass.getText().toString(),confirmationPass.getText().toString())) {
                pass.setError("Senhas não correspondem");
            }

            if(GenericValidator.isBlankOrNull(confirmationPass.getText().toString())) {
                confirmationPass.setError("Senha é obrigatória");
                validation = false;
            } else if(!ValidationHelper.pass(context, confirmationPass.getText().toString())) {
                confirmationPass.setError("Senha inválida");
                validation = false;
            } else if(!ValidationHelper.confirmationPass(pass.getText().toString(),confirmationPass.getText().toString())) {
                confirmationPass.setError("Senhas não correspondem");
                validation = false;
            }
        } else {
            if(!ValidationHelper.pass(context, pass.getText().toString())) {
                pass.setError("Senha inválida");
                validation = false;
            } else if(!ValidationHelper.confirmationPass(pass.getText().toString(),confirmationPass.getText().toString())) {
                pass.setError("Senhas não correspondem");
            }

            if(!ValidationHelper.pass(context, confirmationPass.getText().toString())) {
                confirmationPass.setError("Senha inválida");
                validation = false;
            } else if(!ValidationHelper.confirmationPass(pass.getText().toString(),confirmationPass.getText().toString())) {
                confirmationPass.setError("Senhas não correspondem");
                validation = false;
            }
        }

        if(GenericValidator.isBlankOrNull(name.getText().toString())) {
            name.setError("Nome é obrigatório");
            validation = false;
        } else if(!ValidationHelper.genericSize(context, name.getText().toString())) {
            name.setError("Nome inválido");
            validation = false;
        }

        if(!GenericValidator.isBlankOrNull(phone.getText().toString()) && !ValidationHelper.phone(context, phone.getText().toString())) {
            phone.setError("Telefone inválido");
            validation = false;
        }

        if(!GenericValidator.isBlankOrNull(company.getText().toString()) && !ValidationHelper.genericSize(context, company.getText().toString())) {
            company.setError("Companhia inválida");
            validation = false;
        }

        if(!GenericValidator.isBlankOrNull(role.getText().toString()) && !ValidationHelper.genericSize(context, role.getText().toString())) {
            role.setError("Companhia inválida");
            validation = false;
        }

        return validation;
    }
}
