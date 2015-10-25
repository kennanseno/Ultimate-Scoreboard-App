package com.kennanseno.ultimate_scoreboard_app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EventsActivity extends Activity {

    ArrayList<Event> eventList = new ArrayList<>();

    //URL to get JSON Array
    private static String url = "kennanseno.com/ultimate-app/getEvents.php";

    //JSON Node Names
    private static final String TAG_EVENT_ID = "event_id";
    private static final String TAG_EVENT_NAME = "event_name";
    private static final String TAG_EVENT_VENUE = "venue";
    private static final String TAG_EVENT_START_DATE = "start_date";
    private static final String TAG_EVENT_END_DATE = "end_date";
    private static final String TAG_EVENT_ORGANIZER = "user_id";


    JSONArray data = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);

        new JSONParse().execute();

        EventsAdapter eventAdapter = new EventsAdapter(this, eventList);
        ListView eventListView = (ListView)findViewById(R.id.eventListView);
        eventListView.setAdapter(eventAdapter);

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
                // Getting JSON Array
                data = json.getJSONArray(0);

                // Storing  JSON item in a Variable
                for(int count = 0; count < data.length(); count++){
                    JSONObject j = data.getJSONObject(count);
                    Event singleEvent =  new Event();

                    singleEvent.setId(j.getInt(TAG_EVENT_ID));
                    singleEvent.setName(j.getString(TAG_EVENT_NAME));
                    singleEvent.setVenue(j.getString(TAG_EVENT_VENUE));
                    singleEvent.setStartDate(j.getString(TAG_EVENT_START_DATE));
                    singleEvent.setEndDate(j.getString(TAG_EVENT_END_DATE));
                    singleEvent.setEventOrganizer(j.getInt(TAG_EVENT_ORGANIZER));

                    eventList.add(singleEvent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
