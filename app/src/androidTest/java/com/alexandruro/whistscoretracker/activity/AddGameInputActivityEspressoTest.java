package com.alexandruro.whistscoretracker.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddGameInputActivityEspressoTest {

    @Test
    public void testBetsNoDelay() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AddGameInputActivity.class);
        intent.putExtra("nrOfHands", 4);
        intent.putExtra("requestCode", GameActivity.BET_REQUEST);
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("firstPlayerIndex", 0);

        ActivityScenario<AddGameInputActivity> scenario = ActivityScenario.launch(intent);

        for(int i=0; i<=8; i++) {
            if(i <= 4)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("1")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 4)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("2")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 4)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("1")).perform(click());

        onView(withText("0")).check(ViewAssertions.matches(not(isEnabled())));
        onView(withText("1")).check(ViewAssertions.matches(isEnabled()));
        onView(withText("2")).check(ViewAssertions.matches(isEnabled()));
        onView(withText("3")).check(ViewAssertions.matches(isEnabled()));
        onView(withText("4")).check(ViewAssertions.matches(isEnabled()));
        onView(withText("5")).check(ViewAssertions.matches(not(isEnabled())));
        onView(withText("6")).check(ViewAssertions.matches(not(isEnabled())));
        onView(withText("7")).check(ViewAssertions.matches(not(isEnabled())));
        onView(withText("8")).check(ViewAssertions.matches(not(isEnabled())));

        onView(withText("1")).perform(click());

        assertEquals(Activity.RESULT_OK, scenario.getResult().getResultCode());
        assertArrayEquals(new int[]{1,2,1,1}, scenario.getResult().getResultData().getIntArrayExtra("inputs"));
    }

    @Test
    public void testBetsDelay() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AddGameInputActivity.class);
        intent.putExtra("nrOfHands", 4);
        intent.putExtra("requestCode", GameActivity.BET_REQUEST);
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("firstPlayerIndex", 1);

        ActivityScenario<AddGameInputActivity> scenario = ActivityScenario.launch(intent);

        for(int i=0; i<=8; i++) {
            if(i <= 4)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("1")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 4)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("2")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 4)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("1")).perform(click());

        onView(withText("0")).check(ViewAssertions.matches(not(isEnabled())));
        onView(withText("1")).check(ViewAssertions.matches(isEnabled()));
        onView(withText("2")).check(ViewAssertions.matches(isEnabled()));
        onView(withText("3")).check(ViewAssertions.matches(isEnabled()));
        onView(withText("4")).check(ViewAssertions.matches(isEnabled()));
        onView(withText("5")).check(ViewAssertions.matches(not(isEnabled())));
        onView(withText("6")).check(ViewAssertions.matches(not(isEnabled())));
        onView(withText("7")).check(ViewAssertions.matches(not(isEnabled())));
        onView(withText("8")).check(ViewAssertions.matches(not(isEnabled())));

        onView(withText("1")).perform(click());

        assertEquals(Activity.RESULT_OK, scenario.getResult().getResultCode());
        assertArrayEquals(new int[]{1,1,2,1}, scenario.getResult().getResultData().getIntArrayExtra("inputs"));
    }

    @Test
    public void testResultsNoDelay() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AddGameInputActivity.class);
        intent.putExtra("nrOfHands", 4);
        intent.putExtra("requestCode", GameActivity.RESULT_REQUEST);
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("firstPlayerIndex", 0);

        ActivityScenario<AddGameInputActivity> scenario = ActivityScenario.launch(intent);

        for(int i=0; i<=8; i++) {
            if(i <= 4)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("1")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 3)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("2")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 1)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("1")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 0)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("0")).perform(click());

        assertEquals(Activity.RESULT_OK, scenario.getResult().getResultCode());
        assertArrayEquals(new int[]{1,2,1,0}, scenario.getResult().getResultData().getIntArrayExtra("inputs"));
    }

    @Test
    public void testResultsDelay() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AddGameInputActivity.class);
        intent.putExtra("nrOfHands", 4);
        intent.putExtra("requestCode", GameActivity.RESULT_REQUEST);
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("firstPlayerIndex", 3);

        ActivityScenario<AddGameInputActivity> scenario = ActivityScenario.launch(intent);

        for(int i=0; i<=8; i++) {
            if(i <= 4)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("1")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 3)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("2")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 1)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("1")).perform(click());

        for(int i=0; i<=8; i++) {
            if(i <= 0)
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(isEnabled()));
            else
                onView(withText(String.valueOf(i))).check(ViewAssertions.matches(not(isEnabled())));
        }

        onView(withText("0")).perform(click());

        assertEquals(Activity.RESULT_OK, scenario.getResult().getResultCode());
        assertArrayEquals(new int[]{2,1,0,1}, scenario.getResult().getResultData().getIntArrayExtra("inputs"));
    }
}
