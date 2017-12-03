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

public class NewsUserEventPair {
    String name;
    HabitEvent event;
    Photograph icon;

    public NewsUserEventPair(String name, HabitEvent event, Photograph avatar){
        this.name = name;
        this.event = event;
        this.icon = avatar;
    }

    public String getName() {
        return name;
    }

    public HabitEvent getEvent() {
        return event;
    }

    public Photograph getIcon(){return icon;}
}
