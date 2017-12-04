

/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Admin;
import ca.ualberta.cs.opgoaltracker.models.Restriction;

/**
 * Created by donglin3 on 11/11/17.
 */

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText pictureSizeEdittext;
    private EditText titleSizeEdittext;
    private EditText reasonSizeEdittext;
    private EditText commentSizeEdittext;
    private EditText idEdittext;
    private EditText passwordEdittext;
    private Button saveButton;
    private Button addAccountButton;
    private Button logoutButton;
    private ArrayList<Admin> adminList = new ArrayList<Admin>();
    private String currentAdminID;
    private int newPictureSize;
    private int newTitleSize;
    private int newReasonSize;
    private int newCommentSize;
    private Restriction restriction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Admin");

        pictureSizeEdittext = (EditText) findViewById(R.id.picture_size_edittext);
        titleSizeEdittext = (EditText) findViewById(R.id.title_size_edittext);
        reasonSizeEdittext = (EditText) findViewById(R.id.reason_size_edittext);
        commentSizeEdittext =(EditText) findViewById(R.id.comment_size_edittext);
        idEdittext =(EditText) findViewById(R.id.id_edittext);
        passwordEdittext = (EditText) findViewById(R.id.password_edittext);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        addAccountButton = (Button) findViewById(R.id.add_account_button);
        addAccountButton.setOnClickListener(this);

        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent adminIntent = getIntent();
        this.currentAdminID = adminIntent.getStringExtra("ADMINID");
        restriction = adminIntent.getParcelableExtra("Restriction for Admin");

        // Reset EditTexts in case the restrictions were updated.
        pictureSizeEdittext.setText(String.valueOf(restriction.getPictureSize()));
        titleSizeEdittext.setText(String.valueOf(restriction.getTitleSize()));
        reasonSizeEdittext.setText(String.valueOf(restriction.getReasonSize()));
        commentSizeEdittext.setText(String.valueOf(restriction.getCommentSize()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    // code taken from https://developer.android.com/training/basics/firstapp/starting-activity.html#BuildIntent
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // Handle item selection
        switch (itemId) {
            case R.id.delete_admin_account:
                deleteCurrentAdmin();
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == saveButton ) {
            saveChanges();
        } else if (view == addAccountButton) {
            addNewAdmin();
        } else if (view == logoutButton) {
            finish();
        }
    }

    /**
     * save restriction changes to Elasticsearch
     * @author donglin
     * @see MainActivity
     * @since 2.0
     */
    public void saveChanges() {
        this.newPictureSize = Integer.parseInt(pictureSizeEdittext.getText().toString());
        this.newTitleSize = Integer.parseInt(titleSizeEdittext.getText().toString());
        this.newReasonSize = Integer.parseInt(reasonSizeEdittext.getText().toString());
        this.newCommentSize = Integer.parseInt(commentSizeEdittext.getText().toString());

        restriction = new Restriction(newPictureSize, newTitleSize, newReasonSize, newCommentSize);

        // update restriction
        ElasticsearchController.AddRestrictionTask updateRestrictionTask = new ElasticsearchController.AddRestrictionTask();
        updateRestrictionTask.execute(restriction);
    }

    /**
     * add new admin to Elasticsearch
     * @author donglin
     * @since 2.0
     */
    public void addNewAdmin() {
        String newAdminName = idEdittext.getText().toString();

        // query server if username exists
        String query = "{\n" +
                "	\"query\": {\n" +
                "		\"term\": {\"_id\":\"" + newAdminName + "\"}\n" +
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
                Toast.makeText(AdminActivity.this, "Can Not Connect to Server", Toast.LENGTH_SHORT).show();
            } else if (getAdminsTask.get().isEmpty() && getParticipantsTask.get().isEmpty()) { // if username not exists
                Admin newAdmin = new Admin(newAdminName);
                ElasticsearchController.AddAdminsTask addAdminsTask = new ElasticsearchController.AddAdminsTask();
                addAdminsTask.execute(newAdmin);
            } else {
                Toast.makeText(AdminActivity.this, "Username already existed.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
                Log.i("Error", "Failed to get the participant from the asyc object");
        }

    }

    /**
     * if if current admin wants to delete account, check if it's the last admin account. if so then deny, if not delete. finish back to MainActvity
     * @author donglin
     * @since 2.0
     */
    public void deleteCurrentAdmin() {
        String query = "{\n" +
                "	\"query\": {\n" +
                "		\"term\": {\"_id\":\"" + currentAdminID + "\"}\n" +
                "	}\n" +
                "}";
        ElasticsearchController.DeleteAdminsTask deleteAdminsTask = new ElasticsearchController.DeleteAdminsTask();
        deleteAdminsTask.execute(query);
    }

}
