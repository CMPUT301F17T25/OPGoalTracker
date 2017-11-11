/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;

/**
 * Created by malon_000 on 2017-10-22.
 */

public class Participant implements Parcelable {
    private Photograph avatar;
    private HabitList habitList;
    private FriendList followerList;
    private FriendList followeeList;
    private FriendList requestList;
    private String id;

    public Participant(String id) {
        this.id = id;
    }


    public Photograph getAvatar(){
        return avatar;
    }
    public void setAvatar(Photograph avatar) throws ImageTooLargeException {
        int avatarSize = avatar.getHeight() * avatar.getWidth() * 24 / 8;
        if( avatarSize > 65336){
            throw new ImageTooLargeException();
        }
        this.avatar=avatar;
    }
    public String getId(){
        return id;
    }

    public HabitList getHabitList(){
        return habitList;
    }
    public void setHabitList(HabitList habitList) throws UndefinedException {
        if (habitList==null) {
            throw new UndefinedException();
        }
        this.habitList=habitList;
    }
    public FriendList getFollowerList() throws UndefinedException {
        if (followerList==null){
            throw new UndefinedException();
        }
        return followerList;
    }
    public void setFollowerList(FriendList followerList){
        this.followerList=followerList;
    }
    public FriendList getFolloweeList() throws UndefinedException {
        if (followeeList==null) {
            throw new UndefinedException();
        }
        return followeeList;
    }
    public void setFolloweeList(FriendList followeeList){
        this.followeeList=followeeList;
    }
    public FriendList getRequestList() throws UndefinedException {
        if (requestList==null){
            throw new UndefinedException();
        }

        return requestList;
    }
    public void setRequestList(FriendList requestList){
        this.requestList=requestList;
    }

    protected Participant(Parcel in) {
        avatar = (Photograph) in.readValue(Photograph.class.getClassLoader());
        habitList = (HabitList) in.readValue(HabitList.class.getClassLoader());
        followerList = (FriendList) in.readValue(FriendList.class.getClassLoader());
        followeeList = (FriendList) in.readValue(FriendList.class.getClassLoader());
        requestList = (FriendList) in.readValue(FriendList.class.getClassLoader());
        id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(avatar);
        dest.writeValue(habitList);
        dest.writeValue(followerList);
        dest.writeValue(followeeList);
        dest.writeValue(requestList);
        dest.writeString(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Participant> CREATOR = new Parcelable.Creator<Participant>() {
        @Override
        public Participant createFromParcel(Parcel in) {
            return new Participant(in);
        }

        @Override
        public Participant[] newArray(int size) {
            return new Participant[size];
        }
    };
}