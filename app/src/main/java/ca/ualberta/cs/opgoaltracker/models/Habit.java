package ca.ualberta.cs.opgoaltracker.models;

import java.util.Date;

import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;

/**
 * Created by song on 2017/10/23.
 */

public class Habit {

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
