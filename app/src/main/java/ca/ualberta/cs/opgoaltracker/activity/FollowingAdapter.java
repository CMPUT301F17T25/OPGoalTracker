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
 * Adapter for FollowingListFragment. It is used to inflate xml layout,
 * as well as handle buttons and on click events.
 *
 * @author song
 * @version 1.0
 *
 */
public class FollowingAdapter extends ArrayAdapter<Participant> {
    private ArrayList<Participant> friendList;
    Context mContext;

    /**
     * Constructor for adapter
     * @param context
     * @param friendList
     */
    public FollowingAdapter(Context context, ArrayList<Participant> friendList) {
        super(context, R.layout.fragment_friend, friendList);
        this.friendList = friendList;
        this.mContext=context;
    }

    /**
     * getCount method
     * @return size of followingList
     */
    @Override
    public int getCount() {
        return friendList.size();
    }

    /**
     * getItem method
     * @param pos
     * @return the participant at selected location
     */
    @Override
    public Participant getItem(int pos) {
        return friendList.get(pos);
    }

    /**
     * getView method, used to generate the view for FollowingListFragment page
     * also define the onClick method for unfollow button.
     * @param position
     * @param convertView
     * @param parent
     * @return customView, view for FollowingListFragment
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.followed_item,parent,false);
        Participant participant = getItem(position);

        TextView userName = (TextView) customView.findViewById(R.id.userName);
        //TextView location = (TextView) customView.findViewById(R.id.location);
        ImageView picture = (ImageView) customView.findViewById(R.id.picture);
        //CheckBox like = (CheckBox) customView.findViewById(R.id.like);
        Button unfollow = (Button) customView.findViewById(R.id.unfollow);

        unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendList.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        //participant need to change
        userName.setText(participant.getId());
        //picture.setImageResource(participant.getAvatar());
        //like.setChecked(participant.like());

        return customView;
    }
}