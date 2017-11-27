/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.Photograph;

/**
 * This is the activity that allows users to create a new habit event
 * This page allows user to create a new habit event with custom comment and ability to select a picture
 * @author Long Ma
 * @version 2.0
 * @see AppCompatActivity
 * @since 2.0
 */
public class HabitEventAddActivity extends AppCompatActivity {
    HabitEvent newEvent;
    Boolean setPicture;
    Bitmap picture;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        setPicture=false;

        // spinner does not work work as of right now, will add in a later version
        // for the all habit types are set to 'test'

        //Adds picture
        //Important, requires permission to add picture from storage
        //Currently requires permission to be given manually, will change to ask for permission later
        ImageButton getImage = (ImageButton)findViewById(R.id.new_event_picture);
        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        Button createEvent = (Button)findViewById(R.id.create_event);
        createEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText comment = (EditText)findViewById(R.id.new_event_comment);
                CheckBox location = (CheckBox)findViewById(R.id.new_event_location);

                try {
                    String eventComment = comment.getText().toString();
                    //for some reason eventComment triggers commenttoolongexception
                    newEvent = new HabitEvent("test",eventComment,new Date());


                } catch (CommentTooLongException e) {
                    e.printStackTrace();
                }
                if (newEvent!=null) {
                    if (location.isChecked()){
                        newEvent.setLocation("somewhere");
                    }
                    if (setPicture) {
                        try {
                            newEvent.setPhoto(new Photograph(33, 33));
                        } catch (ImageTooLargeException e) {
                            e.printStackTrace();
                        }
                    }

                    Intent data = new Intent();
                    data.putExtra("event", (Parcelable)newEvent);
                    setResult(AppCompatActivity.RESULT_OK,data);
                    finish();
                }
                else{
                    Log.d("failed","null event");
                    setResult(AppCompatActivity.RESULT_CANCELED);
                    finish();
                }


            }
        });



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = data.getExtras();
        switch (requestCode){
            case 2:
                try{
                    Log.d("getbitmap", "no bitmap");
                    Uri uri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            uri, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    //taken from https://stackoverflow.com/questions/2507898/how-to-pick-an-image-from-gallery-sd-card-for-my-app
                    //2017-11-13
                    Log.d("pic","gotten filepath, getting bitmap");
                    Bitmap picture = BitmapFactory.decodeFile(filePath);
                    ImageButton getImage = (ImageButton) findViewById(R.id.new_event_picture);
                    getImage.setImageBitmap(picture);


                }catch (Exception e){}
        }
    }
}
