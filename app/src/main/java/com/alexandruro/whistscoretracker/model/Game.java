package com.alexandruro.whistscoretracker.model;

import com.alexandruro.ApplicationBugException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a game state
 */
public class Game {

    public enum Status { WAITING_FOR_BET, WAITING_FOR_RESULT, GAME_OVER }

    public enum Type { ONE_EIGHT_ONE, EIGHT_ONE_EIGHT }

    private List<String> playerNames;
    private List<PlayerRecord> scoreTable;
    private Status gameStatus;
    private int currentRound;
    private int nrOfPlayers;
    private Type type;
    private int prize;

    /**
     * Creates a new game
     * @param playerNames
     * @param type
     * @param prize
     */
    public Game(List<String> playerNames, Type type, int prize) {
        this.playerNames = playerNames;
        this.type = type;
        this.prize = prize;

        initialiseNewGame();
    }

    /**
     * Creates a game
     * @param playerNames
     * @param scoreTable
     * @param gameStatus
     * @param currentRound
     * @param type
     */
    public Game(List<String> playerNames, List<PlayerRecord> scoreTable, Status gameStatus, int currentRound, Type type) {
        this.playerNames = playerNames;
        this.scoreTable = scoreTable;
        this.gameStatus = gameStatus;
        this.currentRound = currentRound;
        this.nrOfPlayers = playerNames.size();
        this.type = type;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public List<PlayerRecord> getScoreTable() {
        return scoreTable;
    }

    public Status getGameStatus() {
        return gameStatus;
    }

    /**
     * Returns the current round number, sarting with 1.
     * I results have been added then the round number was increased.
     * @return The current round number
     */
    public int getCurrentRound() {
        return currentRound;
    }

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

    /**
     * Computes the number of hands in the given round
     * @param roundNumber The round number
     * @return The number of hands
     */
    public int getNrOfHands(int roundNumber) {
        if(roundNumber >3* nrOfPlayers +12)
            return -1;
        if(type == Type.ONE_EIGHT_ONE) {
            if (roundNumber <= nrOfPlayers)
                return 1;
            else if (roundNumber <= nrOfPlayers + 6)
                return roundNumber - nrOfPlayers + 1;
            else if (roundNumber <= 2 * nrOfPlayers + 6)
                return 8;
            else if (roundNumber <= 2 * nrOfPlayers + 12)
                return 2 * nrOfPlayers + 14 - roundNumber;
            else return 1;
        }
        else
        if(roundNumber <= nrOfPlayers)
            return 8;
        else if(roundNumber <= nrOfPlayers +6)
            return nrOfPlayers +8- roundNumber;
        else if(roundNumber <=2* nrOfPlayers +6)
            return 1;
        else if(roundNumber <=2* nrOfPlayers +12)
            return roundNumber -2* nrOfPlayers -5;
        else return 8;
    }

    /**
     * Computes the number of hands in the current round
     * @return The number of hands
     */
    public int getNrOfHands() {
        return getNrOfHands(currentRound);
    }

    /**
     * Return whether the game has started (the first bets have been made) or not
     * @return True if the games has started, false otherwise
     */
    public boolean isStarted() {
        return currentRound > 1 || gameStatus != Status.WAITING_FOR_BET;
    }

    /**
     * Return whether the game has ended or not
     * @return True if the game is over, false otherwise
     */
    public boolean isOver() {
        return currentRound > 3*nrOfPlayers + 12;
    }

    /**
     * Undo the last action (last added bets / results)
     */
    public void undo() {
        if(gameStatus == Status.WAITING_FOR_RESULT) {
            undoBets();
        }
        else {
            undoResults();
        }
    }

    /**
     * Undo the last bets
     */
    private void undoBets() {
        for(int i = 0; i< playerNames.size(); i++) {
            scoreTable.get(i).undoBet();
        }
        gameStatus = Status.WAITING_FOR_BET;
    }

    /**
     * Undo the last results
     */
    private void undoResults() {
        currentRound--;
        for(PlayerRecord playerRecord: scoreTable) {
            playerRecord.undoResult();
        }
        for(int i=0; i<currentRound-1; i++) {
            int nrOfHands = getNrOfHands(i+1);
            for(PlayerRecord playerRecord: scoreTable) {
                playerRecord.recalculateRoundScore(i, nrOfHands == 1);
            }
        }
        gameStatus = Status.WAITING_FOR_RESULT;
    }

    /**
     * Add a set of results
     * @param results
     */
    public void addResults(int[] results) {
        if(results.length != playerNames.size()) {
            throw new ApplicationBugException("addResults() called with incorrect number of results. This should not happen.");
        }
        for(int i = 0; i< playerNames.size(); i++)
            scoreTable.get(i).addResult(results[i], getNrOfHands() == 1);
        currentRound++;

        if(isOver()) {
            gameStatus = Status.GAME_OVER;
            // prepare the leaderboard
            Collections.sort(scoreTable, Collections.reverseOrder());
        }
        else {
            gameStatus = Status.WAITING_FOR_BET;
        }
    }

    /**
     * Add a set of bets
     * @param bets
     */
    public void addBets(int[] bets) {
        if(bets.length != playerNames.size()) {
            throw new ApplicationBugException("addBets() called with incorrect number of bets. This should not happen.");
        }
        for(int i = 0; i< playerNames.size(); i++)
            scoreTable.get(i).addBet(bets[i]);
        gameStatus = Status.WAITING_FOR_RESULT;
    }

    /**
     * Initialise a new game.
     * This can be used at the beginning when setting up the game or when restarting the game.
     */
    public void initialiseNewGame() {
        this.scoreTable = new ArrayList<>();
        for(String name: playerNames)
            scoreTable.add(new PlayerRecord(name, prize));
        this.gameStatus = Status.WAITING_FOR_BET;
        this.currentRound = 1;
        this.nrOfPlayers = playerNames.size();
    }
}