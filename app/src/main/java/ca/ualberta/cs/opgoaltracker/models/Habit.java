package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;

/**
 * Created by song on 2017/10/23.
 */

public class Habit implements Parcelable {

    private String habitType;
    private String reason;
    private Date date;
    private long startTime;
    private long intervalTime;
    Admin admin = new Admin("admin");

    public Habit(String habitType, String reason, Date date, long startTime, long intervalTime) throws StringTooLongException,NoTitleException {
        if(habitType.length() > admin.getTitleLength() || reason.length()> admin.getReasonLength() ) {
            throw new StringTooLongException();
        }
        if(habitType.equals("")){
            throw new NoTitleException();
        }
        this.habitType = habitType;
        this.date = date;
        this.reason = reason;
        this.startTime = startTime;
        this.intervalTime = intervalTime;
    }

    // start of implementing Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(habitType);
        out.writeString(reason);
        // Write long value of Date
        out.writeLong(date.getTime());
        out.writeLong(startTime);
        out.writeLong(intervalTime);
    }

    private void readFromParcel(Parcel in) {
        habitType = in.readString();
        reason = in.readString();
        // Read Long value and convert to date
        date = new Date(in.readLong());
        startTime = in.readLong();
        intervalTime = in.readLong();

    }

    protected Habit(Parcel in) {
        readFromParcel(in);
    }

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

    public String getHabitType() {
        return habitType;
    }

    public String getReason() {
        return reason;
    }

    public Date getDate() {
        return date;
    }

    public long getStartTime(){return startTime;}

    public long getIntervalTime(){return intervalTime;}

    public void setHabitType(String habitType) throws StringTooLongException,NoTitleException {
        if (habitType.length()>admin.getTitleLength()){
            throw new StringTooLongException();
        }
        if(habitType.equals("")){
            throw new NoTitleException();
        }
        this.habitType = habitType;
    }

    public void setReason(String reason) throws StringTooLongException{
        if (reason.length()>admin.getReasonLength()){
            throw new StringTooLongException();
        }
        this.reason = reason;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPeriod(long startTime, long intervalTime){
        this.startTime = startTime;
        this.intervalTime = intervalTime;
    }
}
