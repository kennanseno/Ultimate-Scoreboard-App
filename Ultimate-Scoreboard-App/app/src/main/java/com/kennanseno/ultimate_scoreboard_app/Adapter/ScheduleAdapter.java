package com.kennanseno.ultimate_scoreboard_app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kennanseno.ultimate_scoreboard_app.R;
import com.kennanseno.ultimate_scoreboard_app.Model.Schedule;

import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<Schedule>{

    public ScheduleAdapter(Context context, List<Schedule> schedule) {
        super(context, R.layout.schedule_list_row, schedule);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater eventInflater = LayoutInflater.from(getContext());

        if (convertView == null) {
            convertView = eventInflater.inflate(R.layout.schedule_list_row, parent, false);
        }

        Schedule singleSchedule = getItem(position);

        //TODO implement ViewHolder pattern
        TextView club1Code = (TextView) convertView.findViewById(R.id.club1TextView);
        TextView club2Code= (TextView) convertView.findViewById(R.id.club2TextView);
        TextView club1Score = (TextView) convertView.findViewById(R.id.club1ScoreTextView);
        TextView club2Score = (TextView) convertView.findViewById(R.id.club2ScoreTextView);
        TextView club1SpiritScore = (TextView) convertView.findViewById(R.id.club1SpiritScoreTextView);
        TextView club2SpiritScore = (TextView) convertView.findViewById(R.id.club2SpiritScoreTextView);
        TextView time = (TextView) convertView.findViewById(R.id.timeTextView);
        TextView day = (TextView) convertView.findViewById(R.id.dayTextView);

        club1Code.setText(singleSchedule.getClub1Id());
        club2Code.setText(singleSchedule.getClub2Id());
        day.setText("Day " + singleSchedule.getDay());
        time.setText(singleSchedule.getStartTime() + " - " + singleSchedule.getEndTime());
        club1Score.setText(Integer.toString(singleSchedule.getClub1Score()));
        club2Score.setText(Integer.toString(singleSchedule.getClub2Score()));
        club1SpiritScore.setText(Integer.toString(singleSchedule.getClub1SpiritScore()));
        club2SpiritScore.setText(Integer.toString(singleSchedule.getClub2SpiritScore()));

        return convertView;
    }
}
