package com.alexandruro.whistscoretracker.model;

import com.alexandruro.whistscoretracker.exception.ApplicationBugException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameTest {

    @Test(expected = ApplicationBugException.class)
    public void undoGameNotStarted() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        Game game = new Game(playerNames, Game.Type.EIGHT_ONE_EIGHT, 5);

        game.undo();
    }

    @Test
    public void undo() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);
        game.addBets(new int[]{1, 1, 1});

        game.undo();

        assertEquals(new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10), game);
        assertFalse(game.isStarted());
    }

    @Test
    public void undo2() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game1 = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);
        Game game2 = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);

        for(int i=0; i<=19; i++) {
            game1.addBets(new int[]{0,0,0});
            game2.addBets(new int[]{0,0,0});
            int[] results = new int[]{0,0,0};
            results[0] = game1.getNrOfHands();
            game1.addResults(results);
            game2.addResults(results);
        }
        game1.addBets(new int[]{1,1,1});
        game2.addBets(new int[]{1,1,1});
        int[] results = new int[]{0,0,0};
        results[1] = game1.getNrOfHands();
        game1.addResults(results);

        game1.undo();

        assertEquals(game2, game1);
        assertFalse(game1.isOver());
    }

    @Test
    public void addResults() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);
        game.addBets(new int[]{1,1});

        PlayerRecord playerRecord1 = mock(PlayerRecord.class);
        PlayerRecord playerRecord2 = mock(PlayerRecord.class);
        game.getScoreTable().set(0, playerRecord1);
        game.getScoreTable().set(1, playerRecord2);

        game.addResults(new int[]{0,1});

        assertEquals(2, game.getCurrentRound());
        assertEquals(Game.Status.WAITING_FOR_BET, game.getGameStatus());
        verify(playerRecord1).addResult(0, true);
        verify(playerRecord2).addResult(1, true);
    }

    @Test
    public void addBets() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);

        PlayerRecord playerRecord1 = mock(PlayerRecord.class);
        PlayerRecord playerRecord2 = mock(PlayerRecord.class);
        game.getScoreTable().set(0, playerRecord1);
        game.getScoreTable().set(1, playerRecord2);

        game.addBets(new int[]{1,1});

        assertEquals(1, game.getCurrentRound());
        assertEquals(Game.Status.WAITING_FOR_RESULT, game.getGameStatus());
        verify(playerRecord1).addBet(1);
        verify(playerRecord2).addBet(1);
    }

    @Test
    public void initialiseNewGameWithConstructor() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        Game game = new Game(playerNames, Game.Type.EIGHT_ONE_EIGHT, 5);

        assertEquals(playerNames, game.getPlayerNames());
        assertEquals(4, game.getScoreTable().size());
        assertEquals("mockUser1", game.getScoreTable().get(0).getName());
        assertEquals(0, game.getScoreTable().get(0).getScore());
        assertEquals(Game.Status.WAITING_FOR_BET, game.getGameStatus());
        assertEquals(1, game.getCurrentRound());
    }

    @Test
    public void initialiseNewGameWithMethod() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        List<PlayerRecord> scoreTable = new ArrayList<>();
        for(String name: playerNames) {
            PlayerRecord playerRecord = new PlayerRecord(name, 5);
            playerRecord.addBet(1);
            playerRecord.addResult(1, false);
            scoreTable.add(playerRecord);
        }
        Game game = new Game("mockId", scoreTable, Game.Status.GAME_OVER, 15, Game.Type.EIGHT_ONE_EIGHT);

        game.initialiseNewGame();

        assertEquals(playerNames, game.getPlayerNames());
        assertEquals(4, game.getScoreTable().size());
        assertEquals("mockUser1", game.getScoreTable().get(0).getName());
        assertEquals(0, game.getScoreTable().get(0).getScore());
        assertEquals(Game.Status.WAITING_FOR_BET, game.getGameStatus());
        assertEquals(1, game.getCurrentRound());
        assertEquals(5, game.getScoreTable().get(0).getPrize());
    }

    @Test
    public void getNrOfHands() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);
        assertEquals(1, game.getNrOfHands());
        assertEquals(1, game.getNrOfHands(1));
        assertEquals(2, game.getNrOfHands(4));
        assertEquals(8, game.getNrOfHands(10));
        assertEquals(7, game.getNrOfHands(13));
        assertEquals(1, game.getNrOfHands(19));
        assertEquals(1, game.getNrOfHands(21));
    }

    @Test
    public void getNrOfHands2() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3", "mockUser4");
        Game game = new Game(playerNames, Game.Type.EIGHT_ONE_EIGHT, 5);
        assertEquals(8, game.getNrOfHands());
        assertEquals(8, game.getNrOfHands(1));
        assertEquals(7, game.getNrOfHands(5));
        assertEquals(1, game.getNrOfHands(11));
        assertEquals(2, game.getNrOfHands(15));
        assertEquals(8, game.getNrOfHands(21));
        assertEquals(8, game.getNrOfHands(24));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNrOfHandsOutOfBounds() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);
        game.getNrOfHands(22);
    }

    @Test
    public void isStarted() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);

        assertFalse(game.isStarted());
    }

    @Test
    public void isStarted2() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);
        game.addBets(new int[]{1, 1, 1});

        assertTrue(game.isStarted());
    }

    @Test
    public void isStarted3() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);
        game.addBets(new int[]{1, 1, 1});
        game.addResults(new int[]{1, 0, 0});

        assertTrue(game.isStarted());
    }

    @Test
    public void isOver() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);

        assertFalse(game.isOver());
    }

    @Test
    public void isOver2() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);
        game.addBets(new int[]{1, 1, 1});
        game.addResults(new int[]{1, 0, 0});

        assertFalse(game.isOver());
    }

    @Test
    public void isOver3() {
        Game game = createFinishedGame();

        assertEquals(Game.Status.GAME_OVER, game.getGameStatus());
        assertTrue(game.isOver());
    }

    private static Game createFinishedGame() {
        List<String> playerNames = Arrays.asList("mockUser1", "mockUser2", "mockUser3");
        Game game = new Game(playerNames, Game.Type.ONE_EIGHT_ONE, 10);

        for(int i=0; i<=20; i++) {
            game.addBets(new int[]{0,0,0});
            int[] results = new int[]{0,0,0};
            results[0] = game.getNrOfHands();
            game.addResults(results);
        }
        return game;
    }
}