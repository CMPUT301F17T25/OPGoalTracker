/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.exception.DuplicatedHabitException;

/**
 * This HabitList Object contains a list of Habit<br>
 *     The ArrayList contains HabitType Object as instances<br>
 * @author Dichong Song, Yongjia Huang
 * @version 3.0
 * @see Parcelable
 * @see ArrayList
 * @since 1.0
 */
public class HabitList implements Parcelable {
    private ArrayList<Habit> aHabitList ;

    /**
     * Basic HabitList getter
     * @return ArrayList<Habit></Habit>
     */
    public  HabitList(){
        aHabitList = new ArrayList<Habit>();
    }
    public ArrayList<Habit> getHabitList() {
        return aHabitList;
    }


    public int getListLen(){
        return aHabitList.size();
    }
    /**
     * Basic Habit getter
     * @param index the selected index of the Habit
     * @return Habit type object
     */
    public Habit getHabit(int index){
        return this.aHabitList.get(index);
    }

    /**
     * Basic add habit method , be able to add a Habit type object into the HabitList
     * @param habit The selected Habit
     * @throws DuplicatedHabitException
     */
    public void addHabit(Habit habit) throws DuplicatedHabitException {
        //not fully implemented, should just check habitType
        if (aHabitList.contains(habit)){
            throw new DuplicatedHabitException();
        }
        this.aHabitList.add(habit);
    }

    /**
     * Basic delete Habit method, be able to delete a habit from the arraylist
     * @param index the selected Habit object
     * @throws IndexOutOfBoundsException
     */
    public void deleteHabit(int index) throws IndexOutOfBoundsException{
        if (index<this.aHabitList.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.aHabitList.remove(index);
    }

    /**
     *
     */
    public void todayHabit(){
        //todo
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
            aHabitList = new ArrayList<Habit>();
            in.readList(aHabitList, Habit.class.getClassLoader());
        } else {
            aHabitList = null;
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
        if (aHabitList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(aHabitList);
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