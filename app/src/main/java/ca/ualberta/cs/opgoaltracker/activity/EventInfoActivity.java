/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;

/**
 * Created by malon_000 on 2017-11-06.
 */

/**
 * This is the fragment that displays more information about a single habit event
 * <br>
 * This page allows user to see and edit their own a specific event
 * <br>
 * @author Long Ma
 * @version 2.0
 * @see AppCompatActivity,
 * @see HabitEvent
 * @since 2.0
 */
public class EventInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

            //set picture
        ImageButton imageButton = (ImageButton) findViewById(R.id.new_event_picture);
        if (getIntent().hasExtra("picture")) {
            Bitmap picture = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("picture"),0,getIntent().getByteArrayExtra("picture").length);
            imageButton.setImageBitmap(picture);
        }
        EditText comment= (EditText) findViewById(R.id.new_event_comment);
//        CheckBox location = (CheckBox) findViewById(R.id.new_event_location);
        Button saveChanges = (Button) findViewById(R.id.create_event);
        saveChanges.setText("Save Changes");

        comment.setText(getIntent().getStringExtra("comment"));
//        if (getIntent().getBooleanExtra("location",false)){
//            location.setChecked(true);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.event_event_delete:
                return true;
            // delete event
        }
        return super.onOptionsItemSelected(item);
    }
}
