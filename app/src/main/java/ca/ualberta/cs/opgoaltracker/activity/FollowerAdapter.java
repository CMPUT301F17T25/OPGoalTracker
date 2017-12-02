/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.ParticipantName;
import ca.ualberta.cs.opgoaltracker.models.Photograph;

/**
 * Created by song on 2017/11/13.
 */

/**
 * Adapter for FollowerListFragment. It is used to inflate xml layout,
 * as well as handle buttons and on click events.
 *
 * @author song
 * @version 1.0
 *
 */
public class FollowerAdapter extends ArrayAdapter<ParticipantName> {
    private ArrayList<ParticipantName> followerList;
    private ArrayList<ParticipantName> targetFollowingList;
    private Participant currentUser;
    ParticipantName followerName;
    Participant follower;
    private Photograph photo;
    private int currentPosition;
    Context mContext;

    /**
     * Constructor for adapter
     * @param context
     * @param friendList
     */
    public FollowerAdapter(Context context, ArrayList<ParticipantName> friendList,Participant currentUser) {
        super(context, R.layout.fragment_friend, friendList);
        this.followerList = friendList;
        this.mContext=context;
        this.currentUser = currentUser;
    }

    /**
     * getCount method
     * @return size of followerList
     */
    @Override
    public int getCount() {
        return followerList.size();
    }

    /**
     * getItem method
     * @param pos
     * @return the participant at selected location
     */
    @Override
    public ParticipantName getItem(int pos) {
        return followerList.get(pos);
    }

    /**
     * getView method, used to generate the view for FollowerListFragment page
     * @param position
     * @param convertView
     * @param parent
     * @return customView, view for FollowerListFragment
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.follower_item,parent,false);
        followerName = getItem(position);
        currentPosition = position;

        String query = "{\n" +
                "	\"query\": {\n" +
                "		\"term\": {\"_id\":\"" + followerName.getId() + "\"}\n" +
                "	}\n" +
                "}";
        ElasticsearchController.GetParticipantsTask getParticipantsTask = new ElasticsearchController.GetParticipantsTask();
        getParticipantsTask.execute(query);

        try {
            if (getParticipantsTask.get() == null) { // check if connected to server
                Toast.makeText(convertView.getContext(), "Can Not Connect to Server", Toast.LENGTH_SHORT).show();
            }else if (getParticipantsTask.get().isEmpty() == false){
                follower = getParticipantsTask.get().get(0);
                targetFollowingList = follower.getFollowingList();
                photo = follower.getAvatar();
                followerList = currentUser.getFollowerList();
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the participant from the asyc object");
        }
        TextView userName = (TextView) customView.findViewById(R.id.userName);
        TextView location = (TextView) customView.findViewById(R.id.location);
        ImageView picture = (ImageView) customView.findViewById(R.id.picture);
        Button block = (Button) customView.findViewById(R.id.block);

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockFollower();
                notifyDataSetChanged();
            }
        });

        userName.setText(followerName.getId());
        if (photo!=null){
            picture.setImageBitmap(photo.getBitMap());
        }
        return customView;
    }

    public void blockFollower(){
        followerList.remove(currentPosition);
        for (int i=0;i<targetFollowingList.size();i++){
            if (targetFollowingList.get(i).equals(currentUser)){
                targetFollowingList.remove(i);
            }
        }
        update(currentUser,follower);
    }

    public void update(Participant currentUser, Participant following){
        ElasticsearchController.AddParticipantsTask addUsersTask1 = new ElasticsearchController.AddParticipantsTask();
        addUsersTask1.execute(currentUser);
        ElasticsearchController.AddParticipantsTask addUsersTask2 = new ElasticsearchController.AddParticipantsTask();
        addUsersTask2.execute(following);
    }
}