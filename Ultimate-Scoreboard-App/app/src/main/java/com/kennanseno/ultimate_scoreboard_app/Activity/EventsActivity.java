package com.kennanseno.ultimate_scoreboard_app.Activity;

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

import com.kennanseno.ultimate_scoreboard_app.Adapter.EventsAdapter;
import com.kennanseno.ultimate_scoreboard_app.Backend.DBManager;
import com.kennanseno.ultimate_scoreboard_app.Model.Event;
import com.kennanseno.ultimate_scoreboard_app.R;

import org.json.JSONObject;

import java.util.ArrayList;


public class EventsActivity extends AppCompatActivity {

    //URL to get JSON Array
    private String url = "http://kennanseno.com/ultimate-app/getEvents.php";

    //JSON Node Names
    private final String TAG_EVENT_ID = "event_id";
    private final String TAG_EVENT_NAME = "event_name";
    private final String TAG_EVENT_VENUE = "venue";
    private final String TAG_EVENT_START_DATE = "start_date";
    private final String TAG_EVENT_END_DATE = "end_date";
    private final String TAG_EVENT_ORGANIZER = "user_id";

    JSONObject data = null;

    ListView eventListView;
    EventsAdapter eventAdapter;
    ArrayList<Event> eventList = new ArrayList<>();
    DBManager dbManager =  new DBManager(EventsActivity.this);
    Toolbar toolbar;
    int userId;
    Intent intent;


    //TODO transition animation between activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);
        Log.d("Test", ">>>>EventsActivity<<<<");

        toolbar = (Toolbar) findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");

        //new JSONParse().execute();
        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");
        Log.d("Test", "USER ID: " +  userId);

        eventList = dbManager.getAllEvents();
        eventAdapter = new EventsAdapter(EventsActivity.this, eventList);
        eventListView = (ListView)findViewById(R.id.eventListView);
        eventListView.setAdapter(eventAdapter);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int eventId = eventList.get(position).getId();
                intent = new Intent(EventsActivity.this, ScheduleActivity.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.new_event) {
            intent = new Intent(EventsActivity.this, CreateEventActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }




//    @Override


    /*
    private class JSONParse extends AsyncTask<String, String, ArrayList<Event>>{

        @Override
        protected ArrayList doInBackground(String... args) {

            try {

                JSONParser jParser = new JSONParser();

                // Getting JSON from URL
                JSONArray jArray = jParser.getJSONFromUrl(url);

                for (int count = 0; count < jArray.length(); count++) {
                    data = jArray.optJSONObject(count);

                    Event singleEvent = new Event();

                    singleEvent.setId(data.getInt(TAG_EVENT_ID));
                    singleEvent.setName(data.getString(TAG_EVENT_NAME));
                    singleEvent.setVenue(data.getString(TAG_EVENT_VENUE));
                    singleEvent.setStartDate(data.getString(TAG_EVENT_START_DATE));
                    singleEvent.setEndDate(data.getString(TAG_EVENT_END_DATE));
                    singleEvent.setEventOrganizer(data.getInt(TAG_EVENT_ORGANIZER));
                    Log.d("Test", "Event ID: " + singleEvent.getId() + " Name: " + singleEvent.getName() + " Venue: " + singleEvent.getVenue() + " Start Date: " + singleEvent.getStartDate() + " End Date: " + singleEvent.getEndDate() + " Organizer Id: " + singleEvent.getEventOrganizer());

                    eventList.add(singleEvent);
                }

            }catch (JSONException e){
                e.printStackTrace();
            }

            return eventList;
        }

        @Override
        protected void onPostExecute(final ArrayList<Event> events) {

            eventAdapter = new EventsAdapter(EventsActivity.this, events);
            eventListView = (ListView)findViewById(R.id.eventListView);
            eventListView.setAdapter(eventAdapter);

            eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String eventId = Integer.toString(events.get(position).getId());
                    Intent intent = new Intent(EventsActivity.this, ScheduleActivity.class);
                    intent.putExtra("eventId", eventId);
                    startActivity(intent);
                }
            });
        }
    }

    */

}
