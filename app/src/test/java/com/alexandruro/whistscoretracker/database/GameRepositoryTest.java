package com.alexandruro.whistscoretracker.database;

import com.alexandruro.whistscoretracker.model.Game;
import com.google.common.util.concurrent.ListenableFuture;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameRepositoryTest {

    GameRepository gameRepository;
    GameDao gameDao;

    @Before
    public void setUp() {
        gameDao = mock(GameDao.class);
        gameRepository = new GameRepository(gameDao);
    }

    @Test
    public void getGame() {
        ListenableFuture<Game> listenableFuture = mock(ListenableFuture.class);
        when(gameDao.getGame("mockGameId")).thenReturn(listenableFuture);

        assertEquals(listenableFuture, gameRepository.getGame("mockGameId"));
    }
}