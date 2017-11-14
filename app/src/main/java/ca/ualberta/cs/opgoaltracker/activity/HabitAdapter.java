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
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
        TextView tvReason = (TextView) v.findViewById(R.id.tv_reason);

        // Set text for TextView
        tvTitle.setText(habitList.get(position).getHabitType());
        tvReason.setText(String.valueOf(habitList.get(position).getReason()));
        return v ;
    }
}
