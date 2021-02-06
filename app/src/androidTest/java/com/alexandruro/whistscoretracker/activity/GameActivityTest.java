package com.alexandruro.whistscoretracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.model.Game;
import com.alexandruro.whistscoretracker.util.RecyclerViewItemCountAssertion;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.not;

public class GameActivityTest  {

    private ActivityScenario<GameActivity> scenario;
    private View decorView;

    @Before
    public void setUp() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        intent.putExtra("type", Game.Type.ONE_EIGHT_ONE);
        intent.putExtra("prize", 0);
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4"));
        intent.putStringArrayListExtra("playerNames", playerNames);

        scenario = ActivityScenario.launch(intent);
    }

    @Test
    public void testNewGame() {
        onView(withText("Name1")).check(matches(isDisplayed()));
        onView(withText("Name2")).check(matches(isDisplayed()));
        onView(withText("Name3")).check(matches(isDisplayed()));
        onView(withText("Name4")).check(matches(isDisplayed()));
    }

    @Test
    public void testRecreate() {
        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(0));

        Intent betIntent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        betIntent.putExtra("inputs", new int[] {1, 0, 0, 1});
        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.BET_REQUEST, Activity.RESULT_OK, betIntent));
        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(1));

        // If a device is low on resources, the system might destroy an activity, requiring your app to recreate that activity when the user returns to your app.
        scenario.recreate();

        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void testUndoNoRounds() {
        onView(withId(R.id.action_undo))
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click());

        scenario.onActivity(activity -> decorView = activity.getWindow().getDecorView());
        onView(withText(R.string.no_rounds))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUndoCancel() {
        Intent betIntent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        betIntent.putExtra("inputs", new int[] {1, 0, 0, 1});
        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.BET_REQUEST, Activity.RESULT_OK, betIntent));

        onView(withId(R.id.action_undo))
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click());

        onView(withText(R.string.undo_prompt)).check(matches(ViewMatchers.isDisplayed()));
        onView(withText(R.string.cancel)).perform(click());

        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void testUndoBet() {
        Intent betIntent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        betIntent.putExtra("inputs", new int[] {1, 0, 0, 1});
        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.BET_REQUEST, Activity.RESULT_OK, betIntent));

        onView(withId(R.id.action_undo))
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click());

        onView(withText(R.string.undo_prompt)).check(matches(ViewMatchers.isDisplayed()));
        onView(withText(R.string.undo)).perform(click());

        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(0));

        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.BET_REQUEST, Activity.RESULT_OK, betIntent));
        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void testUndoResult() {
        Intent betIntent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        betIntent.putExtra("inputs", new int[] {1, 0, 0, 1});
        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.BET_REQUEST, Activity.RESULT_OK, betIntent));
        Intent resultIntent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        resultIntent.putExtra("inputs", new int[] {1, 0, 0, 0});
        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.RESULT_REQUEST, Activity.RESULT_OK, resultIntent));

        onView(withId(R.id.action_undo))
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click());

        onView(withText(R.string.undo_prompt)).check(matches(ViewMatchers.isDisplayed()));
        onView(withText(R.string.undo)).perform(click());

        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(1));

        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.RESULT_REQUEST, Activity.RESULT_OK, resultIntent));
        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void testRestartCancel() throws InterruptedException {
        Intent betIntent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        betIntent.putExtra("inputs", new int[] {1, 0, 0, 1});
        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.BET_REQUEST, Activity.RESULT_OK, betIntent));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText(R.string.restart))
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click());

        onView(withText(R.string.restart_prompt)).check(matches(ViewMatchers.isDisplayed()));
        onView(withText(R.string.cancel)).perform(click());

        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void testRestart() {
        Intent betIntent = new Intent(ApplicationProvider.getApplicationContext(), GameActivity.class);
        betIntent.putExtra("inputs", new int[] {1, 0, 0, 1});
        scenario.onActivity(activity -> activity.onActivityResult(GameActivity.BET_REQUEST, Activity.RESULT_OK, betIntent));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText(R.string.restart))
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click());

        onView(withText(R.string.restart_prompt)).check(matches(ViewMatchers.isDisplayed()));
        onView(withText(R.string.restart)).perform(click());

        onView(withId(R.id.tableBody)).check(new RecyclerViewItemCountAssertion(0));
    }

}