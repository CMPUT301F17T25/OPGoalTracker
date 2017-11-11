/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.exception.MismatchedHabitTypeException;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEventList implements Parcelable {
    private ArrayList<HabitEvent> aHabitEventList;
    private String habitType;

    public HabitEventList(String habitType){
        this.habitType = habitType;
    }

    public ArrayList<HabitEvent> getaHabitEventList() {
        return aHabitEventList;
    }

    public HabitEvent getaHabitEvent(int index){
        return this.aHabitEventList.get(index);
    }

    public void addHabitEvent(HabitEvent habitEvent) throws MismatchedHabitTypeException {
        if (!habitEvent.getHabitType().equals(this.habitType)){
            throw new MismatchedHabitTypeException();
        }
        this.aHabitEventList.add(habitEvent);
    }

    public void deleteHabitEvent(int index) throws IndexOutOfBoundsException{
        if (index<this.aHabitEventList.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.aHabitEventList.remove(index);
    }

    public ArrayList<HabitEvent> search(String keyword) {
        ArrayList<HabitEvent> habitEventWithKeywordList = new ArrayList<>();
        for (HabitEvent habitEvent : this.aHabitEventList) {
            if (habitEvent.getComment().contains(keyword)) {
                habitEventWithKeywordList.add(habitEvent);
            }
        }
        return habitEventWithKeywordList;
    }

    protected HabitEventList(Parcel in) {
        if (in.readByte() == 0x01) {
            aHabitEventList = new ArrayList<HabitEvent>();
            in.readList(aHabitEventList, HabitEvent.class.getClassLoader());
        } else {
            aHabitEventList = null;
        }
        habitType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (aHabitEventList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(aHabitEventList);
        }
        dest.writeString(habitType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HabitEventList> CREATOR = new Parcelable.Creator<HabitEventList>() {
        @Override
        public HabitEventList createFromParcel(Parcel in) {
            return new HabitEventList(in);
        }

        @Override
        public HabitEventList[] newArray(int size) {
            return new HabitEventList[size];
        }
    };
}