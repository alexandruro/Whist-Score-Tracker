package com.alexandruro.whistscoretracker.activity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.alexandruro.whistscoretracker.database.AppDatabase;
import com.alexandruro.whistscoretracker.model.Game;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class MainMenuActivityTest {

    @Before
    public void setUp() throws Exception {
//        InstrumentationRegistry.getInstrumentation().getTargetContext().openOrCreateDatabase("main-db", Context.MODE_PRIVATE, null)

        // in memory db?


        Game game = new Game(Arrays.asList("nume1", "nume2", "nume3", "nume4"), Game.Type.ONE_EIGHT_ONE, 5);

//        AppDatabase database = AppDatabase.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AppDatabase database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(), AppDatabase.class).build();
        database.gameDao().insert(game);

        ActivityScenario.launch(MainMenuActivity.class);
    }

    @Test
    public void test() {
        System.out.println("abcd");
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.buttonNewGame), withText("New Game"),
//                        isDisplayed()));
    }
}
