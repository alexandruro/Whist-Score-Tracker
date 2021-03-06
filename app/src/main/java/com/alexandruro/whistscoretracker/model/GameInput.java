package com.alexandruro.whistscoretracker.model;


import com.alexandruro.whistscoretracker.config.Constants;
import com.alexandruro.whistscoretracker.exception.ApplicationBugException;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a set of bets or results corresponding to a round
 */
public class GameInput implements Serializable {

    private final List<String> playerNames;
    private final int nrOfHands;
    private final int requestCode;
    private final int firstPlayerIndex;
    private final int nrOfPlayers;
    private int[] inputs;
    private int index;
    private int handsLeft;

    private boolean done = false;

    /**
     * Create a new GameInput
     * @param playerNames
     * @param nrOfHands
     * @param requestCode
     * @param firstPlayerIndex
     */
    public GameInput(List<String> playerNames, int nrOfHands, int requestCode, int firstPlayerIndex) {
        this.playerNames = playerNames;
        this.nrOfHands = nrOfHands;
        this.requestCode = requestCode;
        this.firstPlayerIndex = firstPlayerIndex;

        this.inputs = new int[playerNames.size()];
        this.index = 0;
        this.nrOfPlayers = playerNames.size();
        this.handsLeft = nrOfHands;
    }

    public void addInput(int input) {
        inputs[index] = input;
        handsLeft -= input;
        if(index < playerNames.size()-1) {
            index++;
        }
        else {
            done = true;
        }
    }

    public void undo() {
        if(index == 0) {
            throw new ApplicationBugException("Tried to undo input with index=0. This should not happen.");
        }

        index--;
        handsLeft += inputs[index];
    }

    public int[] getInputs() {
        if(!done) {
            throw new ApplicationBugException("Returning the set of inputs before finishing adding them.");
        }
        // Shift inputs array
        int[] results = new int[nrOfPlayers];
        for(int i=0; i<nrOfPlayers; i++) {
            results[i] = inputs[(i+nrOfPlayers-firstPlayerIndex)%nrOfPlayers];
        }
        return results;
    }

    /**
     * Check if a particular number of hands is a valid input for the next player.
     * This can be used to enable/disable buttons.
     * @param input The number of hands
     * @return Whether the input is valid
     */
    public boolean isValidInput(int input) {
        boolean invalidBet = requestCode == Constants.BET_REQUEST && input == handsLeft && index == playerNames.size()-1;
        boolean invalidResult = requestCode == Constants.RESULT_REQUEST && ((input != handsLeft && index == playerNames.size()-1) || input>handsLeft);
        return input<=nrOfHands && !invalidBet && !invalidResult;
    }

    public String getNextPlayerName() {
        return playerNames.get((index+firstPlayerIndex)%nrOfPlayers);
    }

    public boolean isDone() {
        return done;
    }

    /**
     * Get the current player index (starting with 0)
     * @return The index
     */
    public int getIndex() {
        return index;
    }
}
