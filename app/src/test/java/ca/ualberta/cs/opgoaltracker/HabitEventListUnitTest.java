package ca.ualberta.cs.opgoaltracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.activity.MainActivity;
import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.exception.MismatchedHabitTypeException;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.HabitEventList;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEventListUnitTest extends ActivityInstrumentationTestCase2 {

    public HabitEventListUnitTest(){
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

    @Test(expected = MismatchedHabitTypeException.class)
    public void testAddHabitEventWithMismatchedType() throws CommentTooLongException, MismatchedHabitTypeException{
        String habitTypeA = "typeA";
        String comment = "hello";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitTypeA, comment, date);

        String habitTypeB = "typeB";
        HabitEventList habitEventList = new HabitEventList(habitTypeB);
        habitEventList.addHabitEvent(habitEvent);
    }

    public void testAddHabitEventWithMatchedType() throws CommentTooLongException, MismatchedHabitTypeException{
        String habitTypeA = "typeA";
        String comment = "hello";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitTypeA, comment, date);

        HabitEventList habitEventList = new HabitEventList(habitTypeA);
        habitEventList.addHabitEvent(habitEvent);
        assertEquals(habitEventList.getaHabitEvent(0), habitEvent);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteNonExistentHabitEvent() throws CommentTooLongException, MismatchedHabitTypeException, IndexOutOfBoundsException{
        String habitTypeB = "typeB";
        HabitEventList habitEventList = new HabitEventList(habitTypeB);
        habitEventList.deleteHabitEvent(0);
    }

    public void testSearchHabitEventWithMatchedKeyword() throws CommentTooLongException, MismatchedHabitTypeException{
        String habitTypeA = "typeA";
        String keyword = "hello";
        String comment = "hello world";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitTypeA, comment, date);

        HabitEventList habitEventList = new HabitEventList(habitTypeA);
        habitEventList.addHabitEvent(habitEvent);

        ArrayList<HabitEvent> habitEventWithKeywordList = habitEventList.search(keyword);
        assertEquals(habitEventWithKeywordList.get(0), habitEvent);
    }

    public void testSearchHabitEventWithMismatchedKeyword() throws CommentTooLongException, MismatchedHabitTypeException{
        String habitTypeA = "typeA";
        String keyword = "nihao";
        String comment = "hello world";
        Date date = new java.util.Date();
        HabitEvent habitEvent = new HabitEvent(habitTypeA, comment, date);

        HabitEventList habitEventList = new HabitEventList(habitTypeA);
        habitEventList.addHabitEvent(habitEvent);

        ArrayList<HabitEvent> habitEventWithKeywordList = habitEventList.search(keyword);
        assertTrue(habitEventWithKeywordList.isEmpty());
    }

}
