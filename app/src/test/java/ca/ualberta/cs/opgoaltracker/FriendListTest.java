/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ca.ualberta.cs.opgoaltracker.activity.MainActivity;
import ca.ualberta.cs.opgoaltracker.exception.OutOfBoundException;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.FriendList;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.Participant;

/**
 * Created by malon_000 on 2017-10-22.
 */

public class FriendListTest extends ActivityInstrumentationTestCase2 {
    public FriendListTest() {
        super(MainActivity.class);
    }
    @Override
    public void setUp() throws Exception{}

    @Override
    public void tearDown() throws Exception {}

    @Override
    public void runTest() throws Exception {}

    @Test
    public void testNothingWrong() throws OutOfBoundException {
        FriendList friendList = new FriendList("normal");
        Participant a = new Participant("test");
        friendList.add(a);
        Participant b = new Participant("test1");
        friendList.add(b);
        Participant c = new Participant("test2");
        friendList.add(c);
        Participant d = friendList.getFriend(1);
        assertEquals(d.getId(),b.getId());
        friendList.delete(1);
        Participant e = friendList.getFriend(1);
        assertEquals(e.getId(),c.getId());
    }
    @Test(expected = OutOfBoundException.class)
    public void testEmpty() throws OutOfBoundException {
        FriendList emptyList = new FriendList("empty");
        emptyList.getFriend(0);
    }

}
