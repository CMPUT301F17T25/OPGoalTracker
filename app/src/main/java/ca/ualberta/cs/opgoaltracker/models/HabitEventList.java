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
 * This HabitEvent List Object contains a list of Habit Event<br>
 *     The ArrayList contains HabitEvent Type Object as instances<br>
 *         Is not used as of final version;
 * @author Donglin Han, Yongjia Huang
 * @version 3.0
 * @see Parcelable
 * @see ArrayList
 * @since 1.0
 */
public class HabitEventList implements Parcelable {
    private ArrayList<HabitEvent> aHabitEventList;
    private String habitType;

    /**
     * Constructor for creating a HabitEventList object with a specific habit type
     * @param habitType
     */
    public HabitEventList(String habitType){
        this.habitType = habitType;
    }

    /**
     * Basic HabitEvent Lisdt getter
     * @return ArrayLisdt<HabitEvent></HabitEvent>
     */
    public ArrayList<HabitEvent> getaHabitEventList() {
        return aHabitEventList;
    }

    /**
     * Basic HabitEvent getter,
     * @param index the index of the specific habitEvent in HabitEventList container
     * @return HabitEvent type object
     */
    public HabitEvent getaHabitEvent(int index){
        return this.aHabitEventList.get(index);
    }

    /**
     * Add a habit event into HabitEventList object
     * @param habitEvent the selected habitevent
     * @throws MismatchedHabitTypeException
     */
    public void addHabitEvent(HabitEvent habitEvent) throws MismatchedHabitTypeException {
        if (!habitEvent.getHabitType().equals(this.habitType)){
            throw new MismatchedHabitTypeException();
        }
        this.aHabitEventList.add(habitEvent);
    }

    /**
     * Delete the specific habitEvent from the HabiteventLIst
     * @param index the selected habit event
     * @throws IndexOutOfBoundsException
     */
    public void deleteHabitEvent(int index) throws IndexOutOfBoundsException{
        if (index<this.aHabitEventList.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.aHabitEventList.remove(index);
    }

    /**
     * The Search method, search the keyword of a habitevent
     * @param keyword : String
     * @return ArrayList<HabitEvent> which contains all HabitEvent that has that key word </HabitEvent>
     */
    public ArrayList<HabitEvent> search(String keyword) {
        ArrayList<HabitEvent> habitEventWithKeywordList = new ArrayList<>();
        for (HabitEvent habitEvent : this.aHabitEventList) {
            if (habitEvent.getComment().contains(keyword)) {
                habitEventWithKeywordList.add(habitEvent);
            }
        }
        return habitEventWithKeywordList;
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param in
     */
    protected HabitEventList(Parcel in) {
        if (in.readByte() == 0x01) {
            aHabitEventList = new ArrayList<HabitEvent>();
            in.readList(aHabitEventList, HabitEvent.class.getClassLoader());
        } else {
            aHabitEventList = null;
        }
        habitType = in.readString();
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
        if (aHabitEventList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(aHabitEventList);
        }
        dest.writeString(habitType);
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     */
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