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
 * This Participant is a basic object for normal login user<br>
 *     This object contains HabitList, FriendList, as instance<br>
 *         normally pass this object between activities
 * @author Ma Long, Yongjia Huang,Dichong Song
 * @version 4.0
 * @see Parcelable
 * @since 1.0
 */
public class Participant implements Parcelable {
    private Photograph avatar;
    private HabitList habitList;
    private ArrayList<Participant> followerList;
    private ArrayList<Participant> followingList;
    private FriendList requestList;
    private String id;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Basic Constructor for creating a Participant with specific user id.
     * @param id
     */
    public Participant(String id) {
        this.id = id;
        habitList = new HabitList();
        followerList = new ArrayList<Participant>();
        followingList = new ArrayList<Participant>();
        requestList = new FriendList("requestList");
    }


    /**
     * Basic avatar getter
     * @return avatar : Photograph
     * @see Photograph
     */
    public Photograph getAvatar(){
        return avatar;
    }

    /**
     * Basic avatar settter
     * @param avatar : Photograph
     * @throws ImageTooLargeException
     */
    public void setAvatar(Photograph avatar) throws ImageTooLargeException {
        int avatarSize = avatar.getHeight() * avatar.getWidth() * 24 / 8;
        if( avatarSize > 65336){
            throw new ImageTooLargeException();
        }
        this.avatar=avatar;
    }

    /**
     * Basic id getter
     * @return id : String
     */
    public String getId(){
        return id;
    }

    /**
     * Basic HabitList getter
     * @return habitList : HabitList
     * @see HabitList
     */
    public HabitList getHabitList(){
        return habitList;
    }

    /**
     * Basic HabitList setter
     * @param habitList : HabitList
     * @throws UndefinedException
     * @see HabitList
     */
    public void setHabitList(HabitList habitList) throws UndefinedException {
        if (habitList==null) {
            throw new UndefinedException();
        }
        this.habitList=habitList;
    }

    /**
     * Basic follower list getter
     * @return ArrayList<Participant>
     * @throws UndefinedException
     */
    public ArrayList<Participant> getFollowerList() throws UndefinedException {
        if (followerList==null){
            throw new UndefinedException();
        }
        return followerList;
    }

    /**
     * Basic Follower list setter
     * @param followerList : ArrayList<Participant>
     */
    public void setFollowerList(ArrayList<Participant> followerList){
        this.followerList=followerList;
    }

    /**
     * Basic following list getter
     * @return followingList : ArrayList<Participant>
     * @throws UndefinedException
     */
    public ArrayList<Participant> getFollowingList() throws UndefinedException {
        if (followingList==null) {
            throw new UndefinedException();
        }
        return followingList;
    }

    /**
     * Basic followingList setter
     * @param followingList ArrayList<Participant>
     */
    public void setFollowingList(ArrayList<Participant> followingList){
        this.followingList=followingList;
    }

    /**
     * Basic requestList getter
     * @return ArrayList<Participant>
     * @throws UndefinedException
     *  *                    @see FriendList
     */
    public FriendList getRequestList() throws UndefinedException {
        if (requestList==null){
            throw new UndefinedException();
        }
        return requestList;
    }

    /**
     * Basic RequestList setter
     * @param requestList : FriendList
     *                    @see FriendList
     */
    public void setRequestList(FriendList requestList){
        this.requestList=requestList;
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     * @param in
     */
    protected Participant(Parcel in) {
        avatar = (Photograph) in.readValue(Photograph.class.getClassLoader());
        habitList = (HabitList) in.readValue(HabitList.class.getClassLoader());
        followerList = new ArrayList<Participant>();
        followingList = new ArrayList<Participant>();
        in.readList(followerList,Participant.class.getClassLoader());
        in.readList(followingList,Participant.class.getClassLoader());
        requestList = (FriendList) in.readValue(FriendList.class.getClassLoader());
        id = in.readString();
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
        dest.writeValue(avatar);
        dest.writeValue(habitList);
        dest.writeList(followerList);
        dest.writeList(followingList);
        dest.writeValue(requestList);
        dest.writeString(id);
    }

    /**
     * Default Parcel method , implement Parcelable
     * @see Parcelable
     */
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