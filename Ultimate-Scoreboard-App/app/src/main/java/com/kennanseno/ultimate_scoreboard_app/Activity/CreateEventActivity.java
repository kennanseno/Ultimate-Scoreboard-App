package com.kennanseno.ultimate_scoreboard_app.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.kennanseno.ultimate_scoreboard_app.Backend.DBManager;
import com.kennanseno.ultimate_scoreboard_app.R;

public class CreateEventActivity extends AppCompatActivity {

    DBManager dbManager =  new DBManager(CreateEventActivity.this);
    int startYear, startMonth, startDay, endYear, endMonth, endDay, userId;
    static final int END_DATE_DIALOG_ID = 0;
    static final int START_DATE_DIALOG_ID = 1;
    Button addStartDateButton, addEndDateButton, createButton, cancelButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        final EditText eventName = (EditText)findViewById(R.id.createEventNameEditText);
        final EditText eventAddress = (EditText)findViewById(R.id.createEventAddressEditText);
        createButton = (Button)findViewById(R.id.createEventCreateButton);
        cancelButton = (Button)findViewById(R.id.createEventCancelButton);
        addStartDateButton = (Button)findViewById(R.id.createEventAddDateButton);
        addEndDateButton = (Button)findViewById(R.id.createEventAddEndDateButton);

        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");

        //cancel
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateEventActivity.this, "Cancelled!",Toast.LENGTH_SHORT).show();
                intent = new Intent(CreateEventActivity.this, EventsActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        //create
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String startDate = startYear + "-" + startMonth + "-" + startDay ;
                String endDate = endYear + "-" + endMonth + "-" + endDay;
                dbManager.insertNewEvent(eventName.getText().toString(), eventAddress.getText().toString(), startDate, endDate, userId);
                Toast.makeText(CreateEventActivity.this, "Event Created!",Toast.LENGTH_SHORT).show();
                intent = new Intent(CreateEventActivity.this, EventsActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventName.setText("");
            }
        });

        eventAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventAddress.setText("");
            }
        });

        addStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);
            }
        });

        addEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == START_DATE_DIALOG_ID){
            return new DatePickerDialog(this, startDatePickerListener, startYear, startMonth, startDay);
        }else if(id == END_DATE_DIALOG_ID){
            return new DatePickerDialog(this, endDatePickerListener, endYear, endMonth, endDay );
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener startDatePickerListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startYear = year;
            startMonth = monthOfYear;
            startDay = dayOfMonth;
            Log.d("Test", "Start Date: " + startDay + "-" + startMonth + "-" + startYear);
        }
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endYear = year;
            endMonth = monthOfYear;
            endDay = dayOfMonth;
            Log.d("Test", "End Date: " + endDay + "-" + endMonth + "-" + endYear);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
