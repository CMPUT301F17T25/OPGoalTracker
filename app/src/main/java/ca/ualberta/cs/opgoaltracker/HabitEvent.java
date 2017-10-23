package ca.ualberta.cs.opgoaltracker;

import java.util.Date;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEvent {

    private String habitType;
    private String comment;
    private Date date;
    private Photograph photo;
    private String location;
    Admin admin = new Admin("admin");

    public HabitEvent(String habitType, String comment, Date date) throws StringTooLongException {
        if(comment.length() > admin.getCommentLength()) {
            throw new StringTooLongException();
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

    public void setComment(String comment) throws StringTooLongException {
        if(comment.length() > admin.getCommentLength()) {
            throw new StringTooLongException();
        }
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPhoto(Photograph photo) throws ImageTooLargeException{
        // Assuming 24bits/pixel. Note that 8bits/byte.
        int photoSize = photo.getHeight() * photo.getWidth() * 24 / 8;
        if( photoSize > admin.getPicSize()){
            throw new ImageTooLargeException();
        }
        this.photo = photo;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

