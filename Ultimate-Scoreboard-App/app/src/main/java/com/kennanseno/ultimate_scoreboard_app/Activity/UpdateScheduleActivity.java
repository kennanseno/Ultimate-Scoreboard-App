package com.kennanseno.ultimate_scoreboard_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kennanseno.ultimate_scoreboard_app.Network.DBManager;
import com.kennanseno.ultimate_scoreboard_app.R;

public class UpdateScheduleActivity extends AppCompatActivity {

    Button updateResultButton, cancelResultButton;
    EditText team1Score, team1SpiritScore1, team1SpiritScore2, team1SpiritScore3, team1SpiritScore4, team1SpiritScore5;
    EditText team2Score, team2SpiritScore1, team2SpiritScore2, team2SpiritScore3, team2SpiritScore4, team2SpiritScore5;
    Intent intent;
    String userId;
    int eventId, team1ScoreId, team2ScoreId;
    DBManager dbManager =  new DBManager(UpdateScheduleActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("userId");
        eventId = extras.getInt("eventId");
        team1ScoreId = extras.getInt("team1ScoreId");
        team2ScoreId = extras.getInt("team2ScoreId");

        team1Score = (EditText)findViewById(R.id.updateMatchTeam1ScoreEditText);
        team1SpiritScore1 = (EditText)findViewById(R.id.updateMatchTeam1SpiritScore1EditText);
        team1SpiritScore2 = (EditText)findViewById(R.id.updateMatchTeam1SpiritScore2EditText);
        team1SpiritScore3 = (EditText)findViewById(R.id.updateMatchTeam1SpiritScore3EditText);
        team1SpiritScore4 = (EditText)findViewById(R.id.updateMatchTeam1SpiritScore4EditText);
        team1SpiritScore5 = (EditText)findViewById(R.id.updateMatchTeam1SpiritScore5EditText);
        team2Score = (EditText)findViewById(R.id.updateMatchTeam2ScoreEditText);
        team2SpiritScore1 = (EditText)findViewById(R.id.updateMatchTeam2SpiritScore1EditText);
        team2SpiritScore2 = (EditText)findViewById(R.id.updateMatchTeam2SpiritScore2EditText);
        team2SpiritScore3 = (EditText)findViewById(R.id.updateMatchTeam2SpiritScore3EditText);
        team2SpiritScore4 = (EditText)findViewById(R.id.updateMatchTeam2SpiritScore4EditText);
        team2SpiritScore5 = (EditText)findViewById(R.id.updateMatchTeam2SpiritScore5EditText);
        updateResultButton = (Button)findViewById(R.id.updateMatchUpdateButton);
        cancelResultButton = (Button)findViewById(R.id.updateMatchCancelButton);

        team1Score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team1Score.setText("");
            }
        });

        team2Score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team2Score.setText("");
            }
        });

        updateResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: update local db
                intent = new Intent(UpdateScheduleActivity.this, ScheduleActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("eventId", eventId);
                intent.putExtra("team1ScoreId", team1ScoreId);
                intent.putExtra("team2ScoreId", team2ScoreId);

                int team1NewScore = Integer.parseInt(team1Score.getText().toString());
                int team1Spirit1= Integer.parseInt(team1SpiritScore1.getText().toString());
                int team1Spirit2= Integer.parseInt(team1SpiritScore2.getText().toString());
                int team1Spirit3= Integer.parseInt(team1SpiritScore3.getText().toString());
                int team1Spirit4= Integer.parseInt(team1SpiritScore4.getText().toString());
                int team1Spirit5= Integer.parseInt(team1SpiritScore5.getText().toString());
                dbManager.updateScore(team1ScoreId, team1NewScore, team1Spirit1, team1Spirit2, team1Spirit3, team1Spirit4, team1Spirit5);

                int team2NewScore = Integer.parseInt(team2Score.getText().toString());
                int team2Spirit1= Integer.parseInt(team2SpiritScore1.getText().toString());
                int team2Spirit2= Integer.parseInt(team2SpiritScore2.getText().toString());
                int team2Spirit3= Integer.parseInt(team2SpiritScore3.getText().toString());
                int team2Spirit4= Integer.parseInt(team2SpiritScore4.getText().toString());
                int team2Spirit5= Integer.parseInt(team2SpiritScore5.getText().toString());
                dbManager.updateScore(team2ScoreId, team2NewScore, team2Spirit1, team2Spirit2, team2Spirit3, team2Spirit4, team2Spirit5);

                startActivity(intent);
                Toast.makeText(UpdateScheduleActivity.this, "Score Updated!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UpdateScheduleActivity.this, ScheduleActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
