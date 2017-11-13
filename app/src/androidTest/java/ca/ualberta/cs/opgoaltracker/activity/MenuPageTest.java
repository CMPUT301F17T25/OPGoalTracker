package ca.ualberta.cs.opgoaltracker.activity;


import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Participant;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by yongjiahuang on 2017-11-12.
 */
public class MenuPageTest {

    @Rule
    public ActivityTestRule<MenuPage> menuPageActivityTestRule = new ActivityTestRule<MenuPage>(MenuPage.class);

    private Participant testUser = new Participant("111");
    private String title ;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkNavigationDrawerOpen(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));


    }
    @Test
    public void checkHabitonClickSuccess(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.habit));
        title = (String) menuPageActivityTestRule.getActivity().getTitle();
        assertEquals(title,"Habit");
    }

    @Test
    public void checkHabitEventonClickSuccess(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.habitEvent));
        title = (String) menuPageActivityTestRule.getActivity().getTitle();
        assertEquals(title,"Habit Event");
    }

    @Test
    public void checkFriendonClickSuccess(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.friends));
        title = (String) menuPageActivityTestRule.getActivity().getTitle();
        assertEquals(title,"My Friends");
    }

    @Test
    public void checkNewsonClickSuccess(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.social));
        title = (String) menuPageActivityTestRule.getActivity().getTitle();
        assertEquals(title,"News");
    }

    public void checkSettingonClickSuccess(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.setting));
        title = (String) menuPageActivityTestRule.getActivity().getTitle();
        assertEquals(title,"My Account");
    }



    @Test
    public void testNavigationDrawerBackButton() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        pressBack();
    }



    @After
    public void tearDown() throws Exception {

    }

}