package br.ufba.dcc.meetly.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.dao.MeetingDAO;
import br.ufba.dcc.meetly.dao.UserDAO;
import br.ufba.dcc.meetly.helper.MeetingFormHelper;
import br.ufba.dcc.meetly.helper.UserFormHelper;
import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.models.UserModel;

public class MeetingActivity extends AppCompatActivity {

    private MeetingModel meeting = null;
    private View rootView;
    private MeetingFormHelper meetingFormHelper;
    private MeetingDAO meetingDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        rootView = findViewById(R.id.activity_meeting);
        meetingFormHelper = new MeetingFormHelper(rootView);
        meetingDAO = new MeetingDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_meeting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void saveMeetingAction(MenuItem item) {
        boolean newMeeting = (meeting == null);

        if(meetingFormHelper.validateForm(newMeeting)) {
            if (newMeeting) {
                meeting = meetingFormHelper.getMeeting();

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

}
