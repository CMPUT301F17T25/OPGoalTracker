/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;

/**
 * Created by song on 2017/11/30.
 */

public class ParticipantName implements Parcelable {
    private String id;
    /**
     * Basic Constructor for creating a Participant with specific user id.
     * @param id
     */
    public ParticipantName(String id) {
        this.id = id;
    }

    public ParticipantName(Participant p){this.id  =  p.getId();}
    /**
     * Basic id getter
     * @return id : String
     */
    public String getId(){
        return id;
    }

    public boolean equals(Participant p){
        if (this.getId().equals(p.getId())){
            return true;
        }
        return false;
    }

    protected ParticipantName(Parcel in) {
        id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ParticipantName> CREATOR = new Parcelable.Creator<ParticipantName>() {
        @Override
        public ParticipantName createFromParcel(Parcel in) {
            return new ParticipantName(in);
        }

        @Override
        public ParticipantName[] newArray(int size) {
            return new ParticipantName[size];
        }
    };
}
