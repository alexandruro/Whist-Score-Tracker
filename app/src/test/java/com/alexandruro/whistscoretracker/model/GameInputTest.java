package com.alexandruro.whistscoretracker.model;

import com.alexandruro.whistscoretracker.config.Constants;
import com.alexandruro.whistscoretracker.exception.ApplicationBugException;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GameInputTest {

    List<String> playerNames;

    @Before
    public void setUp() {
        playerNames = Arrays.asList("One", "Two", "Three");
    }

    @Test
    public void constructor() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.BET_REQUEST, 1);

        assertEquals(0, gameInput.getIndex());
        assertEquals(0, gameInput.getInputTotal());
        assertEquals(4, gameInput.getNrOfHands());
        assertFalse(gameInput.isDone());

        for(int i=0; i<=4; i++) {
            assertTrue(gameInput.isValidInput(i));
        }
        for(int i=5; i<=8; i++) {
            assertFalse(gameInput.isValidInput(i));
        }

        List<PlayerInput> expectedPlayerInputs = Collections.singletonList(new PlayerInput("Two"));
        assertEquals(expectedPlayerInputs, gameInput.getPlayerInputs());
    }

    @Test(expected = ApplicationBugException.class)
    public void addInput() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.BET_REQUEST, 0);
        gameInput.addInput(1);
        gameInput.addInput(3);
        gameInput.addInput(3);
        gameInput.addInput(2);
    }

    @Test(expected = ApplicationBugException.class)
    public void addInput2() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.BET_REQUEST, 0);
        gameInput.addInput(1);
        gameInput.addInput(0);
        gameInput.addInput(3);
    }

    @Test(expected = ApplicationBugException.class)
    public void undo() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.BET_REQUEST, 0);

        gameInput.undo();
    }

    @Test
    public void undo2() {
        GameInput gameInput1 = new GameInput(playerNames, 4, Constants.BET_REQUEST, 1);
        GameInput gameInput2 = new GameInput(playerNames, 4, Constants.BET_REQUEST, 1);

        gameInput1.addInput(1);
        gameInput1.undo();

        assertEquals(gameInput2, gameInput1);
    }

    @Test
    public void undo3() {
        GameInput gameInput1 = new GameInput(playerNames, 4, Constants.RESULT_REQUEST, 1);
        GameInput gameInput2 = new GameInput(playerNames, 4, Constants.RESULT_REQUEST, 1);

        gameInput1.addInput(3);
        gameInput1.addInput(0);
        gameInput1.undo();
        gameInput1.undo();

        assertEquals(gameInput2, gameInput1);
    }

    @Test(expected = ApplicationBugException.class)
    public void getInputsArray() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.BET_REQUEST, 1);

        gameInput.getInputsArray();
    }

    @Test
    public void getInputsArray2() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.BET_REQUEST, 2);
        gameInput.addInput(1);
        gameInput.addInput(2);
        gameInput.addInput(3);

        int[] expectedArray = {2,3,1};
        assertArrayEquals(expectedArray, gameInput.getInputsArray());
    }

    @Test
    public void isValidInput() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.BET_REQUEST, 2);
        gameInput.addInput(1);

        for(int i=0; i<=4; i++) {
            assertTrue(gameInput.isValidInput(i));
        }
        for(int i=5; i<=8; i++) {
            assertFalse(gameInput.isValidInput(i));
        }
    }

    @Test
    public void isValidInput2() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.BET_REQUEST, 2);
        gameInput.addInput(1);
        gameInput.addInput(0);

        for(int i=0; i<=2; i++) {
            assertTrue(gameInput.isValidInput(i));
        }
        assertFalse(gameInput.isValidInput(3));
        assertTrue(gameInput.isValidInput(4));
        for(int i=5; i<=8; i++) {
            assertFalse(gameInput.isValidInput(i));
        }
    }

    @Test
    public void isValidInput3() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.RESULT_REQUEST, 2);
        gameInput.addInput(1);
        gameInput.addInput(0);

        for(int i=0; i<=2; i++) {
            assertFalse(gameInput.isValidInput(i));
        }
        assertTrue(gameInput.isValidInput(3));
        for(int i=4; i<=8; i++) {
            assertFalse(gameInput.isValidInput(i));
        }
    }

    @Test
    public void isDone() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.RESULT_REQUEST, 2);

        assertFalse(gameInput.isDone());
    }

    @Test
    public void isDone2() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.RESULT_REQUEST, 2);
        gameInput.addInput(3);
        gameInput.addInput(0);

        assertFalse(gameInput.isDone());
    }

    @Test
    public void isDone3() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.RESULT_REQUEST, 2);
        gameInput.addInput(3);
        gameInput.addInput(0);
        gameInput.addInput(1);

        assertTrue(gameInput.isDone());
    }

    @Test
    public void getInputTotal() {
        GameInput gameInput = new GameInput(playerNames, 4, Constants.RESULT_REQUEST, 2);
        gameInput.addInput(2);
        gameInput.addInput(1);

        assertEquals(3, gameInput.getInputTotal());
    }
}