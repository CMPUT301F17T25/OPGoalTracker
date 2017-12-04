/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.ParticipantName;

/**
 * Created by malon_000 on 2017-11-30.
 */


/**
 * This is a custom class to store the information of a user and his habit event.
 * used in the NewsFragment
 *
 * @author Long Ma
 * @version 3.0
 * @since 1.0
 */
public class NewsUserEventPair {
    String name;
    HabitEvent event;
    Photograph icon;


    /**
     * A class created to facilitate the storing of a user's info and their habitevent for news screen
     * @param name: habitevent's creator's id/name
     * @param event: the habitevent in question
     * @param avatar: habitevent's creator's photo/avatar
     */
    public NewsUserEventPair(String name, HabitEvent event, Photograph avatar){
        this.name = name;
        this.event = event;
        this.icon = avatar;
    }

    /**
     * gets the participant's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the habitevent
     * @return: event
     */
    public HabitEvent getEvent() {
        return event;
    }

    /**
     * get the avatar/photo of the participant
     * @return : icon;
     */
    public Photograph getIcon(){return icon;}
}
