package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;

/**
 * Created by malon_000 on 2017-11-03.
 */

class HabitEventAdapter extends ArrayAdapter<HabitEvent> {

    public HabitEventAdapter(Context context, List<HabitEvent> objects) {
        super(context, R.layout.fragment_habit_event, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //use elastic search to get user's info but for since this is just a test
        LayoutInflater EventInflater = LayoutInflater.from(getContext());
        View customView = EventInflater.inflate(R.layout.event_row,parent,false);

        HabitEvent event = getItem(position);
        TextView EventType = (TextView) customView.findViewById(R.id.eventType);
        TextView EventComment = (TextView) customView.findViewById(R.id.eventComment);
        TextView EventDate = (TextView) customView.findViewById(R.id.eventDate);
        //ImageView EventImage = (ImageView) customView.findViewById(R.id.newsImage);


        EventType.setText(event.getHabitType());
        EventComment.setText(event.getComment());
        EventDate.setText(event.getDate().toString());
        //EvenImage.setImageResource(event.getPhoto());

        return customView;
    }
}
