package ca.ualberta.cs.opgoaltracker;

import android.media.Image;

import java.util.Date;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEvent {
    private String habitType;
    private String comment;
    private Date date;
    private Image picture;
    private String location;

    public HabitEvent(String habitType, String comment, Date date){
        this.habitType = habitType;
        this.comment = comment;
        this.date = date;
    }

    public String getHabitType() {
        return habitType;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public Image getPicture() {
        return picture;
    }

    public String getLocation() {
        return location;
    }

    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

