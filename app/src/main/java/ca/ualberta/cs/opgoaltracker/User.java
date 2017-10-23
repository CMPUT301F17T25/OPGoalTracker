package ca.ualberta.cs.opgoaltracker;

/**
 * Created by malon_000 on 2017-10-22.
 */

public abstract class User {
    private String id;

    public User(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

}
