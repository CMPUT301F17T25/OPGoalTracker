/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import junit.extensions.ActiveTestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;
import ca.ualberta.cs.opgoaltracker.models.FriendList;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.HabitList;
import ca.ualberta.cs.opgoaltracker.models.Participant;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

/**
 * Created by yongjiahuang on 2017-11-12.
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private Participant testUser = new Participant("111");
    private ArrayList<Habit> testHabitList;
    private Habit testHabit;
    private int numItems;


    @Before
    public void setUp() throws Exception {
        long startTime;
        long intervalTime = 36000;
        testHabitList = testUser.getHabitList();
        Date today = new Date();
        ArrayList<Boolean> period = new ArrayList<>();
        testHabit = new Habit("Testing Habit", "Just for testing.", today,period);
        testHabitList.add(testHabit);
        numItems = testHabitList.size();


    }
    @Test
    public void testLoginScenario(){
        Espresso.onView(withId(R.id.userID)).perform(typeText(testUser.getId()));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.signin)).perform(click());


    }

    @After
    public void tearDown() throws Exception {

    }

//    @Test
//    public void isAdminExistent() throws Exception {
//
//    }

}