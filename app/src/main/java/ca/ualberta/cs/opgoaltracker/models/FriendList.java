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
 * This Friends List Object is designed for representing follower list and followee list<br>
 *     The Base type of FriendList is an ArrayList, <br>
 *         This ArrayList contains Participant Object
 * @author Ma Long, Yongjia Huang
 * @version 3.0
 * @see Parcelable
 * @see ArrayList
 * @since 1.0
 */
public class FriendList implements Parcelable {
    private ArrayList<Participant> friendList;
    private String listType;

    /**
     * Constructor
     * @param listType Follower List or Followee List
     */
    public FriendList(String listType){
        this.listType=listType;
    }

    /**
     * FriendList Getter
     * @return ArrayList<Participant>
     */
    public ArrayList<Participant> getFriendList(){
        return friendList;
    }

    /**
     * get the selected Friend
     * @param index selected index
     * @return Participant object
     * @throws OutOfBoundException
     */
    public Participant getFriend(int index) throws OutOfBoundException {
        if (index>friendList.size()){
            throw new OutOfBoundException();
        }
        return friendList.get(index);
    }

    /**
     * get the len of the arraylist
     * @return int len
     */
    public int getLen(){
        return friendList.size();
    }

    /**
     * add a Participant object into the friend list
     * @param participant the friend you want to add
     */
    public void add(Participant participant){
        friendList.add(participant);
    }

    /**
     * delete a friend from the list
     * @param index the selected friend index
     * @throws OutOfBoundException
     */
    public void delete(int index) throws OutOfBoundException {
        if (index>=friendList.size()){
            throw new OutOfBoundException();
        }
        friendList.remove(index);
    }


    /**
     * Default Parcel method , implement Parcelable
     * @param in
     * @see Parcelable
     */
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

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param dest
     * @param flags
     */
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

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     */
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