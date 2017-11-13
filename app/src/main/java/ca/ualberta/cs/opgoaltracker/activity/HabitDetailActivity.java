/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;
import ca.ualberta.cs.opgoaltracker.models.Habit;

public class HabitDetailActivity extends AppCompatActivity {

    private EditText titleBox;
    private EditText reasonBox;
    private CalendarView calendarView;
    private CheckBox checkBoxMon;
    private CheckBox checkBoxTue;
    private CheckBox checkBoxWed;
    private CheckBox checkBoxThur;
    private CheckBox checkBoxFri;
    private CheckBox checkBoxSat;
    private CheckBox checkBoxSun;

    private Habit habit;
    private String title;
    private String reason;
    private Date date;
    private ArrayList<Boolean> period;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        habit = (Habit) getIntent().getExtras().getParcelable("Habit");

        titleBox = (EditText) findViewById(R.id.editTitleDetail);
        reasonBox = (EditText) findViewById(R.id.editReasonDetail);
        calendarView = (CalendarView) findViewById(R.id.calendarViewDetail);
        checkBoxMon = (CheckBox) findViewById(R.id.checkBoxMonDetail);
        checkBoxTue = (CheckBox) findViewById(R.id.checkBoxTueDetail);
        checkBoxWed = (CheckBox) findViewById(R.id.checkBoxWedDetail);
        checkBoxThur = (CheckBox) findViewById(R.id.checkBoxThurDetail);
        checkBoxFri = (CheckBox) findViewById(R.id.checkBoxFriDetail);
        checkBoxSat = (CheckBox) findViewById(R.id.checkBoxSatDetail);
        checkBoxSun = (CheckBox) findViewById(R.id.checkBoxSunDetail);

        titleBox.setText(habit.getHabitType());
        reasonBox.setText(habit.getReason());
        calendarView.setDate(habit.getDate().getTime(), false, true);
        checkBoxMon.setChecked(habit.getPeriod().get(0));
        checkBoxTue.setChecked(habit.getPeriod().get(1));
        checkBoxWed.setChecked(habit.getPeriod().get(2));
        checkBoxThur.setChecked(habit.getPeriod().get(3));
        checkBoxFri.setChecked(habit.getPeriod().get(4));
        checkBoxSat.setChecked(habit.getPeriod().get(5));
        checkBoxSun.setChecked(habit.getPeriod().get(6));
    }

    public void buttonSave(View view) {
        title = titleBox.getText().toString();
        reason = reasonBox.getText().toString();
        date = new Date(calendarView.getDate());

        period = new ArrayList<Boolean>();
        period.add(checkBoxMon.isChecked());
        period.add(checkBoxTue.isChecked());
        period.add(checkBoxWed.isChecked());
        period.add(checkBoxThur.isChecked());
        period.add(checkBoxFri.isChecked());
        period.add(checkBoxSat.isChecked());
        period.add(checkBoxSun.isChecked());

        try {
            habit.setHabitType(title);
        } catch (StringTooLongException exc) {
            Toast.makeText(getApplicationContext(), "Too Many Characters",
                    Toast.LENGTH_SHORT).show();
        } catch (NoTitleException exc) {
            Toast.makeText(getApplicationContext(), "Please Enter Title",
                    Toast.LENGTH_SHORT).show();
        }

        try {
            habit.setReason(reason);
        } catch (StringTooLongException exc) {
            Toast.makeText(getApplicationContext(), "Too Many Characters",
                    Toast.LENGTH_SHORT).show();
        }

        habit.setDate(date);
        habit.setPeriod(period);
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
