package ca.ualberta.cs.opgoaltracker;

import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Date;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEventUnitTest extends ActivityInstrumentationTestCase2 {

    public HabitEventUnitTest(){
        super(ca.ualberta.cs.opgoaltracker.MainActivity.class);
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
        HabitEvent habitEvent = new HabitEvent(habitType, comment, date);
    }

    public void testNormalComment() throws CommentTooLongException{
        String habitType = "";
        String comment = "hello";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitType, comment, date);
        assertEquals(habitEvent.getComment(), comment);
    }

    @Test(expected = ImageTooLargeException.class)
    public void testTooLargeImage() throws CommentTooLongException, ImageTooLargeException{
        String habitType = "";
        String comment = "";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitType, comment, date);
        Photograph photo = new Photograph(12345, 67890);
        habitEvent.setPhoto(photo);
    }

    public void testNormalImage() throws CommentTooLongException, ImageTooLargeException{
        String habitType = "";
        String comment = "";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitType, comment, date);
        Photograph photo = new Photograph(123, 67);
        habitEvent.setPhoto(photo);
        assertEquals(habitEvent.getPhoto(), photo);
    }
}
