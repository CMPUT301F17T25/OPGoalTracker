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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HabitTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void habitTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userID), isDisplayed()));
        appCompatEditText.perform(replaceText("111"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signin), withText("Sign In"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_habit), isDisplayed()));
        floatingActionButton.perform(click());

        // pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.buttonCreate), withText("Create"),
                        withParent(allOf(withId(R.id.constraintLayout),
                                withParent(withId(R.id.habitAddScrollView))))));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTitleAdd),
                        withParent(allOf(withId(R.id.constraintLayout),
                                withParent(withId(R.id.habitAddScrollView))))));
        appCompatEditText2.perform(scrollTo(), replaceText("Test title"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editReasonAdd),
                        withParent(allOf(withId(R.id.constraintLayout),
                                withParent(withId(R.id.habitAddScrollView))))));
        appCompatEditText3.perform(scrollTo(), replaceText("Test reason"), closeSoftKeyboard());

        // pressBack();

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.checkBoxMonAdd),
                        withParent(allOf(withId(R.id.constraintLayout),
                                withParent(withId(R.id.habitAddScrollView))))));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.checkBoxTueAdd),
                        withParent(allOf(withId(R.id.constraintLayout),
                                withParent(withId(R.id.habitAddScrollView))))));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.buttonCreate), withText("Create"),
                        withParent(allOf(withId(R.id.constraintLayout),
                                withParent(withId(R.id.habitAddScrollView))))));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_title), withText("Test title"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_habit),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Test title")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_reason), withText("Test reason"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_habit),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Test reason")));

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.list_habit),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTitleDetail), withText("Test title"),
                        withParent(withId(R.id.constraintLayout))));
        appCompatEditText4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editTitleDetail),
                        withParent(withId(R.id.constraintLayout))));
        appCompatEditText5.perform(scrollTo(), replaceText("Test title 1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editReasonDetail),
                        withParent(withId(R.id.constraintLayout))));
        appCompatEditText6.perform(scrollTo(), replaceText("Test reason 2"), closeSoftKeyboard());

        // pressBack();

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.checkBoxMonDetail),
                        withParent(withId(R.id.constraintLayout))));
        appCompatCheckBox3.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.checkBoxTueDetail),
                        withParent(withId(R.id.constraintLayout))));
        appCompatCheckBox4.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox5 = onView(
                allOf(withId(R.id.checkBoxSatDetail),
                        withParent(withId(R.id.constraintLayout))));
        appCompatCheckBox5.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox6 = onView(
                allOf(withId(R.id.checkBoxSunDetail),
                        withParent(withId(R.id.constraintLayout))));
        appCompatCheckBox6.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.buttonSave), withText("Save"),
                        withParent(withId(R.id.constraintLayout))));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tv_title), withText("Test title 1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_habit),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Test title 1")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.tv_reason), withText("Test reason 2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_habit),
                                        0),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("Test reason 2")));

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        withId(R.id.list_habit),
                        0),
                        isDisplayed()));
        linearLayout2.perform(click());

        // pressBack();

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.habit_detail_delete), withContentDescription("Delete"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.tv_title), withText("Test title"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_habit),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(doesNotExist());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.tv_reason), withText("Test reason"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_habit),
                                        0),
                                1),
                        isDisplayed()));
        textView6.check(doesNotExist());

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
