package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.exception.OutOfBoundException;

/**
 * Created by malon_000 on 2017-10-22.
 */

public class FriendList implements Parcelable {
    private ArrayList<User> friendList;
    private String listType;

    public FriendList(String listType){
        this.listType=listType;
    }
    public ArrayList<User> getFriendList(){
        return friendList;
    }
    public User getFriend(int index) throws OutOfBoundException {
        if (index>friendList.size()){
            throw new OutOfBoundException();
        }
        return friendList.get(index);
    }
    public int getLen(){
        return friendList.size();
    }
    public void add(User user){
        friendList.add(user);
    }
    public void delete(int index) throws OutOfBoundException {
        if (index>=friendList.size()){
            throw new OutOfBoundException();
        }
        friendList.remove(index);
    }

    protected FriendList(Parcel in) {
        if (in.readByte() == 0x01) {
            friendList = new ArrayList<User>();
            in.readList(friendList, User.class.getClassLoader());
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