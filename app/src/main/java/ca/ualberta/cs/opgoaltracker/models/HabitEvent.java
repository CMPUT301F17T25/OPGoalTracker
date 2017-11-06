package ca.ualberta.cs.opgoaltracker.models;

import java.util.Date;

import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEvent {

    private String habitType;
    private String comment;
    private Date date;
    private Photograph photo;
    private String location;
    private int maxPhotoSize = 65536;

    public HabitEvent(String habitType, String comment, Date date) throws CommentTooLongException {
        if(comment.length() > 20) {
            throw new CommentTooLongException();
        }

        this.habitType = habitType;
        this.date = date;
        this.comment = comment;
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

    public Photograph getPhoto() {
        return photo;
    }

    public String getLocation() {
        return location;
    }

    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }

    public void setComment(String comment) throws CommentTooLongException {
        if(comment.length() > 20) {
            throw new CommentTooLongException();
        }
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPhoto(Photograph photo) throws ImageTooLargeException {
        // Assuming 24bits/pixel. Note that 8bits/byte.
        int photoSize = photo.getHeight() * photo.getWidth() * 24 / 8;
        if( photoSize > this.maxPhotoSize){
            throw new ImageTooLargeException();
        }
        this.photo = photo;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser(){
        return "User return";
    }
}

