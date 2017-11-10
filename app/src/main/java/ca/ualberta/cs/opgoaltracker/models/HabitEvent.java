package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEvent implements Parcelable {

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

    protected HabitEvent(Parcel in) {
        habitType = in.readString();
        comment = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        photo = (Photograph) in.readValue(Photograph.class.getClassLoader());
        location = in.readString();
        maxPhotoSize = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(habitType);
        dest.writeString(comment);
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeValue(photo);
        dest.writeString(location);
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