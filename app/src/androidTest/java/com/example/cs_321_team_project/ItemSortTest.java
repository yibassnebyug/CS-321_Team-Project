package com.example.cs_321_team_project;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ItemSortTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void itemSortTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floatingActionButton3), withContentDescription("add item button"),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.genreSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.statusSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.addTextBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("C"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.addButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.floatingActionButton3), withContentDescription("add item button"),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.genreSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView3.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.statusSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner4.perform(click());

        DataInteraction appCompatCheckedTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView4.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.addTextBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("B"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.floatingActionButton3), withContentDescription("add item button"),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.addTextBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("A"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.genreSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner5.perform(click());

        DataInteraction appCompatCheckedTextView5 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatCheckedTextView5.perform(click());

        ViewInteraction appCompatSpinner6 = onView(
                allOf(withId(R.id.statusSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner6.perform(click());

        DataInteraction appCompatCheckedTextView6 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(4);
        appCompatCheckedTextView6.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.addButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.nameTitle), withText("Name"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Name")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.genreTitle), withText("Genre"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView2.check(matches(withText("Genre")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.statusTitle), withText("Status"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView3.check(matches(withText("Status")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.nameTitle), withText("Name"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.nameTitle), withText("Name ?"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView4.check(matches(withText("Name ?")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.displayName), withText("A"),
                        withParent(allOf(withId(R.id.displayLayout),
                                withParent(withId(R.id.mediaList)))),
                        isDisplayed()));
        textView5.check(matches(withText("A")));

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.nameTitle), withText("Name ?"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.nameTitle), withText("Name ?"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView6.check(matches(withText("Name ?")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.displayName), withText("C"),
                        withParent(allOf(withId(R.id.displayLayout),
                                withParent(withId(R.id.mediaList)))),
                        isDisplayed()));
        textView7.check(matches(withText("C")));

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.genreTitle), withText("Genre"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.nameTitle), withText("Name"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView8.check(matches(withText("Name")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.genreTitle), withText("Genre ?"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView9.check(matches(withText("Genre ?")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.displayGenre), withText("Book"),
                        withParent(allOf(withId(R.id.displayLayout),
                                withParent(withId(R.id.mediaList)))),
                        isDisplayed()));
        textView10.check(matches(withText("Book")));

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.genreTitle), withText("Genre ?"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.displayGenre), withText("Video Game"),
                        withParent(allOf(withId(R.id.displayLayout),
                                withParent(withId(R.id.mediaList)))),
                        isDisplayed()));
        textView11.check(matches(withText("Video Game")));

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.statusTitle), withText("Status"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                4),
                        isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.genreTitle), withText("Genre"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView12.check(matches(withText("Genre")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.statusTitle), withText("Status ?"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView13.check(matches(withText("Status ?")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.displayStatus), withText("Dropped"),
                        withParent(allOf(withId(R.id.displayLayout),
                                withParent(withId(R.id.mediaList)))),
                        isDisplayed()));
        textView14.check(matches(withText("Dropped")));

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.statusTitle), withText("Status ?"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                4),
                        isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.displayStatus), withText("Ongoing"),
                        withParent(allOf(withId(R.id.displayLayout),
                                withParent(withId(R.id.mediaList)))),
                        isDisplayed()));
        textView15.check(matches(withText("Ongoing")));

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(R.id.nameTitle), withText("Name"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatTextView7.perform(click());

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.statusTitle), withText("Status"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView16.check(matches(withText("Status")));
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
