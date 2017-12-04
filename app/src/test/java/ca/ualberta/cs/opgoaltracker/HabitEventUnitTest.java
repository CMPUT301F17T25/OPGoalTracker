/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Date;

import ca.ualberta.cs.opgoaltracker.activity.MainActivity;
import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.Photograph;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEventUnitTest extends ActivityInstrumentationTestCase2 {

    public HabitEventUnitTest(){
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception{

    }

    @Override
    public void tearDown() throws Exception {

    }

    @Override
    public void runTest() throws Exception {

    }

    @Test(expected = CommentTooLongException.class)
    public void testTooLongComment() throws CommentTooLongException{
        String habitType = "";
        String comment = "hellooooooooooooimmmmmmmmmmmmmmmmmmdoneeeeeeeeeeeeeeeeeeeeeeeeeeee";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitType, comment, date, 20);
    }

    public void testNormalComment() throws CommentTooLongException{
        String habitType = "";
        String comment = "hello";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitType, comment, date, 20);
        assertEquals(habitEvent.getComment(), comment);
    }

    @Test(expected = ImageTooLargeException.class)
    public void testTooLargeImage() throws CommentTooLongException, ImageTooLargeException{
        String habitType = "";
        String comment = "";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitType, comment, date, 20);
        Photograph photo = new Photograph("res/drawable/newevent.png", 65535);
        habitEvent.setPhoto(photo);
    }

    public void testNormalImage() throws CommentTooLongException, ImageTooLargeException{
        String habitType = "";
        String comment = "";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitType, comment, date, 20);
        Photograph photo = new Photograph("res/drawable/newevent.png", 65535);
        habitEvent.setPhoto(photo);
        assertEquals(habitEvent.getPhoto(), photo);
    }
}
