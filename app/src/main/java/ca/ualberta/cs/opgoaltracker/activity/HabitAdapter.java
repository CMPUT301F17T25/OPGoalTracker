/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Habit;

/**
 * Created by arthur on 2017/11/5.
 */

public class HabitAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Habit> habitList;

    // Counstructor
    public HabitAdapter(Context context, ArrayList<Habit> habitList) {
        this.context = context;
        this.habitList = habitList;
    }

    @Override
    public int getCount() {
        return habitList.size();
    }

    @Override
    public Object getItem(int position) {
        return habitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v= View.inflate(context, R.layout.item_habit_list, null);
        TextView separator = (TextView) v.findViewById(R.id.separator);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
        TextView tvReason = (TextView) v.findViewById(R.id.tv_reason);

        Habit thisHabit = habitList.get(position);
        Calendar thisDate = Calendar.getInstance();
        thisDate.setTime(thisHabit.getDate());
        Calendar currentDate = Calendar.getInstance();

        // Set text for separator
//        int a = thisDate.get(Calendar.YEAR);
//        int b = currentDate.get(Calendar.YEAR);
//        int c = thisDate.get(Calendar.DAY_OF_YEAR);
//        int d = currentDate.get(Calendar.DAY_OF_YEAR);
        if ((thisDate.get(Calendar.YEAR) < currentDate.get(Calendar.YEAR)) ||
                (thisDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                        thisDate.get(Calendar.DAY_OF_YEAR) <= currentDate.get(Calendar.DAY_OF_YEAR))) {
            if (thisHabit.getPeriod().get(currentDate.get(Calendar.DAY_OF_WEEK) - 1)) {
                separator.setText("TO-DO");
            } else {
                separator.setText("Not For Today");
            }
        } else {
            separator.setText("Not For Today");
        }
        // Set text for other TextView
        tvTitle.setText(thisHabit.getHabitType());
        tvReason.setText(thisHabit.getReason());
        return v ;
    }
}
