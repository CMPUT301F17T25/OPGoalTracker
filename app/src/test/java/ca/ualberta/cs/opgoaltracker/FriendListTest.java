/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.activity.MainActivity;
import ca.ualberta.cs.opgoaltracker.exception.OutOfBoundException;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.FriendList;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.Participant;

/**
 * Created by malon_000 on 2017-10-22.
 * modified by song on 2017/11/13
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
        Participant currentUser = new Participant("test");
        ArrayList<Participant> followingList = new ArrayList<Participant>();

        Participant a = new Participant("test1");
        followingList.add(a);
        Participant b = new Participant("test2");
        followingList.add(b);
        Participant c = new Participant("test3");
        followingList.add(c);

        currentUser.setFollowingList(followingList);

        Participant d = followingList.get(1);
        assertEquals(d.getId(),b.getId());
        followingList.remove(1);
        Participant e = followingList.get(1);
        assertEquals(e.getId(),c.getId());
    }

    @Test(expected = OutOfBoundException.class)
    public void testEmpty() throws OutOfBoundException {
        ArrayList<Participant> followingList = new ArrayList<Participant>();
        followingList.get(0);
    }
}
