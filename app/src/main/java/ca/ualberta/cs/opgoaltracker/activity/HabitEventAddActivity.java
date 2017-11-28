/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.Controller.LatitudeAndLongitudeWithPincode;
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
    Boolean setPicture=false;
    Bitmap picture;
    View view;
    String habitType;
    private  LatitudeAndLongitudeWithPincode convertedAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        //for test only
        ArrayList<String> arrayListHabit = new ArrayList<String>();
        arrayListHabit.add("test");
        arrayListHabit.add("test1");

        final Spinner habitList = (Spinner)findViewById(R.id.habit_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListHabit);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        habitList.setAdapter(dataAdapter);

        // for the all habit types are set to 'test'

        //Adds picture
        //Important, requires permission to add picture from storage
        //Currently requires permission to be given manually, will change to ask for permission later
        ImageButton getImage = (ImageButton)findViewById(R.id.new_event_picture);
        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * TO SELECT PICTURE
                 */


                if (ContextCompat.checkSelfPermission(HabitEventAddActivity.this, android.Manifest.permission.
                        READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }else{
                    ActivityCompat.requestPermissions(HabitEventAddActivity.this, new String[]{
                            android.Manifest.permission.READ_EXTERNAL_STORAGE}, 51);

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                }
            }
        });

        Button createEvent = (Button)findViewById(R.id.create_event);
        createEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText comment = (EditText)findViewById(R.id.new_event_comment);
                EditText address = (EditText) findViewById(R.id.address);

                try {
                    String eventComment = comment.getText().toString();
                    String selectedHabit = habitList.getSelectedItem().toString();
                    //for some reason eventComment triggers commenttoolongexception
                    newEvent = new HabitEvent(selectedHabit,eventComment,new Date());


                } catch (CommentTooLongException e) {
                    e.printStackTrace();
                }
                try{
                    String eventAddress = address.getText().toString();
                    GetCoordinates getLocation = new GetCoordinates();
                    getLocation.execute(eventAddress.replace(" ","+"));
                }catch (Exception e){
                    e.printStackTrace();
                }
//                if (newEvent!=null) {
////                    //handles if upload location
////                    if (location.isChecked()){
////                        newEvent.setLocation("somewhere");
////                    }
//                    //handles picture
//                    if (setPicture) {
//                        try {
//                            newEvent.setPhoto(new Photograph(33, 33));
//                        } catch (ImageTooLargeException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                    Intent data = new Intent();
//                    data.putExtra("event", (Parcelable)newEvent);
//                    setResult(AppCompatActivity.RESULT_OK,data);
//                    finish();
//                }
//                else{
//                    Log.d("failed","null event");
//                    setResult(AppCompatActivity.RESULT_CANCELED);
//                    finish();
//                }


            }
        });

    }

    //NEEDED TO REQUEST PERMISSION
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[]grantedResults){
        if (requestCode==51){
            if (grantedResults.length>0 && grantedResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 2:

                //GET RESULT FROM SELECT PICTURE
                if (resultCode == RESULT_OK && null != data) {
                    try {
                        setPicture = true;
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

                        Bitmap picture = BitmapFactory.decodeFile(filePath);
                        ImageButton getImage = (ImageButton) findViewById(R.id.new_event_picture);
                        getImage.setImageBitmap(picture);


                    }catch (Exception e){}
                }
            }
    }

    private class GetCoordinates extends AsyncTask<String,Void,String> {
        ProgressDialog dialog = new ProgressDialog(HabitEventAddActivity.this);

        String[] location = new String[] {"",""};


        @Override
        protected void onPreExecute(){

            super.onPreExecute();
//            dialog.setMessage("Please wait....");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                LatitudeAndLongitudeWithPincode http = new LatitudeAndLongitudeWithPincode();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHTTPDate(url);
                return response;
            }catch (Exception ex){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            try{
                JSONObject jsonObject = new JSONObject(s);
                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();
                newEvent.setLocation(lat,lng);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (newEvent!=null) {
//                    //handles if upload location
//                    if (location.isChecked()){
//                        newEvent.setLocation("somewhere");
//                    }
                //handles picture
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
    }
}
