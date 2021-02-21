package com.alexandruro.whistscoretracker.database;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.alexandruro.whistscoretracker.model.Game;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    private GameDao gameDao;
    private AppDatabase database;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        gameDao = database.gameDao();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void writeGameAndRead() throws ExecutionException, InterruptedException {
        Game game = new Game(Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4"), Game.Type.ONE_EIGHT_ONE, 5);
        game.addBets(new int[]{0, 1, 1, 0});
        game.addResults(new int[]{0, 0, 1, 0});

        gameDao.insert(game);

        assertEquals(game, gameDao.getGame(game.getUid()).get());
    }

    @Test
    public void getAllEmpty() {
        Assert.assertEquals(0, gameDao.getAll().size());
    }

    @Test
    public void getAllAndDelete() {
        Game game = new Game(Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4"), Game.Type.ONE_EIGHT_ONE, 5);
        game.addBets(new int[]{0, 1, 1, 0});
        game.addResults(new int[]{0, 0, 1, 0});
        Game game2 = new Game(Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4"), Game.Type.ONE_EIGHT_ONE, 5);

        gameDao.insert(game);
        gameDao.insert(game2);

        assertEquals(Arrays.asList(game, game2), gameDao.getAll());

        gameDao.delete(game);

        assertEquals(Collections.singletonList(game2), gameDao.getAll());
    }

}
