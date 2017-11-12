package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Admin;

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
    private String newPictureSize = "65536";
    private String newTitleSize = "20";
    private String newReasonSize = "30";
    private String newCommentSize = "20";


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

        AdminIOGson adminIOGson = new AdminIOGson();
        String[] jsonFileList = this.fileList();
        // TODO may need to start i from 1 instead of 0
        for(int i = 0; i < jsonFileList.length; i++) {
            if(jsonFileList[i].startsWith("admin")) {
                this.adminList.add(adminIOGson.loadAdminFromFile(jsonFileList[i].replace(".json", ""), this));
            }
        }

        // Reset EditTexts in case the restrictions were updated.
        pictureSizeEdittext.setText(String.valueOf(this.adminList.get(0).getPicSize()), TextView.BufferType.EDITABLE);
        titleSizeEdittext.setText(String.valueOf(this.adminList.get(0).getTitleLength()), TextView.BufferType.EDITABLE);
        reasonSizeEdittext.setText(String.valueOf(this.adminList.get(0).getReasonLength()), TextView.BufferType.EDITABLE);
        commentSizeEdittext.setText(String.valueOf(this.adminList.get(0).getCommentLength()), TextView.BufferType.EDITABLE);
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
     * if current admin edit its restrictions, save to its account and save to json
     * if current admin wants to edit restrictions (change pic size ), updated it for all admins, save to json
     * @author donglin
     * @see MainActivity
     * @since 2.0
     */
    public void saveChanges() {
        this.newPictureSize = pictureSizeEdittext.getText().toString();
        this.newTitleSize = titleSizeEdittext.getText().toString();
        this.newReasonSize = reasonSizeEdittext.getText().toString();
        this.newCommentSize = commentSizeEdittext.getText().toString();

        AdminIOGson adminIOGson = new AdminIOGson();
        // TODO may need to start i from 1 instead of 0
        for(int i = 0; i < this.adminList.size(); i++) {
            this.adminList.get(i).setPicSize(Integer.parseInt(newPictureSize));
            this.adminList.get(i).setTitleLength(Integer.parseInt(newTitleSize));
            this.adminList.get(i).setReasonLength(Integer.parseInt(newReasonSize));
            this.adminList.get(i).setCommentLength(Integer.parseInt(newCommentSize));
            adminIOGson.saveAdminInFile(this.adminList.get(i), this);
        }
    }

    /**
     * if current admin wants to create another admin, check validity of name(repeated) save to new file with corresponding  json file name
     * @author donglin
     * @since 2.0
     */
    public void addNewAdmin() {
        String newAdminName = idEdittext.getText().toString();
        if(isAdminExistent(newAdminName)) {
            Toast.makeText(AdminActivity.this, "This Admin already exists", Toast.LENGTH_SHORT).show();
        } else{
            Admin newAdmin = new Admin(newAdminName);
            newAdmin.setPicSize(Integer.parseInt(newPictureSize));
            newAdmin.setTitleLength(Integer.parseInt(newTitleSize));
            newAdmin.setReasonLength(Integer.parseInt(newReasonSize));
            newAdmin.setCommentLength(Integer.parseInt(newCommentSize));
            this.adminList.add(newAdmin);
            AdminIOGson adminIOGson = new AdminIOGson();
            adminIOGson.saveAdminInFile(newAdmin, this);
        }
    }

    /**
     * if if current admin wants to delete account, check if it's the last admin account. if so then deny, if not delete. finish back to MainActvity
     * @author donglin
     * @since 2.0
     */
    public void deleteCurrentAdmin() {
        if(this.adminList.size() == 1) {
            Toast.makeText(AdminActivity.this, "The last Admin should not be deleted", Toast.LENGTH_SHORT).show();
        } else {
            AdminIOGson adminIOGson = new AdminIOGson();
            adminIOGson.deleteFile(this.currentAdminID, this);
            finish();
        }
    }

    /**
     * check if the admin id is the same with corresponding json file name
     * @author donglin
     * @since 2.0
     * @param newAdminID
     * @return
     */
    public boolean isAdminExistent(String newAdminID) {
        String[] jsonFileList = this.fileList();

        // TODO may need to start i from 1 instead of 0
        for(int i = 0; i < jsonFileList.length; i++) {
            if(newAdminID.equals(jsonFileList[i].replace(".json", ""))) {
                return true;
            }
        }

        return false;
    }

}
