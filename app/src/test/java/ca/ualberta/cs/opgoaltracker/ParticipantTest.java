package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by malon_000 on 2017-10-22.
 */

public class ParticipantTest extends ActivityInstrumentationTestCase2 {
    public ParticipantTest(){
        super(ca.ualberta.cs.opgoaltracker.MainActivity.class);
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
        FriendList followerList = new FriendList("follower");
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
    @Test(expected = UndefinedException.class)
    public void getNoFolloweeList() throws UndefinedException {
        Participant participant = new Participant("NoFolloweeList");
        participant.getFolloweeList();
    }
}
