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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.ualberta.cs.opgoaltracker.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HabitEventTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void habitEventTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userID), isDisplayed()));
        appCompatEditText.perform(replaceText("111"), closeSoftKeyboard());

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

        ViewInteraction textView = onView(
                allOf(withId(R.id.event_menu_map),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                1),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.event_menu_search),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.new_event),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.relativeLayout_1),
                                        0),
                                1),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction listView = onView(
                allOf(withId(R.id.list_event),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.relativeLayout_1),
                                        0),
                                0),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        ViewInteraction listView2 = onView(
                allOf(withId(R.id.list_event),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.relativeLayout_1),
                                        0),
                                0),
                        isDisplayed()));
        listView2.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.new_event), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.new_event_picture),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView3), withText("Habit:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Habit:")));

        ViewInteraction spinner = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        spinner.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.new_event_comment),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction checkBox = onView(
                allOf(withId(R.id.new_event_location),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        5),
                                1),
                        isDisplayed()));
        checkBox.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.create_event),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.create_event),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.create_event), withText("Create"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.eventDate), withText("Mon Nov 13 14:44:26 MST 2017"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_event),
                                        0),
                                3),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.eventImage),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_event),
                                        0),
                                2),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.eventType), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_event),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("test")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.eventComment),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_event),
                                        0),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.eventComment),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_event),
                                        0),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("")));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.new_event), isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.new_event_comment), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.new_event_comment), isDisplayed()));
        appCompatEditText3.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.new_event_comment), withText("test"), isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.create_event), withText("Create"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.eventComment), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_event),
                                        1),
                                1),
                        isDisplayed()));
        textView8.check(matches(withText("test")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.eventComment), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_event),
                                        1),
                                1),
                        isDisplayed()));
        textView9.check(matches(withText("test")));

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
