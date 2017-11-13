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

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Habit;

public class HabitAddActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_habit_add);

        titleBox = (EditText) findViewById(R.id.editTitleAdd);
        reasonBox = (EditText) findViewById(R.id.editReasonAdd);
        calendarView = (CalendarView) findViewById(R.id.calendarViewAdd);
        checkBoxMon = (CheckBox) findViewById(R.id.checkBoxMonAdd);
        checkBoxTue = (CheckBox) findViewById(R.id.checkBoxTueAdd);
        checkBoxWed = (CheckBox) findViewById(R.id.checkBoxWedAdd);
        checkBoxThur = (CheckBox) findViewById(R.id.checkBoxThurAdd);
        checkBoxFri = (CheckBox) findViewById(R.id.checkBoxFriAdd);
        checkBoxSat = (CheckBox) findViewById(R.id.checkBoxSatAdd);
        checkBoxSun = (CheckBox) findViewById(R.id.checkBoxSunAdd);
    }

    public void buttonCreate (View view) {
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

        habit = new Habit(title, reason, date, period);

        Intent intent = new Intent(this, MenuPage.class);
        intent.putExtra("Habit", habit);
        setResult(RESULT_OK,intent);
        finish();
    }
}
