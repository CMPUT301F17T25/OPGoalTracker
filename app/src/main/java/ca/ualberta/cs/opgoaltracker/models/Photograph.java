/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;

import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;

/**
 * This Photograph object is created from image setting propuse
 * @author Donglin Han, Yongjia Huang, Mingwei Li
 * @version 4.0
 * @see Parcelable
 * @since 1.0
 */
public class Photograph implements Parcelable {

    private int height;
    private int width;
    private byte[] photo;


    /**
     * Basic Constructor for creating a photograph object
     * @param picturePath : path of selected picture
     */
    public Photograph(String picturePath) throws ImageTooLargeException {
        Bitmap p = BitmapFactory.decodeFile(picturePath);
        File file = new File(picturePath);
        long length = file.length();

        if (length > 65535) {
            throw new ImageTooLargeException();
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        p.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        photo = stream.toByteArray();
        this.width = p.getWidth();
        this.height= p.getHeight();

    }


    /**
     * getter for the image in bigmap format
     * @return Bitmap b
     */
    public Bitmap getBitMap(){
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        return bmp;
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
    public Photograph(Parcel in) {
        this.height = in.readInt();
        this.width = in.readInt();
        photo = new byte[in.readInt()];
        in.readByteArray(photo);
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
        dest.writeInt(this.height);
        dest.writeInt(this.width);
        dest.writeInt(photo.length);
        dest.writeByteArray(photo);

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