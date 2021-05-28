package com.alexandruro.whistscoretracker.model;

import com.alexandruro.whistscoretracker.config.Constants;
import com.alexandruro.whistscoretracker.exception.ApplicationBugException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a set of bets or results corresponding to a round
 */
public class GameInput implements Serializable {

    private final List<String> playerNames;
    private final int nrOfHands;
    private final int requestCode;
    private final int firstPlayerIndex;
    private final int nrOfPlayers;
    private final List<PlayerInput> inputs;
    private int index;
    private int handsLeft;

    private boolean done = false;

    /**
     * Create a new GameInput
     * @param playerNames
     * @param nrOfHands
     * @param requestCode
     * @param firstPlayerIndex first player index, starting with 0
     */
    public GameInput(List<String> playerNames, int nrOfHands, int requestCode, int firstPlayerIndex) {
        this.playerNames = playerNames;
        this.nrOfHands = nrOfHands;
        this.requestCode = requestCode;
        this.firstPlayerIndex = firstPlayerIndex;

        this.nrOfPlayers = playerNames.size();
        this.index = 0;
        this.inputs = new ArrayList<>();
        this.inputs.add(new PlayerInput(getNextPlayerName()));
        this.handsLeft = nrOfHands;
    }

    public void addInput(int input) {
        if(done) {
            throw new ApplicationBugException("Tried to add input after getting all the required inputs.");
        }
        if(!isValidInput(input)) {
            throw new ApplicationBugException("Add input called with invalid input.");
        }
        inputs.get(index).setInput(input);
        handsLeft -= input;
        if(index < playerNames.size()-1) {
            index++;
            inputs.add(new PlayerInput(getNextPlayerName()));
        }
        else {
            done = true;
        }
    }

    public void undo() {
        if(index == 0) {
            throw new ApplicationBugException("Tried to undo input with index=0. This should not happen.");
        }

        handsLeft += inputs.get(index-1).getInput();
        inputs.remove(index);
        index--;
        inputs.get(index).unsetInput();
    }

    public int[] getInputsArray() {
        if(!done) {
            throw new ApplicationBugException("Returning the set of inputs before finishing adding them.");
        }
        // Shift inputs array
        int[] results = new int[nrOfPlayers];
        for(int i=0; i<nrOfPlayers; i++) {
            results[i] = inputs.get((i+nrOfPlayers-firstPlayerIndex)%nrOfPlayers).getInput();
        }
        return results;
    }

    public List<PlayerInput> getPlayerInputs() {
        return inputs;
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

    public int getNrOfHands() {
        return nrOfHands;
    }

    public int getInputTotal() {
        return Math.abs(nrOfHands - handsLeft);
    }

    private String getNextPlayerName() {
        return playerNames.get((index+firstPlayerIndex)%nrOfPlayers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInput gameInput = (GameInput) o;
        return nrOfHands == gameInput.nrOfHands &&
                requestCode == gameInput.requestCode &&
                firstPlayerIndex == gameInput.firstPlayerIndex &&
                nrOfPlayers == gameInput.nrOfPlayers &&
                index == gameInput.index &&
                handsLeft == gameInput.handsLeft &&
                done == gameInput.done &&
                playerNames.equals(gameInput.playerNames) &&
                inputs.equals(gameInput.inputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerNames, nrOfHands, requestCode, firstPlayerIndex, nrOfPlayers, inputs, index, handsLeft, done);
    }
}
