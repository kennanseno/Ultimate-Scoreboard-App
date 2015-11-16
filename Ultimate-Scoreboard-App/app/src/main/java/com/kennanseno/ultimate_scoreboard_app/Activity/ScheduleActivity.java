package com.kennanseno.ultimate_scoreboard_app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kennanseno.ultimate_scoreboard_app.Adapter.ScheduleAdapter;
import com.kennanseno.ultimate_scoreboard_app.Backend.DBManager;
import com.kennanseno.ultimate_scoreboard_app.Model.Schedule;
import com.kennanseno.ultimate_scoreboard_app.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    private String url;
    JSONObject data = null;
    Toolbar toolbar;
    Intent intent;
    String userId;
    int eventId;

    ListView scheduleListView;
    ScheduleAdapter scheduleAdapter;
    ArrayList<Schedule> scheduleList = new ArrayList<>();
    DBManager dbManager =  new DBManager(ScheduleActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        Log.d("Test", ">>>ScheduleActivity<<<");
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("userId");
        eventId = extras.getInt("eventId");

        toolbar = (Toolbar) findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Schedules");

        scheduleList = dbManager.getSchedules(eventId);

        scheduleAdapter = new ScheduleAdapter(ScheduleActivity.this, scheduleList);
        scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);

        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.w("Test", scheduleList.get(position).toString());
//                intent = new Intent(ScheduleActivity.this, UpdateScheduleActivity.class);
//                intent.putExtra("userId", userId);
//                intent.putExtra("eventId", eventId);
//                startActivity(intent);

                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
                builder.setMessage("Delete or Update!");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        intent = new Intent(ScheduleActivity.this, UpdateScheduleActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("eventId", eventId);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.w("Test", "Delete Alert Dialog Clicked!");
                        scheduleList.remove(position);
                        scheduleAdapter.notifyDataSetChanged();
                        //TODO add delete statement to delete data in local db
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.w("Test", "Cancel Alert Dialog clicked!");
                    }
                });
                builder.show();
            }
        });

        //url = "http://kennanseno.com/ultimate-app/getMatches.php?event_id=" + extras.getString("eventId");
        //new JSONParse().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.new_schedule) {
            intent = new Intent(ScheduleActivity.this, CreateScheduleActivity.class);
            intent.putExtra("eventId", eventId);
            startActivity(intent);
        }else if(id == R.id.rankings){
            Log.d("Test", "Rankings Activity clicked!");
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    private class JSONParse extends AsyncTask<String, String, ArrayList<Schedule>> {

        @Override
        protected ArrayList doInBackground(String... args) {

            try {

                JSONParser jParser = new JSONParser();

                // Getting JSON from URL
                JSONArray jArray = jParser.getJSONFromUrl(url);

                for (int count = 0; count < jArray.length(); count++) {
                    data = jArray.optJSONObject(count);

                    Schedule singleSchedule = new Schedule();

                    singleSchedule.setMatchId(data.getInt("match_id"));
                    singleSchedule.setClub1Id(data.getString("club1_id"));
                    singleSchedule.setClub1Score(data.getInt("club1_score"));
                    singleSchedule.setClub1SpiritScore(data.getInt("club1_spirit_score"));
                    singleSchedule.setClub2Id(data.getString("club2_id"));
                    singleSchedule.setClub2Score(data.getInt("club2_score"));
                    singleSchedule.setClub2SpiritScore(data.getInt("club2_spirit_score"));
                    singleSchedule.setStartTime(data.getString("start_time"));
                    singleSchedule.setEndTime(data.getString("end_time"));
                    singleSchedule.setDay(data.getInt("day"));
                    singleSchedule.setEventId(data.getInt("event_id"));

                    //Log.d("Test", "ID:" + singleSchedule.getMatchId() + " Club1 Code:" + singleSchedule.getClub1Id() + " Club1 Score:" + singleSchedule.getClub1Score() + " Club1 SpiritScore:" + singleSchedule.getClub1SpiritScore());
                    //Log.d("Test", "Club2 Code:" + singleSchedule.getClub2Id() + " Club2 Score:" + singleSchedule.getClub2Score() + " Club2 SpiritScore:" + singleSchedule.getClub2SpiritScore());
                    //Log.d("Test", "Start Time:" + singleSchedule.getStartTime() + " End Time:" + singleSchedule.getEndTime() + " Day:" + singleSchedule.getDay() + " Event ID:" + singleSchedule.getEventId());

                    scheduleList.add(singleSchedule);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return scheduleList;
        }

        @Override
        protected void onPostExecute(final ArrayList<Schedule> schedule) {

            scheduleAdapter = new ScheduleAdapter(ScheduleActivity.this, schedule);
            scheduleListView = (ListView) findViewById(R.id.scheduleListView);
            scheduleListView.setAdapter(scheduleAdapter);
        }
    }

    */
}
