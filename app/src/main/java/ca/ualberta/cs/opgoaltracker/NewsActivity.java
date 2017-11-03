package ca.ualberta.cs.opgoaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by malon_000 on 2017-11-03.
 */

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        // to do: get all habitlist from user followed
        //get last/latest habit from habit list
        //sort in arrayList
        //

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar3);
        myToolbar.setTitle("News");
        setSupportActionBar(myToolbar);

        ArrayList<HabitEvent> displayList = new ArrayList<HabitEvent>();
        try {
            displayList.add(new HabitEvent("test","this is first test",new Date()));
        } catch (CommentTooLongException e) {
            e.printStackTrace();
        }
        try {
            displayList.add(new HabitEvent("test","this is second test",new Date()));
        } catch (CommentTooLongException e) {
            e.printStackTrace();
        }
        try {
            displayList.add(new HabitEvent("test","this is third test",new Date()));
        } catch (CommentTooLongException e) {
            e.printStackTrace();
        }

        HabitEventAdapter adapter = new HabitEventAdapter(this,displayList);
        ListView listview=(ListView)findViewById(R.id.newsList);
        listview.setAdapter(adapter);


        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myMenuInflater = getMenuInflater();
        myMenuInflater.inflate(R.menu.my_menu,menu);
        return true ;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.action_goto_habitEvent){
            startActivity(new Intent(NewsActivity.this, HabitEventActivity.class));
            finish();

        }
        if (item.getItemId() == R.id.action_goto_habit){
            startActivity(new Intent(NewsActivity.this, HabitActivity.class));
            finish();

        }
        if (item.getItemId() == R.id.action_goto_news){
            startActivity(new Intent(NewsActivity.this, NewsActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
