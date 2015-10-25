package com.kennanseno.ultimate_scoreboard_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventsAdapter extends ArrayAdapter<Event> {

    public EventsAdapter(Context context, ArrayList<Event> event){
        super(context, R.layout.event_list_row, event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater eventInflater = LayoutInflater.from(getContext());

        if (convertView == null) {
            convertView = eventInflater.inflate(R.layout.event_list_row, parent, false);
        }

        Event singleEvent = getItem(position);

        //TODO - implement ViewHolder pattern for performance
        TextView eventName = (TextView) convertView.findViewById(R.id.eventNameTextView);
        TextView eventVenue = (TextView) convertView.findViewById(R.id.eventVenueTextView);
        TextView eventStartDate = (TextView) convertView.findViewById(R.id.eventStartDateTextView);
        TextView eventEndDate = (TextView) convertView.findViewById(R.id.eventEndDateTextView);

        eventName.setText(singleEvent.getName());
        eventVenue.setText(singleEvent.getVenue());
        eventStartDate.setText(singleEvent.getStartDate());
        eventEndDate.setText(singleEvent.getEndDate());

        return convertView;
    }
}
