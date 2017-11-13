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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.User;

/**
 * Created by song on 2017/11/11.
 */

public class FriendAdapter extends ArrayAdapter<Participant> {
    private ArrayList<Participant> friendList;
    Context mContext;

    // Counstructor
    public FriendAdapter(Context context, ArrayList<Participant> friendList) {
        super(context, R.layout.fragment_friend, friendList);
        this.friendList = friendList;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.followed_item,parent,false);
        Participant participant = getItem(position);

        TextView userName = (TextView) customView.findViewById(R.id.userName);
        //TextView location = (TextView) customView.findViewById(R.id.location);
        ImageView picture = (ImageView) customView.findViewById(R.id.picture);
        //CheckBox like = (CheckBox) customView.findViewById(R.id.like);

        //participant need to change
        userName.setText(participant.getId());
        //picture.setImageResource(participant.getAvatar());
        //like.setChecked(participant.like());

        return customView;
    }
}