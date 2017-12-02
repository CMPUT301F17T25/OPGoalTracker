/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.icu.text.MessagePattern;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.ParticipantName;

/**
 * This is the follow friend page.
 * This page shows another user's ID, location and photograph.
 * It allows user to press follow button to add the target user to his following list,
 * and jump back to the FollowingListFragment
 *
 * @author song
 * @version 1.0
 *
 */
public class FriendFollowActivity extends AppCompatActivity {
    private ArrayList<ParticipantName> targetRequestList;
    private ArrayList<ParticipantName> followingList;
    private Participant currentUser;
    private Participant targetUser;
    private ArrayList<String> locationList;
    Boolean identicalFlag = true;
    Boolean followingListFlag = true;
    Boolean requestListFlag = true;
    /**
     * Default onCreate method. Event for followButton is defined inside.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_follow);

        targetUser = getIntent().getParcelableExtra("targetUser");
        currentUser = getIntent().getParcelableExtra("LOGINUSER");


        TextView name = (TextView)findViewById(R.id.name);
        TextView location = (TextView)findViewById(R.id.location);
        name.setText(targetUser.getId());
        locationList = targetUser.getLocation();
        try {
            String cityName;
            Geocoder gcd = new Geocoder(FriendFollowActivity.this, Locale.getDefault());
            if (locationList.get(0) != null && locationList.get(1)!=null){
                List<Address> addresses = gcd.getFromLocation(Double.parseDouble(locationList.get(0)),
                        Double.parseDouble(locationList.get(1)), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                    location.setText(cityName);
                }
            }else{
                location.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Button followButton = (Button) findViewById(R.id.follow);

        followButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (targetUser.getId().equals(currentUser.getId())){
                    Toast.makeText(FriendFollowActivity.this, "You cannot follow yourself", Toast.LENGTH_SHORT).show();
                    identicalFlag = false;
                }
                try {
                    targetRequestList = targetUser.getRequestList();
                    followingList = currentUser.getFollowingList();
                } catch (UndefinedException e) {
                    e.printStackTrace();
                }
                //if target user is already followed, cannot follow again
                for (ParticipantName p: followingList){
                    if (p.equals(targetUser)){
                        Toast.makeText(FriendFollowActivity.this, "You already follow this user", Toast.LENGTH_SHORT).show();
                        followingListFlag = false;
                    }
                }
                //if a request has already send, cannot send again
                for (ParticipantName p:targetRequestList){
                    if(p.equals(currentUser)){
                        Toast.makeText(FriendFollowActivity.this, "You already send the request", Toast.LENGTH_SHORT).show();
                        requestListFlag = false;
                    }
                }
                if(identicalFlag && followingListFlag && requestListFlag){
                    targetRequestList.add(new ParticipantName(currentUser));

                    ElasticsearchController.AddParticipantsTask addAnotherUsersTask = new ElasticsearchController.AddParticipantsTask();
                    addAnotherUsersTask.execute(targetUser);
                }
                Intent intent = new Intent(FriendFollowActivity.this, MenuPage.class);
                intent.putExtra("NEWFOLLOWUSER", currentUser);
                startActivity(intent);
                //intent.putExtra("followingList", (Parcelable) followingList);
                finish();
            }
        });
    }

}