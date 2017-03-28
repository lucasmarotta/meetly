package br.ufba.dcc.meetly.activity;

import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.dao.MeetingDAO;
import br.ufba.dcc.meetly.helper.MeetingFormHelper;
import br.ufba.dcc.meetly.helper.SessionHelper;
import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.models.UserModel;

public class MeetingActivity extends AppCompatActivity {

    private MeetingModel meeting = null;
    private UserModel user = null;
    private SessionHelper session;
    private View rootView;
    private MeetingFormHelper meetingFormHelper;
    private MeetingDAO meetingDAO;


    // Datepicker
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    // TimePicker
    public static TimePicker timePicker;
    private static TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHelper(this);
        setContentView(R.layout.activity_meeting);

        rootView = findViewById(R.id.activity_meeting);
        meetingFormHelper = new MeetingFormHelper(rootView);
        meetingDAO = new MeetingDAO(this);

        initDatePicker();

        // init TimePicker
        timeView = (TextView) findViewById(R.id.meeting_time);
        // end TimePicker
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_meeting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void saveMeetingAction(MenuItem item) {
        session = new SessionHelper(this);
        user = session.getSessionUser();

        boolean newMeeting = (meeting == null);

        if(meetingFormHelper.validateForm(newMeeting)) {
            if (newMeeting) {
                meeting = meetingFormHelper.getMeeting();
                meeting.setUserId(user.getId());
                if (meetingDAO.store(meeting)) {
                    Toast.makeText(this, "Reunião Casdastrada!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Não foi possível cadastrar a reunião. Tente mais tarde", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(MeetingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }


    // DatePicker
    private void initDatePicker()
    {
        dateView = (TextView) findViewById(R.id.meeting_date);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view)
    {
        showDialog(999);
    }

    @Override
    protected DatePickerDialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder()
                .append(String.format("%02d",  day)).append("/")
                .append(String.format("%02d",  month)).append("/")
                .append(year));
    }

    // TimePicker
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            timeView.setText(new StringBuilder()
                    .append(String.format("%02d",  hourOfDay)).append(":")
                    .append(String.format("%02d",  minute)));

        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    // end Time Picker

}
