package com.kennanseno.ultimate_scoreboard_app.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kennanseno.ultimate_scoreboard_app.Network.DBManager;
import com.kennanseno.ultimate_scoreboard_app.R;

import java.util.ArrayList;


//TODO : When a club name not in the db is inputted, add it in the Club table

public class CreateScheduleActivity extends AppCompatActivity {

    DBManager dbManager =  new DBManager(CreateScheduleActivity.this);
    int startHour, startMinute, endHour, endMinute, eventId;
    static final int END_TIME_DIALOG_ID = 0;
    static final int START_TIME_DIALOG_ID = 1;
    Button startTimeButton, endTimeButton, createButton, cancelButton;
    AutoCompleteTextView team1AutoCompleteTextView, team2AutoCompleteTextView;
    EditText dayEditText;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        Bundle extras = getIntent().getExtras();
        eventId = extras.getInt("eventId");

        startTimeButton = (Button)findViewById(R.id.createMatchStartTimeButton);
        endTimeButton = (Button)findViewById(R.id.createMatchEndTimeButton);
        createButton = (Button)findViewById(R.id.createMatchCreateButton);
        cancelButton = (Button)findViewById(R.id.createMatchCancelButton);
        team1AutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.createMatchTeam1TextView);
        team2AutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.createMatchTeam2TextView);
        dayEditText = (EditText)findViewById(R.id.createMatchDayEditText);

        //convert to array
        ArrayList<String> clubList = new ArrayList<>();
        clubList = dbManager.getClubNames();
        String[] clubNames = clubList.toArray(new String[clubList.size()]);

        //Add to autocompletetextview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clubNames);
        team1AutoCompleteTextView.setAdapter(adapter);
        team2AutoCompleteTextView.setAdapter(adapter);

        team1AutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team1AutoCompleteTextView.setText("");
            }
        });

        team2AutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team2AutoCompleteTextView.setText("");
            }
        });

        dayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayEditText.setText("");
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CreateScheduleActivity.this, ScheduleActivity.class);
                intent.putExtra("eventId", eventId);

                //insert new schedule
                String team1 = team1AutoCompleteTextView.getText().toString();
                String team2 = team2AutoCompleteTextView.getText().toString();
                int day = Integer.parseInt(dayEditText.getText().toString());
                String startTime = startHour + ":" + startMinute;
                String endTime = endHour + ":" + endMinute;

                dbManager.insertNewSchedule(team1, team2, startTime, endTime, day, eventId);

                startActivity(intent);
                Toast.makeText(CreateScheduleActivity.this, "Match Created!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CreateScheduleActivity.this, ScheduleActivity.class);
                intent.putExtra("eventId",eventId);
                startActivity(intent);
                Toast.makeText(CreateScheduleActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();
            }
        });

        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_TIME_DIALOG_ID);
            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == START_TIME_DIALOG_ID){
            return new TimePickerDialog(this, startTimePickerListener, startHour, startMinute, true);
        }else if(id == END_TIME_DIALOG_ID){
            return new TimePickerDialog(this, endTimePickerListener, endHour, endMinute, true);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener startTimePickerListener = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startHour = hourOfDay;
            startMinute = minute;
            Log.d("Test", "Start Time: " + startHour + ":" + startMinute);
        }
    };

    private TimePickerDialog.OnTimeSetListener endTimePickerListener = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            endHour = hourOfDay;
            endMinute = minute;
            Log.d("Test", "End Time: " + endHour + ":" + endMinute);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
