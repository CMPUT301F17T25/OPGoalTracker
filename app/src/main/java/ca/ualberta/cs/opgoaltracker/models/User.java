/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

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
