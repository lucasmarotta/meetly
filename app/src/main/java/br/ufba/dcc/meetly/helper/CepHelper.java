package br.ufba.dcc.meetly.helper;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.gilbertotorrezan.viacep.se.ViaCEPClient;
import com.github.gilbertotorrezan.viacep.shared.ViaCEPEndereco;

import br.ufba.dcc.meetly.R;

/**
 * Created by Lucas on 28/03/2017.
 */

public class CepHelper
{
    private static View view;

    private static class CepTask extends AsyncTask<String,Void,ViaCEPEndereco>
    {
        private static final String TAG = CepTask.class.getSimpleName();

        @Override
        protected ViaCEPEndereco doInBackground(String... params)
        {
            ViaCEPEndereco cep = new ViaCEPEndereco();
            try {
                ViaCEPClient client = new ViaCEPClient();
                cep = client.getEndereco(params[0]);
            } catch (Exception e) {
                Log.e(TAG,e.getMessage());
                cep = null;
            }
            return cep;
        }

        @Override
        protected void onPostExecute(ViaCEPEndereco viaCEPEndereco)
        {
            if(viaCEPEndereco != null) {
                ((EditText) view.findViewById(R.id.meeting_cep)).setText(viaCEPEndereco.getCep());

                Spinner state = (Spinner) view.findViewById(R.id.meeting_state);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.form_states, android.R.layout.simple_spinner_item);
                int position = adapter.getPosition(viaCEPEndereco.getUf().toUpperCase());
                state.setSelection(position);

                ((EditText) view.findViewById(R.id.meeting_city)).setText(viaCEPEndereco.getLocalidade());
                ((EditText) view.findViewById(R.id.meeting_city)).setError(null);
                ((EditText) view.findViewById(R.id.meeting_address_name)).setText(viaCEPEndereco.getLogradouro());
                ((EditText) view.findViewById(R.id.meeting_address_name)).setError(null);
                ((EditText) view.findViewById(R.id.meeting_address_neighborhood)).setText(viaCEPEndereco.getBairro());
                ((EditText) view.findViewById(R.id.meeting_address_complement)).setText(viaCEPEndereco.getComplemento());
            } else {
                ((EditText) view.findViewById(R.id.meeting_city)).setText("");
                ((Spinner) view.findViewById(R.id.meeting_state)).setSelection(0);
                ((EditText) view.findViewById(R.id.meeting_address_name)).setText("");
                ((EditText) view.findViewById(R.id.meeting_address_neighborhood)).setText("");
                ((EditText) view.findViewById(R.id.meeting_address_complement)).setText("");
            }

        }
    }

    public static void fillAddressByCep(View view, String cep)
    {
        CepHelper.view = view;
        CepTask cepTask = new CepTask();
        cepTask.execute(cep);
    }

}
