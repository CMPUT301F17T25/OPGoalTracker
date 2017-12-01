/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

import ca.ualberta.cs.opgoaltracker.exception.DuplicatedHabitException;

/**
 * This HabitList Object contains a list of Habit<br>
 *     The ArrayList contains HabitType Object as instances<br>
 * @author Dichong Song, Yongjia Huang, Mingwei Li
 * @version 3.0
 * @see Parcelable
 * @see ArrayList
 * @since 1.0
 */
public class HabitList implements Parcelable {
    private ArrayList<Habit> habitArrayList;

    public  HabitList(){
        habitArrayList = new ArrayList<Habit>();
    }

    /**
     * Basic HabitList getter
     * @return ArrayList<Habit></Habit>
     */
    public ArrayList<Habit> getArrayList() {return habitArrayList;}

    /**
     * Basic HabitList setter
     * @param habitArrayList ArrayList<Habit>
     */
    public void setArrayList(ArrayList<Habit> habitArrayList) {this.habitArrayList = habitArrayList;}

    public int size(){
        return habitArrayList.size();
    }
    /**
     * Basic Habit getter
     * @param index the selected index of the Habit
     * @return Habit type object
     */
    public Habit getHabit(int index){
        return this.habitArrayList.get(index);
    }

    /**
     * Basic add habit method , be able to add a Habit type object into the HabitList
     * @param habit The selected Habit
     * @throws DuplicatedHabitException
     */
    public void addHabit(Habit habit) {
        this.habitArrayList.add(habit);
    }

    /**
     * Basic delete Habit method, be able to delete a habit from the arraylist
     * @param index the selected Habit object
     * @throws IndexOutOfBoundsException
     */
    public void remove(int index) throws IndexOutOfBoundsException{
        if (index<this.habitArrayList.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.habitArrayList.remove(index);
    }

    /**
     * Sort habitList by create date and then move habit that need to do today to the front of the ArrayList.
     */
    public void sort() {
        ArrayList<Habit> todo = new ArrayList<Habit>();
        ArrayList<Habit> notForToday = new ArrayList<Habit>();

        // sort habitArrayList by createDate in ascending order
        Collections.sort(this.habitArrayList);

        // for each habit in habitArrayList, check if it need to do today, and add it into proper ArrayList
        for(Habit habit : this.habitArrayList) {
            if (habit.isTodo()) {
                todo.add(habit);
            } else {
                notForToday.add(habit);
            }
        }

        // combine two ArrayList and replace this.habitArrayList
        todo.addAll(notForToday);
        this.habitArrayList = todo;
    }

    /**
     *
     */
    public void habitStatusIndicator(){
        //todo
    }


    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param in
     */
    protected HabitList(Parcel in) {
        if (in.readByte() == 0x01) {
            habitArrayList = new ArrayList<Habit>();
            in.readList(habitArrayList, Habit.class.getClassLoader());
        } else {
            habitArrayList = null;
        }
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (habitArrayList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(habitArrayList);
        }
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HabitList> CREATOR = new Parcelable.Creator<HabitList>() {
        @Override
        public HabitList createFromParcel(Parcel in) {
            return new HabitList(in);
        }

        @Override
        public HabitList[] newArray(int size) {
            return new HabitList[size];
        }
    };
}