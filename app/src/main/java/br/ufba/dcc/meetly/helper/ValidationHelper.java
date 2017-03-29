package br.ufba.dcc.meetly.helper;

import android.content.Context;
import android.util.Patterns;

import org.apache.commons.validator.GenericValidator;

import br.ufba.dcc.meetly.R;

public class ValidationHelper
{
    static String CEP_REGEXP = "^\\d{5}(-)?\\d{3}$";
    static String TIME_REGEXP = "^\\d{2}[:]\\d{2}$";

    public static boolean email(Context context, String email)
    {
        if(email.length() <= context.getResources().getInteger(R.integer.form_email_size)) {
            return GenericValidator.isEmail(email);
        }
        return false;
    }

    public static boolean pass(Context context, String pass)
    {
        if(pass.length() <= context.getResources().getInteger(R.integer.form_pass_size)) {
            return true;
        }
        return false;
    }

    public static boolean confirmationPass(String pass, String confirmationPaas)
    {
        if(pass.equals(confirmationPaas)) {
            return true;
        }
        return false;
    }

    public static boolean phone(Context context, String phone)
    {
        if(phone.length() <= context.getResources().getInteger(R.integer.form_phone_size)) {
            return Patterns.PHONE.matcher(phone).matches();
        }
        return false;
    }

    public static boolean date(Context context, String date)
    {
        if (date.length() == 10)
        {
            return GenericValidator.isDate(date, "dd/MM/yyyy", true);
        }
        return false;
    }

    public static boolean time(Context context, String time)
    {
        return GenericValidator.matchRegexp(time, TIME_REGEXP);
    }

    public static boolean cep(Context context, String cep)
    {
        return GenericValidator.matchRegexp(cep, CEP_REGEXP);
    }


    public static boolean genericSize(Context context, String generic)
    {
        if(generic.length() <= context.getResources().getInteger(R.integer.form_generic_size)) {
            return true;
        }
        return false;
    }





}
