package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ca.ualberta.cs.opgoaltracker.R;

public class HabitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

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




}
