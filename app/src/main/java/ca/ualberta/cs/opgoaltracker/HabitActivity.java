package ca.ualberta.cs.opgoaltracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HabitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(myToolbar);

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
        myMenuInflater.inflate(R.menu.my_menu,menu);
        return true ;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.action_goto_habitEvent){
            startActivity(new Intent(HabitActivity.this, HabitEventActivity.class));
            finish();

        }
        if (item.getItemId() == R.id.action_goto_habit){
            startActivity(new Intent(HabitActivity.this, HabitActivity.class));
            finish();

        }
        if (item.getItemId() == R.id.action_goto_news){
            startActivity(new Intent(HabitActivity.this, NewsActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }




}
