package com.kennanseno.ultimate_scoreboard_app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class EventsActivity extends Activity {

    ArrayList<Event> eventList = new ArrayList<>();

    //URL to get JSON Array
    private static String url = "http://kennanseno.com/ultimate-app/getEvents.php";

    //JSON Node Names
    private static final String TAG_EVENT_ID = "event_id";
    private static final String TAG_EVENT_NAME = "event_name";
    private static final String TAG_EVENT_VENUE = "venue";
    private static final String TAG_EVENT_START_DATE = "start_date";
    private static final String TAG_EVENT_END_DATE = "end_date";
    private static final String TAG_EVENT_ORGANIZER = "user_id";

    JSONObject data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);

        new JSONParse().execute();

        EventsAdapter eventAdapter = new EventsAdapter(this, eventList);
        ListView eventListView = (ListView)findViewById(R.id.eventListView);
        eventListView.setAdapter(eventAdapter);

        //uncomment to test eventList value
        //Log.d("Test", eventList.get(0).getName());

    }


    private class JSONParse extends AsyncTask<String, String, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            return jParser.getJSONFromUrl(url);
        }
        @Override
        protected void onPostExecute(JSONArray json) {

            try {

                // Storing  JSON item in a Variable
                for(int count = 0; count < json.length(); count++){
                    data = json.optJSONObject(count);

                    Event singleEvent =  new Event();

                    singleEvent.setId(data.getInt(TAG_EVENT_ID));
                    singleEvent.setName(data.getString(TAG_EVENT_NAME));
                    singleEvent.setVenue(data.getString(TAG_EVENT_VENUE));
                    singleEvent.setStartDate(data.getString(TAG_EVENT_START_DATE));
                    singleEvent.setEndDate(data.getString(TAG_EVENT_END_DATE));
                    singleEvent.setEventOrganizer(data.getInt(TAG_EVENT_ORGANIZER));
                    Log.d("Test", "Event ID: " + singleEvent.getId() + " Name: " + singleEvent.getName() + " Venue: " + singleEvent.getVenue() + " Start Date: " + singleEvent.getStartDate() + " End Date: " + singleEvent.getEndDate() + " Organizer Id: " + singleEvent.getEventOrganizer());

                    eventList.add(singleEvent);
                    Log.d("Test", eventList.get(count).getName());
                    // inside the for loop the is seems the singleEvent gets added into the eventList
                    //but when I call it the event list in the onCreate method, it's saying that it is empty.
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
