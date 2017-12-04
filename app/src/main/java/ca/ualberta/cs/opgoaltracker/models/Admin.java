/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

/**
 * This Admin Class is a subclass of User<br>
 *     This Class allows Admin user to set the restrictions to this app
 * @author Dichong Song, Yongjia Huang
 * @version 3.0
 * @see User
 * @since 1.0
 */
public class Admin{
    private String id;

    /**
     * Create Admin object with inputted id
     * @param id
     */
    public Admin(String id) {
        this.id = id;
    }

    /**
     * Basic getter for id
     * @return
     */
    public String getId() {
        return id;
    }

}
