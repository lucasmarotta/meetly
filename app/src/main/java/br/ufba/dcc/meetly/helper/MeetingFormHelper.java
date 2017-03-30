package br.ufba.dcc.meetly.helper;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.validator.GenericValidator;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.models.UserModel;

public class MeetingFormHelper
{
    private View formView;
    private Context context;
    private EditText title;
    private EditText subject;
    private EditText guest;
    private TextView date;
    private TextView time;
    private Spinner state;
    private ArrayAdapter<CharSequence> stateAdapter;

    private EditText cep;
    private EditText city;
    private EditText address;
    private EditText neighborhood;
    private EditText number;
    private EditText complement;
    private EditText room;
    private EditText floor;


    public MeetingFormHelper(View view)
    {
        this.formView = view;
        context = view.getContext();
        init();
    }

    private void init()
    {
        title = (EditText) formView.findViewById(R.id.meeting_title);
        subject = (EditText) formView.findViewById(R.id.meeting_subject);
        guest = (EditText) formView.findViewById(R.id.meeting_guest);
        date = (TextView) formView.findViewById(R.id.meeting_date);
        time = (TextView) formView.findViewById(R.id.meeting_time);
        state = (Spinner) formView.findViewById(R.id.meeting_state);

        cep = (EditText) formView.findViewById(R.id.meeting_cep);
        city = (EditText) formView.findViewById(R.id.meeting_city);
        address = (EditText) formView.findViewById(R.id.meeting_address_name);
        neighborhood = (EditText) formView.findViewById(R.id.meeting_address_neighborhood);
        complement = (EditText) formView.findViewById(R.id.meeting_address_complement);
        number = (EditText) formView.findViewById(R.id.meeting_address_number);
        room = (EditText) formView.findViewById(R.id.meeting_room);
        floor = (EditText) formView.findViewById(R.id.meeting_floor);

        state = (Spinner) formView.findViewById(R.id.meeting_state);
        stateAdapter = ArrayAdapter.createFromResource(context, R.array.form_states, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);

        cep = (EditText) formView.findViewById(R.id.meeting_cep);
        cep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus) {
                    CepHelper.fillAddressByCep(formView, cep.getText().toString());
                }
            }
        });

    }

    public MeetingModel getMeeting()
    {
        MeetingModel meeting = new MeetingModel();
        meeting.setTitle(title.getText().toString());
        meeting.setSubject(subject.getText().toString());
        meeting.setDate(date.getText().toString());
        meeting.setTime(time.getText().toString());
        meeting.setAddressCep(cep.getText().toString());
        meeting.setAddressState(state.getSelectedItem().toString());
        meeting.setAddressCity(city.getText().toString());
        meeting.setAddressName(address.getText().toString());
        meeting.setAddressNeighborhood(neighborhood.getText().toString());
        meeting.setAddressComplement(complement.getText().toString());
        meeting.setAddressNumber(number.getText().toString());
        meeting.setRoom(room.getText().toString());
        meeting.setFloor(floor.getText().toString());

        return meeting;
    }

    public String getGuestEmail()
    {
        return guest.getText().toString();
    }

    public void setMeeting(MeetingModel meeting)
    {
        title.setText(meeting.getTitle());
        subject.setText(meeting.getSubject());
        date.setText(meeting.getDate());
        time.setText(meeting.getTime());
        state.setSelection(stateAdapter.getPosition(meeting.getAddressState()));
        cep.setText(meeting.getAddressCep());
        city.setText(meeting.getAddressCity());
        address.setText(meeting.getAddressName());
        neighborhood.setText(meeting.getAddressNeighborhood());
        complement.setText(meeting.getAddressComplement());
        number.setText(meeting.getAddressNumber());
        room.setText(meeting.getRoom());
        floor.setText(meeting.getFloor());
    }

    public void setMeeting(MeetingModel meeting, boolean viewOnly)
    {
        title.setText(meeting.getTitle());
        subject.setText(meeting.getSubject());
        date.setText(meeting.getDate());
        time.setText(meeting.getTime());
        state.setSelection(stateAdapter.getPosition(meeting.getAddressState()));
        cep.setText(meeting.getAddressCep());
        city.setText(meeting.getAddressCity());
        address.setText(meeting.getAddressName());
        neighborhood.setText(meeting.getAddressNeighborhood());
        complement.setText(meeting.getAddressComplement());
        number.setText(meeting.getAddressNumber());
        room.setText(meeting.getRoom());
        floor.setText(meeting.getFloor());

        if(viewOnly) {
            title.setEnabled(false);
            subject.setEnabled(false);
            date.setEnabled(false);
            time.setEnabled(false);
            state.setEnabled(false);
            cep.setEnabled(false);
            city.setEnabled(false);
            address.setEnabled(false);
            neighborhood.setEnabled(false);
            complement.setEnabled(false);
            number.setEnabled(false);
            room.setEnabled(false);
            floor.setEnabled(false);
        }
    }

    public boolean validateForm(boolean newMeeting)
    {
        boolean validation = true;

        if(GenericValidator.isBlankOrNull(title.getText().toString())) {
            title.setError("Título é obrigatório");
            validation = false;
        } else if(!ValidationHelper.genericSize(context, title.getText().toString())) {
            title.setError("Título inválido");
            validation = false;
        }

        if(GenericValidator.isBlankOrNull(subject.getText().toString())) {
            subject.setError("Assunto é obrigatório");
            validation = false;
        }

        if(GenericValidator.isBlankOrNull(date.getText().toString())) {
            date.setError("Data é obrigatório");
            validation = false;
        } else if(!ValidationHelper.date(context, date.getText().toString())) {
            date.setError("Data inválido");
            validation = false;
        }

        if (GenericValidator.isBlankOrNull(cep.getText().toString())) {
            cep.setError("Cep é obrigatório");
            validation = false;
        } else if (!ValidationHelper.cep(context, cep.getText().toString())) {
            cep.setError("Cep inválido");
        }

        if (GenericValidator.isBlankOrNull(time.getText().toString())) {
            time.setError("Horário é obrigatório");
            validation = false;
        } else if (!ValidationHelper.time(context, time.getText().toString())) {
            time.setError("Horário inválido");
        }

        if(GenericValidator.isBlankOrNull(city.getText().toString())) {
            city.setError("Cidade é obrigatório");
            validation = false;
        } else if(!ValidationHelper.genericSize(context, city.getText().toString())) {
            city.setError("Cidade inválida");
            validation = false;
        }

        if(GenericValidator.isBlankOrNull(address.getText().toString())) {
            address.setError("Endereço é obrigatório");
            validation = false;
        } else if(!ValidationHelper.genericSize(context, address.getText().toString())) {
            address.setError("Endereço inválido");
            validation = false;
        }

        return validation;
    }
}
