package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivityTest  {

    @Test
    public void testEvent() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        intent.putExtra("gameType1", true);
        intent.putExtra("prize", 0);
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));
        intent.putStringArrayListExtra("playerNames", playerNames);

        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(intent);

//        Game game = new Game(playerNames, true, 0);
//
//        scenario.onActivity(activity -> activity.)
//
//        assertEquals(game, )


        // add data
        scenario.recreate();
        // check data still there (and same for all scenarios)

        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }

    @Test
    public void testEvent2() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        intent.putExtra("gameType1", true);
        intent.putExtra("prize", 0);
        ArrayList<String> stringNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));
        intent.putStringArrayListExtra("playerNames", stringNames);

        ActivityScenario scenario = ActivityScenario.launch(intent);

        scenario.recreate();

        // TODO: understand this..

        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.recreate();

        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.recreate();

        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }


}