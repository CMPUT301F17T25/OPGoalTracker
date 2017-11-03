package ca.ualberta.cs.opgoaltracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HabitEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        myToolbar.setTitle("Habit Event");
        setSupportActionBar(myToolbar);


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
            startActivity(new Intent(HabitEventActivity.this, HabitEventActivity.class));
            finish();

        }
        if (item.getItemId() == R.id.action_goto_habit){
            startActivity(new Intent(HabitEventActivity.this, HabitActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}

