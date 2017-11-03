package ca.ualberta.cs.opgoaltracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by malon_000 on 2017-11-03.
 */

public class HabitEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        // to do: get all habitlist from user followed
        //get last/latest habit from habit list
        //sort in arrayList
        //

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
}
