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
 * This Participant is a basic object for normal login user<br>
 *     This object contains HabitList, FriendList, as instance<br>
 *         normally pass this object between activities
 * @author Ma Long, Yongjia Huang
 * @version 3.0
 * @see Parcelable
 * @since 1.0
 */
public class Participant implements Parcelable {
    private Photograph avatar;
    private HabitList habitList;
    private FriendList followerList;
    private FriendList followeeList;
    private FriendList requestList;
    private String id;

    /**
     * Basic Constructor for creating a Participant with specific user id.
     * @param id
     */
    public Participant(String id) {
        this.id = id;
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
     * @return FriendList
     * @throws UndefinedException
     * @see FriendList
     */
    public FriendList getFollowerList() throws UndefinedException {
        if (followerList==null){
            throw new UndefinedException();
        }
        return followerList;
    }

    /**
     * Basic Follower list setter
     * @param followerList : FriendList
     * @see FriendList
     */
    public void setFollowerList(FriendList followerList){
        this.followerList=followerList;
    }

    /**
     * Basic followee list getter
     * @return followee list : FriendList
     * @throws UndefinedException
     * @see FriendList
     */
    public FriendList getFolloweeList() throws UndefinedException {
        if (followeeList==null) {
            throw new UndefinedException();
        }
        return followeeList;
    }

    /**
     * Basic followee list setter
     * @param followeeList FriendList
     *                     @see FriendList
     */
    public void setFolloweeList(FriendList followeeList){
        this.followeeList=followeeList;
    }

    /**
     * Basic requestList getter
     * @return FriendList
     * @throws UndefinedException
     * @see FriendList
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
        followerList = (FriendList) in.readValue(FriendList.class.getClassLoader());
        followeeList = (FriendList) in.readValue(FriendList.class.getClassLoader());
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
        dest.writeValue(followerList);
        dest.writeValue(followeeList);
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