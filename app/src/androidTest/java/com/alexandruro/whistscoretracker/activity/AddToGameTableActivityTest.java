package com.alexandruro.whistscoretracker.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AddToGameTableActivityTest {

    @Test
    public void testBetsNoDelay() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AddToGameTableActivity.class);
        intent.putExtra("nrOfHands", 4);
        intent.putExtra("requestCode", GameActivity.BET_REQUEST);
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("firstPlayerIndex", 0);

        ActivityScenario<AddToGameTableActivity> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> activity.advance(4));
        scenario.onActivity(activity -> activity.advance(3));
        scenario.onActivity(activity -> activity.advance(2));
        scenario.onActivity(activity -> activity.advance(1));

        assertEquals(Activity.RESULT_OK, scenario.getResult().getResultCode());
        assertArrayEquals(new int[]{4,3,2,1}, scenario.getResult().getResultData().getIntArrayExtra("inputs"));
    }

    @Test
    public void testBetsDelay() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AddToGameTableActivity.class);
        intent.putExtra("nrOfHands", 4);
        intent.putExtra("requestCode", GameActivity.BET_REQUEST);
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("firstPlayerIndex", 1);

        ActivityScenario<AddToGameTableActivity> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> activity.advance(4));
        scenario.onActivity(activity -> activity.advance(3));
        scenario.onActivity(activity -> activity.advance(2));
        scenario.onActivity(activity -> activity.advance(1));

        assertEquals(Activity.RESULT_OK, scenario.getResult().getResultCode());
        assertArrayEquals(new int[]{1,4,3,2}, scenario.getResult().getResultData().getIntArrayExtra("inputs"));
    }

    @Test
    public void testResultsNoDelay() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AddToGameTableActivity.class);
        intent.putExtra("nrOfHands", 4);
        intent.putExtra("requestCode", GameActivity.RESULT_REQUEST);
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("firstPlayerIndex", 0);

        ActivityScenario<AddToGameTableActivity> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> activity.advance(3));
        scenario.onActivity(activity -> activity.advance(0));
        scenario.onActivity(activity -> activity.advance(0));
        scenario.onActivity(activity -> activity.advance(1));

        assertEquals(Activity.RESULT_OK, scenario.getResult().getResultCode());
        assertArrayEquals(new int[]{3,0,0,1}, scenario.getResult().getResultData().getIntArrayExtra("inputs"));
    }

    @Test
    public void testResultsDelay() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AddToGameTableActivity.class);
        intent.putExtra("nrOfHands", 4);
        intent.putExtra("requestCode", GameActivity.RESULT_REQUEST);
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("firstPlayerIndex", 2);

        ActivityScenario<AddToGameTableActivity> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> activity.advance(3));
        scenario.onActivity(activity -> activity.advance(0));
        scenario.onActivity(activity -> activity.advance(0));
        scenario.onActivity(activity -> activity.advance(1));

        assertEquals(Activity.RESULT_OK, scenario.getResult().getResultCode());
        assertArrayEquals(new int[]{0,1,3,0}, scenario.getResult().getResultData().getIntArrayExtra("inputs"));
    }

}