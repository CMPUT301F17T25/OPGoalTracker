package ca.ualberta.cs.opgoaltracker.activity;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.view.Gravity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.HabitList;
import ca.ualberta.cs.opgoaltracker.models.Participant;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by yongjiahuang on 2017-11-12.
 */
public class MenuPageTest {

    @Rule
    public ActivityTestRule<MenuPage> menuPageActivityTestRule = new ActivityTestRule<MenuPage>(MenuPage.class);

    private Participant testUser = new Participant("111");
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void checkNavigationDrawer(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
    }
    @After
    public void tearDown() throws Exception {

    }

}