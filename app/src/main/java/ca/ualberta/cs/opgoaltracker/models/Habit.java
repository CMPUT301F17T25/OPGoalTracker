/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;


/**
 * This Habit Object allows User to do the implementation to the Habit <br>
 *     This Habit object is passable by intent<br>
 *
 * @author Dichong Song, Yongjia Huang, Mingwei Li
 * @version 3.0
 * @see Parcelable
 * @since 1.0
 */
public class Habit implements Parcelable {

    private String habitType;
    private String reason;
    private Date date;
    private ArrayList<Boolean> period;
    Admin admin = new Admin("admin");

    /**
     * Basic Habit Constructor, allows user to create a Habit object by defining habit name ( habit type), date, reason ,starttime and intervaltime.<br>
     *
     * @param habitType : String
     * @param reason : String
     * @param date : Date
     * @param period : ArrayList<Boolean>
     * @throws StringTooLongException
     * @throws NoTitleException
     */
    public Habit(String habitType, String reason, Date date, ArrayList<Boolean> period) throws StringTooLongException, NoTitleException {
        if (habitType.length()>admin.getTitleLength()){
            throw new StringTooLongException();
        }
        if(habitType.equals("")){
            throw new NoTitleException();
        }
        if (reason.length()>admin.getReasonLength()){
            throw new StringTooLongException();
        }
        this.habitType = habitType;
        this.date = date;
        this.reason = reason;
        this.period = period;
    }


    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @return
     */
    // start of implementing Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param out
     * @param i
     */
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(habitType);
        out.writeString(reason);
        // Write long value of Date
        out.writeLong(date.getTime());
        out.writeList(period);
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param in
     */
    private void readFromParcel(Parcel in) {
        habitType = in.readString();
        reason = in.readString();
        // Read Long value and convert to date
        date = new Date(in.readLong());
        period = in.readArrayList(null);

    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param in
     */
    protected Habit(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     */
    public static final Creator<Habit> CREATOR = new Creator<Habit>() {
        @Override
        public Habit createFromParcel(Parcel in) {
            return new Habit(in);
        }

        @Override
        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };
    // end of implementing Parcelable


    /**
     * Basic Habit Type getter
     * @return habitType : String
     */
    public String getHabitType() {
        return habitType;
    }

    /**
     * Basic Reason getter
     * @return reason : String
     */
    public String getReason() {
        return reason;
    }

    /**
     * Basic Date getter
     * @return date : Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Basic HabitType setter
     * @param habitType String
     * @throws StringTooLongException
     * @throws NoTitleException
     */
    public void setHabitType(String habitType) throws StringTooLongException,NoTitleException {
        if (habitType.length()>admin.getTitleLength()){
            throw new StringTooLongException();
        }
        if(habitType.equals("")){
            throw new NoTitleException();
        }
        this.habitType = habitType;
    }

    /**
     * Basic Reason Setter
     * @param reason String
     * @throws StringTooLongException
     */
    public void setReason(String reason) throws StringTooLongException{
        if (reason.length()>admin.getReasonLength()){
            throw new StringTooLongException();
        }
        this.reason = reason;
    }

    /**
     * Basic Date setter
     * @param date: Long
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Basic period getter
     * @return period ArrayList<Boolean>
     */
    public ArrayList<Boolean> getPeriod() {return period; }

    /**
     * Basic
     * @param period
     */
    public void setPeriod(ArrayList<Boolean> period) {
        this.period = period;
    }
}
