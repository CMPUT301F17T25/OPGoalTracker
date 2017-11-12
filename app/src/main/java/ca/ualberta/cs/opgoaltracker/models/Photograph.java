/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This Photograph object is created from image setting propuse
 * @author Donglin Han, Yongjia Huang
 * @version 3.0
 * @see Parcelable
 * @since 1.0
 */
public class Photograph implements Parcelable {

    private int height;
    private int width;

    /**
     * Basic Constructor for creating a photograph object
     * @param height : int
     * @param width : int
     */
    public Photograph(int height, int width){
        this.height = height;
        this.width = width;
    }

    /**
     * Basic height getter
     * @return height : int
     */
    public int getHeight() {
        return height;
    }

    /**
     * Basic width getter
     * @return width int
     */
    public int getWidth() {
        return width;
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param in
     */
    protected Photograph(Parcel in) {
        height = in.readInt();
        width = in.readInt();
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
        dest.writeInt(height);
        dest.writeInt(width);
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Photograph> CREATOR = new Parcelable.Creator<Photograph>() {
        @Override
        public Photograph createFromParcel(Parcel in) {
            return new Photograph(in);
        }

        @Override
        public Photograph[] newArray(int size) {
            return new Photograph[size];
        }
    };
}