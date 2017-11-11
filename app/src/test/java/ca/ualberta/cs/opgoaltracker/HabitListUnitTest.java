/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.activity.MainActivity;
import ca.ualberta.cs.opgoaltracker.exception.DuplicatedHabitException;
import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.HabitList;

/**
 * Created by song on 2017/10/23.
 */

public class HabitListUnitTest extends ActivityInstrumentationTestCase2 {

    public HabitListUnitTest(){
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception{}

    @Override
    public void tearDown() throws Exception {}

    @Override
    public void runTest() throws Exception {}

    public void testNormalAddDeleteHabit() throws StringTooLongException,NoTitleException,DuplicatedHabitException {
        String habitType = "run";
        String reason = "i like it";
        Date date = new java.util.Date();
        Calendar calendar= Calendar.getInstance();
        long startTime = calendar.getTimeInMillis();
        long intervalTime = 36000;
        Habit habit = new Habit(habitType, reason, date, startTime, intervalTime);

        HabitList habitList = new HabitList();
        habitList.addHabit(habit);
        assertEquals(habitList.getHabit(0),habit);
        habitList.deleteHabit(0);
        assertEquals(habitList.getHabit(0),null);
    }

    @Test(expected = DuplicatedHabitException.class)
    public void testAddHabitWithDuplicates() throws StringTooLongException,NoTitleException,DuplicatedHabitException{
        String habitType = "run";
        String reason = "i like it";
        Date date = new java.util.Date();
        Calendar calendar= Calendar.getInstance();
        long startTime = calendar.getTimeInMillis();
        long intervalTime = 36000;
        Habit habit = new Habit(habitType, reason, date, startTime, intervalTime);

        HabitList habitList = new HabitList();
        habitList.addHabit(habit);
        habitList.addHabit(habit);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteNonExistentHabitEvent() throws IndexOutOfBoundsException{
        String habitTypeB = "typeB";
        HabitList habitList = new HabitList();
        habitList.deleteHabit(0);
    }

    @Test(expected = StringTooLongException.class)
    public void testAddWrongHabit() throws StringTooLongException,NoTitleException,DuplicatedHabitException{
        String habitType = "111111111111111111111111111111";
        String reason = "i like it";
        Date date = new java.util.Date();
        Calendar calendar= Calendar.getInstance();
        long startTime = calendar.getTimeInMillis();
        long intervalTime = 36000;
        Habit habit = new Habit(habitType, reason, date, startTime, intervalTime);

        HabitList habitList = new HabitList();
        habitList.addHabit(habit);
    }
}
