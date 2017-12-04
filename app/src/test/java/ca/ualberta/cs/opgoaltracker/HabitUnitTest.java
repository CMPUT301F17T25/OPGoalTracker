/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.activity.MainActivity;
import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;
import ca.ualberta.cs.opgoaltracker.models.Habit;

/**
 * Created by song on 2017/10/23.
 */

public class HabitUnitTest  extends ActivityInstrumentationTestCase2 {
    public HabitUnitTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {}

    @Override
    public void tearDown() throws Exception {}

    @Override
    public void runTest() throws Exception {}

    public void testNormalHabit() throws StringTooLongException,NoTitleException {
        String habitType = "run";
        String reason = "i like it";
        Date today = new Date();
        ArrayList<Boolean> period = new ArrayList<>();
        Habit habit = new Habit(habitType,reason, today,period, 20, 30);

        assertEquals(habit.getHabitType(), habitType);
        assertEquals(habit.getReason(), reason);

        habitType = "swim";
        habit.setHabitType(habitType, 20);
        assertEquals(habit.getHabitType(), habitType);

        reason = "no reason";
        habit.setReason(reason, 20);
        assertEquals(habit.getReason(), reason);
    }

    @Test(expected = NoTitleException.class)
    public void testNoTitle() throws StringTooLongException,NoTitleException {
        String habitType = "";
        String reason = "i like it";
        Date date = new java.util.Date();
        Date today = new Date();
        ArrayList<Boolean> period = new ArrayList<>();
        Habit habit = new Habit(habitType,reason, today,period, 20, 30);
    }

    @Test(expected = NoTitleException.class)
    public void testSetNoTitle() throws StringTooLongException,NoTitleException {
        String habitType = "run";
        String reason = "i like it";
        Date date = new java.util.Date();
        Date today = new Date();
        ArrayList<Boolean> period = new ArrayList<>();
        Habit habit = new Habit(habitType,reason, today,period, 20, 30);

        habitType="";
        habit.setHabitType(habitType, 20);
    }

    @Test(expected = StringTooLongException.class)
    public void testTooLongTitle() throws StringTooLongException,NoTitleException {
        String habitType = "111111111111111111111111111111";
        String reason = "i like it";
        Date today = new Date();
        ArrayList<Boolean> period = new ArrayList<>();
        Habit habit = new Habit(habitType,reason, today,period, 20, 30);
    }

    @Test(expected = StringTooLongException.class)
    public void testTooLongReason() throws StringTooLongException,NoTitleException {
        String habitType = "run";
        String reason =  "11111111111111111111111111111111111111111111111111111";
        Date today = new Date();
        ArrayList<Boolean> period = new ArrayList<>();
        Habit habit = new Habit(habitType,reason, today,period, 20, 30);
    }

    @Test(expected = StringTooLongException.class)
    public void testSetLongReason() throws StringTooLongException,NoTitleException {
        String habitType = "runrun";
        String reason = "i like it";
        Date today = new Date();
        ArrayList<Boolean> period = new ArrayList<>();
        Habit habit = new Habit(habitType,reason, today,period, 20, 30);

        reason = "111111111111111111111111111111";
        habit.setReason(reason, 30);
    }
}
