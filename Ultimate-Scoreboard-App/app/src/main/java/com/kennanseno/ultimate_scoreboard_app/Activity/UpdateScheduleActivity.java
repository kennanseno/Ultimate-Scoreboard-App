package com.kennanseno.ultimate_scoreboard_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kennanseno.ultimate_scoreboard_app.R;

public class UpdateScheduleActivity extends AppCompatActivity {

    Button updateResultButton, cancelResultButton;
    EditText team1Score, team1SpiritScore1, team1SpiritScore2, team1SpiritScore3, team1SpiritScore4, team1SpiritScore5;
    EditText team2Score, team2SpiritScore1, team2SpiritScore2, team2SpiritScore3, team2SpiritScore4, team2SpiritScore5;
    Intent intent;
    String userId;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("userId");
        eventId = extras.getInt("eventId");

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

        updateResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: update local db
                intent = new Intent(UpdateScheduleActivity.this, ScheduleActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
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
}
