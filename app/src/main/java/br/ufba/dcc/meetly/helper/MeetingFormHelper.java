package br.ufba.dcc.meetly.helper;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.models.MeetingModel;

/**
 * Created by StormTrooper on 3/27/2017.
 */

public class MeetingFormHelper
{
    private View formView;
    private Context context;
    private EditText title;
    private EditText subject;
    private TextView date;
    private TextView time;
    private EditText cep;
    private Spinner state;

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
        date = (TextView) formView.findViewById(R.id.meeting_date);
        time = (TextView) formView.findViewById(R.id.meeting_time);
        state = (Spinner) formView.findViewById(R.id.meeting_state);

        state = (Spinner) formView.findViewById(R.id.meeting_state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.form_states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);

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
        return meeting;
    }

    public void setMeeting(MeetingModel meeting)
    {
        title.setText(meeting.getTitle());
        subject.setText(meeting.getSubject());
        date.setText(meeting.getDate());
        time.setText(meeting.getTime());
    }

    public boolean validateForm(boolean newMeeting)
    {
        boolean validation = true;

        return validation;
    }
}
