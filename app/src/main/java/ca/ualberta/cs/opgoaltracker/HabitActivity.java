package ca.ualberta.cs.opgoaltracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class HabitActivity extends AppCompatActivity {

    private ArrayList<Habit> habitList; // may be changed after pull
    private ListView lvHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        lvHabit = (ListView) findViewById(R.id.list_habit);

        // jump to HabitAddActivity if floating action button is clicked
        FloatingActionButton add_habit = (FloatingActionButton) findViewById(R.id.add_habit);
        add_habit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(HabitActivity.this, HabitAddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myMenuInflater = getMenuInflater();
        return true ;
    }

}
