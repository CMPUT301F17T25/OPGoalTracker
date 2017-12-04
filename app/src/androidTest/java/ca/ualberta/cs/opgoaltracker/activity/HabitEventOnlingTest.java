/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.ualberta.cs.opgoaltracker.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


// Cannot handles giving permission
// location permission must be given
@LargeTest
@RunWith(AndroidJUnit4.class)
public class HabitEventOnlingTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void habitEventOnlingTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userID), isDisplayed()));
        appCompatEditText.perform(replaceText("test1"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signin), withText("Sign In"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Habit Event"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.new_event), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.new_event_comment), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.new_event_comment), isDisplayed()));
        appCompatEditText3.perform(replaceText("comment"), closeSoftKeyboard());


        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.create_event), withText("Create"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.eventComment), withText("comment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_event),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("comment")));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.new_event), isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.new_event_comment), isDisplayed()));
        appCompatEditText4.perform(replaceText("test text location"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.address), isDisplayed()));
        appCompatEditText5.perform(replaceText("University of alberta"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.create_event), withText("Create"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.new_event), isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.new_event_comment), isDisplayed()));
        appCompatEditText6.perform(replaceText("test gps"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.create_event), withText("Create"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.new_event), isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.new_event_comment), isDisplayed()));
        appCompatEditText7.perform(replaceText("test gps location"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.gps_checkbox), withText("May require permission"), isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.create_event), withText("Create"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.create_event), withText("Create"), isDisplayed()));
        appCompatButton6.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
