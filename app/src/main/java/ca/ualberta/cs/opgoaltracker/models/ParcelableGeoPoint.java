/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.osmdroid.util.GeoPoint;

/**
 * Created by yongjiahuang on 2017-11-28.
 */

public class ParcelableGeoPoint implements Parcelable {

    private GeoPoint geoPoint;

    public ParcelableGeoPoint(GeoPoint point) {
        geoPoint = point;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(geoPoint.getLatitudeE6());
        out.writeInt(geoPoint.getLongitudeE6());
    }

    public static final Parcelable.Creator<ParcelableGeoPoint> CREATOR
            = new Parcelable.Creator<ParcelableGeoPoint>() {
        public ParcelableGeoPoint createFromParcel(Parcel in) {
            return new ParcelableGeoPoint(in);
        }

        public ParcelableGeoPoint[] newArray(int size) {
            return new ParcelableGeoPoint[size];
        }
    };

    private ParcelableGeoPoint(Parcel in) {
        int lat = in.readInt();
        int lon = in.readInt();
        geoPoint = new GeoPoint(lat, lon);
    }
}