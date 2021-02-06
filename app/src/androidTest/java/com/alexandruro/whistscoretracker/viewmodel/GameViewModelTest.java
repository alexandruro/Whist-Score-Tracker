package com.alexandruro.whistscoretracker.viewmodel;

import com.alexandruro.whistscoretracker.model.Game;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.Arrays;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class GameViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    GameViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new GameViewModel();
    }

    @Test
    public void initialiseNewGame() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        Game expectedGame = new Game(playerNames, Game.Type.EIGHT_ONE_EIGHT, 5);

        viewModel.initialiseNewGame(playerNames, Game.Type.EIGHT_ONE_EIGHT, 5);
        Game actualGame = viewModel.getGame().getValue();

        assertEquals(expectedGame, actualGame);
    }

    @Test
    public void getGameNull() {
        assertNull(viewModel.getGame().getValue());
    }

    @Test
    public void getGameSameGame() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        viewModel.initialiseNewGame(playerNames, Game.Type.EIGHT_ONE_EIGHT, 5);

        Game game1 = viewModel.getGame().getValue();
        Game game2 = viewModel.getGame().getValue();

        assertSame(game1, game2);
    }
}