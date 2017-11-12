/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Admin;


/**
 * This is the Register page when User clicked "SignUp" button
 * <br>
 * This page allows user to create a Participant type user ID
 * User can either go back to login page or finish sign up process and jump to the next page.
 * sign up for  first admin account and  be able to sign in , do changes in admin account page
 * <br>
 * @author Yongjia Huang , Donglin Han
 * @version 3.0
 * @see AppCompatActivity
 * @since 1.0
 */
public class Register_activity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameText;
    private Button addTrySignInButton;
    private Button addRegisterButton;


    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_activity);

        usernameText = (EditText) findViewById(R.id.user_id);

        addTrySignInButton = (Button) findViewById(R.id.trysignin);
        addTrySignInButton.setOnClickListener(this);

        addRegisterButton = (Button) findViewById(R.id.register);
        addRegisterButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        // TODO should we enable log in functionality only in the MainActivity?
        if(view == addTrySignInButton) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (view == addRegisterButton) {
            addUser();
            finish();
        }
    }

    /**
     * For admin sign up, add admin user if ID start with "admin"
     * @author donglin
     * @since 1.0
     * @see AdminActivity
     */
    public void addUser() {
        String username = usernameText.getText().toString();

        // TODO assume all admin user has username starting with "admin". This logic should be changed in the future
        if(username.startsWith("admin")) {
            addAdminUser(username);
        } else {
            // TODO implement method to add a regular user
        }
    }

    /**
     * With Gson, connect data to add adminuser, save in json
     * @param username
     * @see AdminIOGson
     * @see AdminActivity
     */
    public void addAdminUser(String username) {
        // TODO sign up the first admin user via this register page, later admin users should be created by an existing admin
        AdminIOGson adminIOGson = new AdminIOGson();
        adminIOGson.saveAdminInFile(new Admin(username), this);
    }

}
