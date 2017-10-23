package ca.ualberta.cs.opgoaltracker;

import java.util.ArrayList;

/**
 * Created by malon_000 on 2017-10-22.
 */

public class FriendList {
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
}
