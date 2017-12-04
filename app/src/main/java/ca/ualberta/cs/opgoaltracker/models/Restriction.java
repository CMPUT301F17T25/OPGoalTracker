/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This Restriction object contains all admin acount setting information.
 * @author Ma Long, Mingwei Li
 * @version 2.0
 * @since 1.0
 */
public class Restriction implements Parcelable {
    int pictureSize;
    int titleSize;
    int reasonSize;
    int commentSize;

    public Restriction(int pictureSize, int titleSize, int reasonSize, int commentSize) {
        this.pictureSize = pictureSize;
        this.titleSize = titleSize;
        this.reasonSize = reasonSize;
        this.commentSize = commentSize;
    }

    public int getPictureSize() {
        return pictureSize;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public int getReasonSize() {
        return reasonSize;
    }

    public int getCommentSize() {
        return commentSize;
    }

    // start of implementing Parcelable
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
     * @param out
     * @param i
     */
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(pictureSize);
        out.writeInt(titleSize);
        out.writeInt(reasonSize);
        out.writeInt(commentSize);
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param in
     */
    private void readFromParcel(Parcel in) {
        pictureSize = in.readInt();
        titleSize = in.readInt();
        reasonSize = in.readInt();
        commentSize = in.readInt();
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param in
     */
    protected Restriction(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     */
    public static final Parcelable.Creator<Restriction> CREATOR = new Parcelable.Creator<Restriction>() {
        @Override
        public Restriction createFromParcel(Parcel in) {
            return new Restriction(in);
        }

        @Override
        public Restriction[] newArray(int size) {
            return new Restriction[size];
        }
    };
    // end of implementing Parcelable
}
