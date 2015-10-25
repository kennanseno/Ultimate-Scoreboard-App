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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);

        new AsyncGetEventData().execute("kennanseno.com/ultimate-app/getEvents.php");
    }

    //Downloading data asynchronously
    class AsyncGetEventData extends AsyncTask<String, JSONArray, Integer> {

        @Override
        protected Integer doInBackground(String... url) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                Log.d("Test", "" + statusCode);
                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
//                String response = streamToString(httpResponse.getEntity().getContent());
//                parseResult(response);
//                result = 1;

            } catch (Exception e) {
                // Log.d(TAG, e.getLocalizedMessage());
            }
            //result = 1;

        }

        @Override
        protected void onPostExecute(Integer result) {

            EventsAdapter eventAdapter = new EventsAdapter(getApplicationContext(), eventList);
            final ListView kennanListView = (ListView)findViewById(R.id.eventListView);
            kennanListView.setAdapter(eventAdapter);

        }
    }

    public String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        return result;
    }

    private void parseResult(String result) {
        try {

            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject post = jsonArray.optJSONObject(i);

                Event event = new Event();

                event.setId(Integer.parseInt(post.optString("event_id")));
                event.setName(post.optString("event_name"));
                event.setVenue(post.optString("venue"));
                event.setStartDate(post.optString("start_date"));
                event.setEndDate(post.optString("end_date"));

                eventList.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
