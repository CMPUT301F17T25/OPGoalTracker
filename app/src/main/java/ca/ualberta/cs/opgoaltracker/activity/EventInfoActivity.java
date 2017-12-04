/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.Photograph;

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
    Boolean changedPhoto = Boolean.FALSE;
    String filePath;


    /**
     * When the app is created
     * Handles the UI
     *
     * @param savedInstanceState
     * @see AppCompatActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        //Changes to the UI and textBox to fit what the habitevent's current informations are
        TextView enterLocation = (TextView) findViewById(R.id.location_text);
        enterLocation.setText("Enter an address to change the location of this event");

        TextView gpsLocation = (TextView) findViewById(R.id.gps_text);
        gpsLocation.setText("Has location stored, uncheck to delet location of this event");

        final HabitEvent selected = getIntent().getParcelableExtra("event");

        //set picture
        final ImageButton getImage = (ImageButton) findViewById(R.id.new_event_picture);
        if (selected.getPhoto() != null) {
            Bitmap picture = selected.getPhoto().getBitMap();
            getImage.setImageBitmap(Bitmap.createScaledBitmap(picture,
                    selected.getPhoto().getWidth(), selected.getPhoto().getHeight(), Boolean.FALSE));
        }
        getImage.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles when a picture is selected
             * Starts a picture selection if permissions are granted, else request permissions
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                /**
                 * TO SELECT PICTURE
                 */
                if (ContextCompat.checkSelfPermission(EventInfoActivity.this, android.Manifest.permission.
                        READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else {
                    ActivityCompat.requestPermissions(EventInfoActivity.this, new String[]{
                            android.Manifest.permission.READ_EXTERNAL_STORAGE}, 51);

                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                }
            }
        });


        EditText comment = (EditText) findViewById(R.id.new_event_comment);
        comment.setText(selected.getComment());
        CheckBox location = (CheckBox) findViewById(R.id.gps_checkbox);
        if (selected.getLocation().get(0) != null && selected.getLocation().get(1) != null) {
            location.setChecked(Boolean.TRUE);
        }
        Button saveChanges = (Button) findViewById(R.id.create_event);
        saveChanges.setText("Save Changes");
        Button createEvent = (Button) findViewById(R.id.create_event);
        createEvent.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles when the participant clicks save button
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Boolean good = Boolean.TRUE;
                Intent intent = new Intent();
                EditText comment = (EditText) findViewById(R.id.new_event_comment);
                EditText address = (EditText) findViewById(R.id.address);


                String eventComment = comment.getText().toString();
                String selectedHabit = selected.getHabitType();
                //creates a new event based on information given
                HabitEvent newEvent = null;
                try {
                    newEvent = new HabitEvent(selectedHabit, eventComment, new Date());
                } catch (CommentTooLongException e) {
                    e.printStackTrace();
                    Toast.makeText(EventInfoActivity.this, "Your comment is too long", Toast.LENGTH_SHORT).show();
                    return;
                }


                //handles the location aspect of the event
                String eventAddress = address.getText().toString();
                if (!eventAddress.isEmpty()) {
                    // if a new address is entered
                    Geocoder geocoder = new Geocoder(EventInfoActivity.this);
                    try {
                        List<Address> ad = geocoder.getFromLocationName(eventAddress, 1);
                        Address a = ad.get(0);
                        String lat = Double.toString(a.getLatitude());
                        String lng = Double.toString(a.getLongitude());
                        newEvent.setLocation(lat, lng);
                        String place = eventAddress + " in " + a.getLocality();
                        Toast.makeText(EventInfoActivity.this, place, Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Log.d("location", "location");
                } else {
                    CheckBox gpsLocation = (CheckBox) findViewById(R.id.gps_checkbox);
                    Log.d("gps", "before first");
                    if (gpsLocation.isChecked()) {
                        //if the checkbox is still checked, the participant did not want to remove the location
                        //keep the old location coordinates
                        Log.d("gps", "before second");
                        newEvent.setLocation(selected.getLocation().get(0), selected.getLocation().get(1));
                    }
                    // taken from https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
                    // 2017-11-29

                    //if the checkbox is unchecked, the coordinates are removed
                }


                //handles picture
                if (selected.getPhoto() != null && changedPhoto == Boolean.FALSE) {
//                    try {
                    newEvent.setPhoto(selected.getPhoto());
//                    } catch (ImageTooLargeException e) {
//                        e.printStackTrace();
//                    }
                } else if (changedPhoto == Boolean.TRUE) {
                    // handles is the picture was changed
                    try {
                        Drawable draw = getImage.getDrawable();
                        Bitmap p = ((BitmapDrawable) draw).getBitmap();
                        newEvent.setPhoto(new Photograph(filePath));
                    } catch (ImageTooLargeException e) {
                        Log.d("exception", "catch first");
                        Toast.makeText(EventInfoActivity.this, "Image should be under 65536 bytes",
                                Toast.LENGTH_SHORT).show();
                        return;

                    }
                }

                if (good == Boolean.TRUE) {
                    //if the new event created is correct, return it
                    Intent data = new Intent();
                    data.putExtra("delete",0);
                    data.putExtra("event", newEvent);
                    data.putExtra("photo", changedPhoto);
                    setResult(AppCompatActivity.RESULT_OK, data);
                    Log.d("last", newEvent.getComment());
                    if (newEvent.getPhoto()==null){
                        Log.d("does not have pic","why");
                    }
                    finish();
                }
            }


        });
    }


    /**
     * creates the menu
     *
     * @param menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles when a item on the menu is pressed
     * since there is only 1 button in this fragment, handles the map button and calls the new activity
     *
     * @param item menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.event_event_delete:
                Intent data = new Intent();
                data.putExtra("delete", 1);
                setResult(AppCompatActivity.RESULT_OK, data);
                finish();
            // delete event
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * handles when return from asking for permission
     *
     * @param requestCode
     * @param permission
     * @param grantedResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantedResults) {
        switch (requestCode) {
            case 51:
                // if the request was to get permission to access storage
                if (grantedResults.length > 0 && grantedResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
        }
    }

    /**
     * responds to when activities ends and comes back to this activity
     * most notably get the photo from photo selection
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:

                //GET RESULT FROM SELECT PICTURE
                if (resultCode == RESULT_OK && null != data) {
                    try {
                        //gets the new photo
                        changedPhoto = true;
                        Uri uri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(
                                uri, filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();

                        //taken from https://stackoverflow.com/questions/2507898/how-to-pick-an-image-from-gallery-sd-card-for-my-app
                        //2017-11-13

                        Bitmap picture = BitmapFactory.decodeFile(filePath);
                        ImageButton getImage = (ImageButton) findViewById(R.id.new_event_picture);
                        //puts the new photo onto the imagebutton
                        getImage.setImageBitmap(picture);
                        changedPhoto=Boolean.TRUE;


                    } catch (Exception e) {
                    }
                }
        }
    }
}
