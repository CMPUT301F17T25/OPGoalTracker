/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

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

class NewsAdapter extends ArrayAdapter<HabitEvent> {
    public NewsAdapter(Context context, List<HabitEvent> objects) {
        super(context, R.layout.fragment_news, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //use elastic search to get user's info but for since this is just a test
        LayoutInflater EventInflater = LayoutInflater.from(getContext());
        View customView = EventInflater.inflate(R.layout.news_row,parent,false);

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
