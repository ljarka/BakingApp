package com.github.ljarka.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void shouldDisplayPlayerViewWhenClickOnSpecificStep() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(android.R.id.content)).perform(swipeUp());
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.playerView)).check(matches(isDisplayed()));
    }
}
