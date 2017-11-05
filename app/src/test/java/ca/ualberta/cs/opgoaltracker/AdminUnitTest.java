package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ca.ualberta.cs.opgoaltracker.activity.MainActivity;
import ca.ualberta.cs.opgoaltracker.models.Admin;

/**
 * Created by song on 2017/10/23.
 */

public class AdminUnitTest extends ActivityInstrumentationTestCase2 {

    public AdminUnitTest(){
        super(MainActivity.class);
    }
    @Override
    public void setUp() throws Exception{}

    @Override
    public void tearDown() throws Exception {}

    @Override
    public void runTest() throws Exception {}

    @Test
    public void testNormalAdminTest(){
        Admin admin = new Admin("admin");
        assertEquals(admin.getCommentLength(),20);
        assertEquals(admin.getPicSize(),65536);
        assertEquals(admin.getReasonLength(),30);
        assertEquals(admin.getTitleLength(),20);

        admin.setCommentLength(30);
        admin.setPicSize(10000);
        admin.setReasonLength(100);
        admin.setTitleLength(50);

        assertEquals(admin.getCommentLength(),30);
        assertEquals(admin.getPicSize(),10000);
        assertEquals(admin.getReasonLength(),100);
        assertEquals(admin.getTitleLength(),50);
    }
}
