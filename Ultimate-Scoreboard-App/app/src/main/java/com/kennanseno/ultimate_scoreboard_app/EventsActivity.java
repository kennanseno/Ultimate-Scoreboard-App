package com.kennanseno.ultimate_scoreboard_app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static android.app.ActivityOptions.*;

public class EventsActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);

        new JSONParse().execute();

        //TODO transition animation between activities
    }

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

}
