package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by donglin3 on 10/22/17.
 */

public class Photograph implements Parcelable {

    private int height;
    private int width;

    public Photograph(int height, int width){
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    protected Photograph(Parcel in) {
        height = in.readInt();
        width = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);
    }

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