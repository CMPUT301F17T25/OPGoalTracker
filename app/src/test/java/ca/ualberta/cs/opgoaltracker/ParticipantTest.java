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
import ca.ualberta.cs.opgoaltracker.exception.ImageTooLargeException;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;
import ca.ualberta.cs.opgoaltracker.models.FriendList;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.Photograph;

/**
 * Created by malon_000 on 2017-10-22.
 */

public class ParticipantTest extends ActivityInstrumentationTestCase2 {
    public ParticipantTest(){
        super(MainActivity.class);
    }
    @Override
    public void setUp() throws Exception{}

    @Override
    public void tearDown() throws Exception {}

    @Override
    public void runTest() throws Exception {}

    @Test
    public void testgetNormalTest() throws UndefinedException {
        Participant participant = new Participant("normal");
        assertEquals(participant.getId(),"normal");
        ArrayList<Participant> followerList = new ArrayList<Participant>();
        participant.setFollowerList(followerList);
        participant.getFollowerList();
    }
    @Test(expected = UndefinedException.class)
    public void testgetNoAvatar() throws UndefinedException{
        Participant participant = new Participant("NoAvatar");
        participant.getAvatar();
    }
    @Test(expected = ImageTooLargeException.class)
    public void testtooLargePicture() throws ImageTooLargeException {
        Participant participant = new Participant("largePicture");
        Photograph avatar = new Photograph(33333,33333);
        participant.setAvatar(avatar);
    }
    @Test
    public void testnormalPicture() throws ImageTooLargeException{
        Participant participant = new Participant("largePicture");
        Photograph avatar = new Photograph(33,33);
        participant.setAvatar(avatar);
    }

    @Test(expected = UndefinedException.class)
    public void getNoFollowerList() throws UndefinedException {
        Participant participant = new Participant("NoFollowerList");
        participant.getFollowerList();
    }

}
