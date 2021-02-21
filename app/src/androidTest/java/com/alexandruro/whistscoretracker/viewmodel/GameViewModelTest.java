package com.alexandruro.whistscoretracker.viewmodel;

import com.alexandruro.whistscoretracker.database.GameRepository;
import com.alexandruro.whistscoretracker.model.Game;
import com.google.common.util.concurrent.ListenableFuture;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void initialiseNewGame() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        Game expectedGame = new Game(playerNames, Game.Type.EIGHT_ONE_EIGHT, 5);
        SavedStateHandle savedStateHandle = new SavedStateHandle();
        savedStateHandle.set("playerNames", playerNames);
        savedStateHandle.set("prize", 5);
        savedStateHandle.set("type", Game.Type.EIGHT_ONE_EIGHT);

        GameViewModel viewModel = new GameViewModel(savedStateHandle, null);
        Game actualGame = viewModel.getGame().getValue();

        assertEquals(expectedGame, actualGame);
        assertEquals(actualGame.getUid(), savedStateHandle.get("gameId"));
    }

    @Test
    public void instantiateExistingGame() throws ExecutionException, InterruptedException {
        SavedStateHandle savedStateHandle = new SavedStateHandle();
        savedStateHandle.set("gameId", "mockGameId");
        GameRepository gameRepository = mock(GameRepository.class);
        Game game = mock(Game.class);
        ListenableFuture<Game> listenableFuture = mock(ListenableFuture.class);
        when(listenableFuture.get()).thenReturn(game);
        when(gameRepository.getGame("mockGameId")).thenReturn(listenableFuture);

        GameViewModel viewModel = new GameViewModel(savedStateHandle, gameRepository);

        assertEquals(game, viewModel.getGame().getValue());
    }

    @Test
    public void getGameSameGame() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        SavedStateHandle savedStateHandle = new SavedStateHandle();
        savedStateHandle.set("playerNames", playerNames);
        savedStateHandle.set("prize", 5);
        savedStateHandle.set("type", Game.Type.EIGHT_ONE_EIGHT);
        GameViewModel viewModel = new GameViewModel(savedStateHandle, null);

        Game game1 = viewModel.getGame().getValue();
        Game game2 = viewModel.getGame().getValue();

        assertSame(game1, game2);
    }

    @Test
    public void persistGame() {
        GameRepository gameRepository = mock(GameRepository.class);
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        SavedStateHandle savedStateHandle = new SavedStateHandle();
        savedStateHandle.set("playerNames", playerNames);
        savedStateHandle.set("prize", 5);
        savedStateHandle.set("type", Game.Type.EIGHT_ONE_EIGHT);
        GameViewModel viewModel = new GameViewModel(savedStateHandle, gameRepository);

        viewModel.persistGame();

        verify(gameRepository, times(1)).insert(viewModel.getGame().getValue());
    }
}