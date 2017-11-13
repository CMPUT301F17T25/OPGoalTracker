/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */
package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Participant;

/**
 * This is the search friend ID page.
 * This page allows user to enter other user's ID and press search button to find,
 * and jump into the adding page
 *
 * @author song
 * @version 1.0
 *
 */
public class FriendSearchActivity extends AppCompatActivity {
    private EditText userID;
    private ArrayList<Participant> followingList;
    Participant currentUser;

    /**
     * default onCreate method. Event for searchButton is defined inside.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);
        Button searchButton = (Button) findViewById(R.id.search);
        userID = (EditText) findViewById(R.id.userID);

        followingList = this.getIntent().getParcelableArrayListExtra("followingList");
        currentUser = getIntent().getParcelableExtra("LOGINUSER");

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: need elastic search to verify if userID is valid or not

                String ID = userID.getText().toString();
                Intent intent = new Intent(FriendSearchActivity.this, FriendFollowActivity.class);
                intent.putExtra("ID",ID);
                intent.putParcelableArrayListExtra("followingList", followingList);
                intent.putExtra("LOGINUSER", currentUser);
                startActivity(intent);

                finish();
            }
        });
    }
}