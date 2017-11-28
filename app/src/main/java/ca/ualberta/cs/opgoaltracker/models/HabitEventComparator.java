/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

import java.util.Comparator;

/**
 * Created by malon_000 on 2017-11-26.
 */

public class HabitEventComparator implements Comparator<HabitEvent> {
    @Override
    public int compare(HabitEvent habitEvent, HabitEvent t1) {
        return habitEvent.getDate().compareTo(t1.getDate());
    }
}
