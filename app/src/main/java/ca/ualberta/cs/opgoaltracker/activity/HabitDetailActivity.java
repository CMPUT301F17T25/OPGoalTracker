package ca.ualberta.cs.opgoaltracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.Date;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Habit;

public class HabitDetailActivity extends AppCompatActivity {

    private Habit habit;
    private EditText title;
    private EditText reason;
    private CalendarView calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        habit = (Habit) getIntent().getExtras().getParcelable("Habit");

        title = (EditText) findViewById(R.id.editTitleDetail);
        reason = (EditText) findViewById(R.id.editReasonDetail);
        calendar = (CalendarView) findViewById(R.id.calendarViewDetail);

        title.setText(habit.getHabitType());
        reason.setText(habit.getReason());

        calendar.setDate(habit.getDate().getTime(), false, true);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_habit_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.habit_detail_delete) {
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }
}
