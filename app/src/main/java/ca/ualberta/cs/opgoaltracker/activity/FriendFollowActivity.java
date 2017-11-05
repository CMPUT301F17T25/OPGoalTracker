package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ca.ualberta.cs.opgoaltracker.R;

/**
 * Created by song on 2017/11/3.
 */

public class FriendFollowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_follow);
        Button followButton = (Button) findViewById(R.id.follow);
        followButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(FriendFollowActivity.this, FriendFragment.class));
                finish();
            }
        });
    }

}