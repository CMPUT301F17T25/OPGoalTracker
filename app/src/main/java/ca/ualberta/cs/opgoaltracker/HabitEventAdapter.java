package ca.ualberta.cs.opgoaltracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by malon_000 on 2017-11-03.
 */

class HabitEventAdapter extends ArrayAdapter<HabitEvent> {

    public HabitEventAdapter(Context context, List<HabitEvent> objects) {
        super(context,R.layout.news_list, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //use elastic search to get user's info but for since this is just a test
        LayoutInflater EventInflater = LayoutInflater.from(getContext());
        View customView = EventInflater.inflate(R.layout.news_list,parent,false);

        HabitEvent event = getItem(position);
        TextView EventType = (TextView) customView.findViewById(R.id.newsType);
        TextView EventComment = (TextView) customView.findViewById(R.id.newsComment);
        //ImageView EventImage = (ImageView) customView.findViewById(R.id.newsImage);
        //ImageView UserImage = (ImageView) customView.findViewById(R.id.UserIcon);
        TextView UserName = (TextView) customView.findViewById(R.id.newsUser);

        EventType.setText(event.getHabitType());
        EventComment.setText(event.getComment());
        //EvenImage.setImageResource(event.getPhoto());

        //UserImage.setImageResouce()
        UserName.setText(event.getUser());

        return customView;
    }
}
