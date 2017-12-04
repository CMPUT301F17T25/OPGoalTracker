/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.Restriction;

public class HabitAddActivity extends AppCompatActivity {

    private EditText titleBox;
    private EditText reasonBox;
    private CalendarView calendarView;
    private CheckBox checkBoxSun;
    private CheckBox checkBoxMon;
    private CheckBox checkBoxTue;
    private CheckBox checkBoxWed;
    private CheckBox checkBoxThur;
    private CheckBox checkBoxFri;
    private CheckBox checkBoxSat;

    private Habit habit;
    private String title;
    private String reason;
    private String owner;
    private Date date;
    private ArrayList<Boolean> period;
    private Restriction restriction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_add);

        owner = getIntent().getStringExtra("user");
        restriction = getIntent().getParcelableExtra("restriction");


        titleBox = (EditText) findViewById(R.id.editTitleAdd);
        reasonBox = (EditText) findViewById(R.id.editReasonAdd);
        calendarView = (CalendarView) findViewById(R.id.calendarViewAdd);
        checkBoxSun = (CheckBox) findViewById(R.id.checkBoxSunAdd);
        checkBoxMon = (CheckBox) findViewById(R.id.checkBoxMonAdd);
        checkBoxTue = (CheckBox) findViewById(R.id.checkBoxTueAdd);
        checkBoxWed = (CheckBox) findViewById(R.id.checkBoxWedAdd);
        checkBoxThur = (CheckBox) findViewById(R.id.checkBoxThurAdd);
        checkBoxFri = (CheckBox) findViewById(R.id.checkBoxFriAdd);
        checkBoxSat = (CheckBox) findViewById(R.id.checkBoxSatAdd);

        date = new Date(); // this is necessary to set default date as today if user didn't change date
        // get selected date from CalendarView
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                date = new Date(calendar.getTimeInMillis());
            }
        });
    }

    /**
     * Invoke this method if Save button at the bottom of the activity is pressed.
     * Create a Habit object with user input value, then pass the Habit object back to HabitFragment
     * @param view
     */
    public void buttonCreate(View view) {
        title = titleBox.getText().toString();
        reason = reasonBox.getText().toString();

        period = new ArrayList<Boolean>();
        period.add(checkBoxSun.isChecked());
        period.add(checkBoxMon.isChecked());
        period.add(checkBoxTue.isChecked());
        period.add(checkBoxWed.isChecked());
        period.add(checkBoxThur.isChecked());
        period.add(checkBoxFri.isChecked());
        period.add(checkBoxSat.isChecked());

        try {
            habit = new Habit(title, reason, date, period, restriction.getTitleSize(), restriction.getReasonSize());
            habit.setOwner(owner);
        } catch (StringTooLongException exc) {
            Toast.makeText(getApplicationContext(), "Too Many Characters",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (NoTitleException exc) {
            Toast.makeText(getApplicationContext(), "Please Enter Title",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // add this habit in Elasticsearch
        ElasticsearchController.AddHabitsTask addHabitsTask = new ElasticsearchController.AddHabitsTask();
        addHabitsTask.execute(habit);

        Intent intent = new Intent(this, MenuPage.class);
        intent.putExtra("Habit", habit);
        setResult(RESULT_OK,intent);
        finish();
    }
}
