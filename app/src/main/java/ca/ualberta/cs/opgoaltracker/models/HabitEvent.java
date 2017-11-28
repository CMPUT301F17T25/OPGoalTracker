/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;

/**
 * This Habit Event describe a single Habit Event which belongs to a Habit Type<br>
 *     This Class allow user to get and set information in this object
 * @author Donglin Han, Yongjia Huang, Mingwei Li
 * @version 3.0
 * @see Parcelable
 * @since 1.0
 */
public class HabitEvent implements Parcelable {

    private String id;
    private String habitType;
    private String comment;
    private Date date;
    private Photograph photo;
    private String lat;
    private String lng;
    private int maxPhotoSize = 65536;

    /**
     * Set id for the HabitEvent object
     * @return id : String
     */
    public String getId() {
        return id;
    }

    /**
     * Get id of the HabitEvent object
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Base Constructor which create a Habit Event type <br>
     *     the New Habit Event type has a restrict commetn length smaller than 20<br>
     * @param habitType
     * @param comment
     * @param date
     * @throws CommentTooLongException
     */
    public HabitEvent(String habitType, String comment, Date date) throws CommentTooLongException {
        if(comment.length() > 20) {
            throw new CommentTooLongException();
        }

        this.habitType = habitType;
        this.date = date;
        this.comment = comment;
    }

    /**
     * Basic Habit Type Getter
     * @return String : habitType
     */
    public String getHabitType() {
        return habitType;
    }

    /**
     * Basic Habit event comment getter
     * @return String : Comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Basic Habit Event Date getter
     * @return Date : date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Basic Habit Event Photo getter
     * @return Photograph : photo
     */
    public Photograph getPhoto() {
        return photo;
    }

    /**
     * Basic Habit Event Location getter
     * @return String : location
     */
    public String[] getLocation() {
        return new String[] {lat,lng};
    }

    /**
     * Basic Habit Event HabitType setter
     * @param habitType : String
     */
    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }

    /**
     *  Basic Habit Event Comment Setter
     * @param comment String
     * @throws CommentTooLongException
     */
    public void setComment(String comment) throws CommentTooLongException {
        if(comment.length() > 20) {
            throw new CommentTooLongException();
        }
        this.comment = comment;
    }

    /**
     *  Basic Habit Event Date Setter
     * @param date : Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *  Basic Habit Event Photo setter
     * @param photo Photograph
     * @throws ImageTooLargeException
     */
    public void setPhoto(Photograph photo) throws ImageTooLargeException {
        // Assuming 24bits/pixel. Note that 8bits/byte.
        int photoSize = photo.getHeight() * photo.getWidth() * 24 / 8;
        if( photoSize > this.maxPhotoSize){
            throw new ImageTooLargeException();
        }
        this.photo = photo;
    }

    /**
     *  Basic Habit Event Location setter
     *
     */
    public void setLocation(String lat,String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     *  Basic Habit Event User getter
     * @return : String
     */
    public String getUser(){
        return "User return";
    }

    @Override
    public String toString(){
        return habitType+comment;
    }

    protected HabitEvent(Parcel in) {
        id = in.readString();
        habitType = in.readString();
        comment = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        photo = (Photograph) in.readValue(Photograph.class.getClassLoader());
        lat = in.readString();
        lng = in.readString();
        maxPhotoSize = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(habitType);
        dest.writeString(comment);
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeValue(photo);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeInt(maxPhotoSize);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HabitEvent> CREATOR = new Parcelable.Creator<HabitEvent>() {
        @Override
        public HabitEvent createFromParcel(Parcel in) {
            return new HabitEvent(in);
        }

        @Override
        public HabitEvent[] newArray(int size) {
            return new HabitEvent[size];
        }
    };
}