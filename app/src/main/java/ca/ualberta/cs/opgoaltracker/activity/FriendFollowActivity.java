/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Participant;

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
    private String userID;
    private ArrayList<Participant> followingList;
    Participant currentUser;

    /**
     * Default onCreate method. Event for followButton is defined inside.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_follow);

        userID = (String) getIntent().getStringExtra("ID");
        followingList = this.getIntent().getParcelableArrayListExtra("followingList");
        currentUser = getIntent().getParcelableExtra("LOGINUSER");

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(userID);
        final Button followButton = (Button) findViewById(R.id.follow);

        followButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: need elastic search to return a participant
                Participant newFollow = new Participant(userID);
                followingList.add(newFollow);

                //TODO: verify that friend has already been added. This part should be connected to server later
                Log.d("followingList:",followingList.get(0).getId());
                currentUser.setFollowingList(followingList);
                Intent intent = new Intent(FriendFollowActivity.this, MenuPage.class);
                intent.putExtra("NEWFOLLOWUSER", currentUser);
                startActivity(intent);
                //intent.putExtra("followingList", (Parcelable) followingList);
                finish();
            }
        });
    }

}