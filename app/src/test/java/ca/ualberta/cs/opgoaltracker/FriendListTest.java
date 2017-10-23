package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by malon_000 on 2017-10-22.
 */

public class FriendListTest extends ActivityInstrumentationTestCase2 {
    public FriendListTest() {
        super(ca.ualberta.cs.opgoaltracker.MainActivity.class);
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
        User a = new Admin("test");
        friendList.add(a);
        User b = new Admin("test1");
        friendList.add(b);
        User c = new Admin("test2");
        friendList.add(c);
        User d = friendList.getFriend(1);
        assertEquals(d.getId(),b.getId());
        friendList.delete(1);
        User e = friendList.getFriend(1);
        assertEquals(e.getId(),c.getId());
    }
    @Test(expected = OutOfBoundException.class)
    public void testEmpty() throws OutOfBoundException {
        FriendList emptyList = new FriendList("empty");
        emptyList.getFriend(0);
    }

}
