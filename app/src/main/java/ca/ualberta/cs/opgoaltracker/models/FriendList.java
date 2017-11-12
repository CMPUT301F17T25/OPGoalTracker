/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.exception.OutOfBoundException;




/**
 * This Friedns List Object is designed for representing follower list and followee list<br>
 *     The Base type of FriendList is an ArrayList, <br>
 * @author Ma Long, Yongjia Huang
 * @version 3.0
 * @see Parcelable
 * @see ArrayList
 * @since 1.0
 */
public class FriendList implements Parcelable {
    private ArrayList<Participant> friendList;
    private String listType;

    public FriendList(String listType){
        this.listType=listType;
    }
    public ArrayList<Participant> getFriendList(){
        return friendList;
    }
    public Participant getFriend(int index) throws OutOfBoundException {
        if (index>friendList.size()){
            throw new OutOfBoundException();
        }
        return friendList.get(index);
    }
    public int getLen(){
        return friendList.size();
    }
    public void add(Participant participant){
        friendList.add(participant);
    }
    public void delete(int index) throws OutOfBoundException {
        if (index>=friendList.size()){
            throw new OutOfBoundException();
        }
        friendList.remove(index);
    }

    protected FriendList(Parcel in) {
        if (in.readByte() == 0x01) {
            friendList = new ArrayList<Participant>();
            in.readList(friendList, Participant.class.getClassLoader());
        } else {
            friendList = null;
        }
        listType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (friendList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(friendList);
        }
        dest.writeString(listType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FriendList> CREATOR = new Parcelable.Creator<FriendList>() {
        @Override
        public FriendList createFromParcel(Parcel in) {
            return new FriendList(in);
        }

        @Override
        public FriendList[] newArray(int size) {
            return new FriendList[size];
        }
    };
}