package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcelable;

import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;

/**
 * Created by malon_000 on 2017-10-22.
 */

public class Participant extends User  {
    private Photograph avatar;
    private HabitList habitList;
    private FriendList followerList;
    private FriendList followeeList;
    private FriendList requestList;

    public Participant(String id) {
        super(id);
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
}
