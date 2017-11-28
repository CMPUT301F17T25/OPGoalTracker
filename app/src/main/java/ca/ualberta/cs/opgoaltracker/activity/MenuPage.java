/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Participant;



/**
 * This is a Navigation Drawer activity <br>
 * This is the Menu side bar for this app <br>
 * This Menu contains -> Habit, Habit Event, Friends, Social, My Account Setting<br>
 * This Menu recives an Participant Object from the Login or Register page<br>
 * This Menu palys a Object collaction role in this project, all objects are sent from this menu<br>
 *
 * @author Yongjia Huang
 * @version 3.0
 * @see AppCompatActivity
 * @since 1.0
 */
public class MenuPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,MyAccountFragment.OnFragmentInteractionListener,NewsFragment.OnFragmentInteractionListener,FriendFragment.OnFragmentInteractionListener,HabitFragment.OnFragmentInteractionListener,HabitEventFragment.OnFragmentInteractionListener{

    private static final String FILENAME = "profile_file.sav";
    String picturePath;
    ImageButton button;

    Participant currentUser;
    Participant currentUser1;
    HabitFragment habitFragment = new HabitFragment();
    HabitEventFragment habitEventFragment = new HabitEventFragment();
    FriendFragment friendFragment = new   FriendFragment();
    NewsFragment newsFragment = new   NewsFragment();
    MyAccountFragment myAccountFragment = new MyAccountFragment();
    TextView comment;
    TextView id;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        currentUser = getIntent().getParcelableExtra("LOGINUSER");
        currentUser1 = getIntent().getParcelableExtra("REQUESTADDFRIEND");
        if (savedInstanceState == null) {
            if (currentUser1!= null){
                Bundle args = new Bundle();
                args.putParcelable("CURRENTUSER",currentUser1);
                friendFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout_1, friendFragment).commit();
            }else{
                Bundle args = new Bundle();
                args.putParcelable("CURRENTUSER",currentUser);
                habitFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout_1, habitFragment).commit();
            }

        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


//        if(drawer.isDrawerOpen(GravityCompat.START)) {
//            Toast.makeText(MenuPage.this, "Got you!", Toast.LENGTH_SHORT).show();
//        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        comment = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menuPageComment);
        comment.setText(currentUser.getComment());
        id = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menuPageId);
        id.setText(currentUser.getId());

        // https://stackoverflow.com/questions/33540090/textview-from-navigationview-header-returning-null
        button = (ImageButton) navigationView.getHeaderView(0).findViewById(R.id.menu_profile_image);
        loadFromFile();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFromFile();
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_menu_page, null);
        super.setContentView(fullView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                comment.setText(currentUser.getComment());
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                comment.setText(currentUser.getComment());
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onResume() {
        super.onResume();
        comment.setText(currentUser.getComment());
        loadFromFile();
    }

    public void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Taken from https://github.com/google/gson/blob/master/UserGuide.md#TOC-Collections-Examples 2017-09-19
            picturePath = gson.fromJson(in, String.class);
            button.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        } catch (FileNotFoundException e) {
            button.setImageResource(R.drawable.newevent);
        }
    }

    /**
     * Default Navigation Drawer onBackPressed method.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**
     * Default Navigation Drawer onCreateOptionMenu method
     * @param menu add menu from menu file
     * @return True if success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page, menu);
        return true;
    }


    /**
     * Default Navigation Drawer onOptionsItemSelected method
     * @param item the selected menu item
     * @return True if success
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is the main connection between the menu activity and the different fragments.
     * This method passes the Participant object (from Login page) to its fragments.
     * @param item the provied menu items
     * @return boolen, true if success
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle args = new Bundle();
        args.putParcelable("CURRENTUSER",currentUser);

        if (id == R.id.habit) {
            this.setTitle("Habit");
            habitFragment.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_1,habitFragment,habitFragment.getTag()).commit();
        } else if (id == R.id.habitEvent) {
            this.setTitle("Habit Event");
            habitEventFragment.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_1,habitEventFragment,habitEventFragment.getTag()).commit();

        } else if (id == R.id.friends) {
            this.setTitle("My Friends");
            friendFragment.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_1,friendFragment,friendFragment.getTag()).commit();

        } else if (id == R.id.social) {
            this.setTitle("News");
            newsFragment.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_1,newsFragment ,newsFragment .getTag()).commit();

        } else if (id == R.id.setting) {
            this.setTitle("My Account");
            myAccountFragment.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_1,myAccountFragment ,myAccountFragment .getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Default Navigation Drawer setActionBarTitle method
     * @param title the title bar message.
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    /**
     * Disable onFragmentInteraction method.
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
