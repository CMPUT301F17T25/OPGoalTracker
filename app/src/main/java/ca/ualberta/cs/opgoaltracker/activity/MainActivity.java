
/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Admin;
import ca.ualberta.cs.opgoaltracker.models.Participant;


/**
 * This is the first login page when this app is lanched.
 * <br>
 * This page allows user to enter their user ID and press login button to login, and allows user to click register and jump into the register page
 * <br>
 * @author Yongjia Huang, Mingwei Li
 * @version 3.0
 * @see AppCompatActivity
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private Participant currentUser;
    private EditText userID;
    private String name;
    private String name2;
    private ArrayList<Admin> adminList;


    /**
     * Default onCreate method for a activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        configureSignUpButton();
        configureSignInButton();
        userID = (EditText) findViewById(R.id.userID);
    }


    /**
     * Disable Back button on android phone
     */
    @Override
    public void onBackPressed() {
    }


    /**
     * Configure SignUp Button
     * <br>
     * SignUp Button brings user to Register Page
     * Once SignUp Button is clicked, the current Login Page is closed.
     */
    private void configureSignUpButton(){
        Button addSignUpButton = (Button) findViewById(R.id.signup);
        addSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, Register_activity.class));//Let signup buttion jump to Register activity page.
//                finish();
            }

        });
    }


    /**
     * Configure SignIn Button
     * <br>
     * SignIn Button checks User's input ID and bring user to next page
     * If User ID is invalid, show hit message
     *
     * For admin :
     * add an additional check to see if the user is an admin user. if so put extra to put its id so that i
     * in AdminActivity which is current admin known
     *
     * @author donglin
     * @see AdminActivity
     * @since 2.0
     */
    private void configureSignInButton(){
        Button addSignInButton = (Button) findViewById(R.id.signin);
        addSignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                name = userID.getText().toString();
                String query = "{\n" +
                            "	\"query\": {\n" +
                            "		\"term\": {\"_id\":\"" + name + "\"}\n" +
                            "	}\n" +
                            "}";
                // query server in type "admin"
                ElasticsearchController.GetAdminsTask getAdminsTask = new ElasticsearchController.GetAdminsTask();
                getAdminsTask.execute(query);
                // query server in type "participant"
                ElasticsearchController.GetParticipantsTask getParticipantsTask = new ElasticsearchController.GetParticipantsTask();
                getParticipantsTask.execute(query);

                try {
                    if (getAdminsTask.get() == null || getParticipantsTask.get() == null) { // check if connected to server
                        Toast.makeText(MainActivity.this, "Can Not Connect to Server", Toast.LENGTH_SHORT).show();
                    } else if (getAdminsTask.get().isEmpty() == false) { // check if this is an admin user
                        //TODO maybe passing currentUser rather than name
                        Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
                        adminIntent.putExtra("ADMINID", name);
                        startActivity(adminIntent);
                    } else if (getParticipantsTask.get().isEmpty() == false) { // if not an admin, check if this is a participant user
                        currentUser = getParticipantsTask.get().get(0); // currentUser size is 0 if there is no ID matched

                        Intent i = new Intent(MainActivity.this, MenuPage.class);
                        i.putExtra("LOGINUSER", currentUser);
                        startActivity(i);
                    } else { // if not admin nor participant, show Toast
                        Toast.makeText(MainActivity.this, "Invalid User ID !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i("Error", "Failed to get the participant from the asyc object");
                }

            }

        });
    }

    /**
     * check if the admin id is the same with corresponding json file name
     * @author donglin
     * @since 2.0
     * @return
     */
    public boolean isAdminExistent() {
        String[] jsonFileList = this.fileList();
        String adminName;
        // TODO may need to start i from 1 instead of 0
        for(int i = 0; i < jsonFileList.length; i++) {
            adminName = jsonFileList[i].replace(".json", "");
            if(this.name.equals(adminName)) {
                return true;
            }
        }

        return false;
    }


//    /**
//     * for Project 4 only.
//     */
//    private void loadFromFile() {
//        try {
//            FileInputStream fis = openFileInput(FILENAME);
//            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//
//            Gson gson = new Gson();
//            Type listType = new TypeToken<Participant>() {
//            }.getType();
//            currentUser = gson.fromJson(in, listType);
//
//        } catch (FileNotFoundException e) {
//            currentUser = new Participant("111") ;
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
//
//    }
//
//    /**
//     * For Project 4 Only
//     * save all the changes into a file
//     * <br>
//     *  load countersList from the file
//     */
//    private void saveInFile() {
//        try {
//            FileOutputStream fos = openFileOutput(FILENAME,
//                    Context.MODE_PRIVATE);
//
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
//
//            Gson gson = new Gson();
//            gson.toJson(currentUser, out);
//
//            out.flush();
//
//            fos.close();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException();
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
//    }


}


