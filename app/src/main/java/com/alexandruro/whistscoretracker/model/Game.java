package com.alexandruro.whistscoretracker.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a game state
 */
public class Game {

    // game status
    public static class Status {
        public static final int WAITING_FOR_BET = 0;
        public static final int WAITING_FOR_RESULT = 1;
        public static final int GAME_OVER = 2;
    }

    private ArrayList<String> playerNames;
    private ArrayList<PlayerRecord> scoreTable;
    private int gameStatus;
    private int currentRound; // starting with 1. if current round ended then it's the next one
    private int nrOfPlayers;
    private boolean gameType1;

    public Game(ArrayList<String> playerNames, boolean gameType1, int prize) {
        this.playerNames = playerNames;
        this.scoreTable = new ArrayList<>();
        for(String name: playerNames)
            scoreTable.add(new PlayerRecord(name, prize));
        this.gameStatus = Status.WAITING_FOR_BET;
        this.currentRound = 1;
        this.nrOfPlayers = playerNames.size();
        this.gameType1 = gameType1;
    }

    public Game(ArrayList<String> playerNames, ArrayList<PlayerRecord> scoreTable, int gameStatus, int currentRound, boolean gameType1, int prize) {
        this.playerNames = playerNames;
        this.scoreTable = scoreTable;
        this.gameStatus = gameStatus;
        this.currentRound = currentRound;
        this.nrOfPlayers = playerNames.size();
        this.gameType1 = gameType1;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public ArrayList<PlayerRecord> getScoreTable() {
        return scoreTable;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

    public boolean isGameType1() {
        return gameType1;
    }

    /**
     * Computes the number of hands in the current round
     * @return The number of hands
     */
    public int getNrOfHands(int round) {
        if(round >3* nrOfPlayers +12)
            return -1;
        if(isGameType1())
            if(round <= nrOfPlayers)
                return 1;
            else if(round <= nrOfPlayers +6)
                return round - nrOfPlayers +1;
            else if(round <=2* nrOfPlayers +6)
                return 8;
            else if(round <=2* nrOfPlayers +12)
                return 2* nrOfPlayers +14- round;
            else return 1;
        else
        if(round <= nrOfPlayers)
            return 8;
        else if(round <= nrOfPlayers +6)
            return nrOfPlayers +8- round;
        else if(round <=2* nrOfPlayers +6)
            return 1;
        else if(round <=2* nrOfPlayers +12)
            return round -2* nrOfPlayers -5;
        else return 8;
    }

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

    public void undo() {
        if(gameStatus == Status.WAITING_FOR_RESULT) {
            undoBets();
        }
        else {
            undoResults();
        }
    }

    private void undoBets() {
        for(int i = 0; i< playerNames.size(); i++) {
            scoreTable.get(i).undoBet();
        }
        gameStatus = Status.WAITING_FOR_BET;
    }

    private void undoResults() {
        currentRound--;
        for(PlayerRecord playerRecord: scoreTable) {
            playerRecord.undoResult();
        }
        for(int i=0; i<currentRound-1; i++) {
            int nrOfHands = getNrOfHands(i);
            for(PlayerRecord playerRecord: scoreTable) {
                playerRecord.recalculateRoundScore(i, nrOfHands == 1);
            }
        }
        gameStatus = Status.WAITING_FOR_RESULT;
    }

    public void addResults(int[] results) {
        if(results.length != playerNames.size()) {
            Log.e("Game", "addResults() called with incorrect number of results. This should not happen.");
            throw new RuntimeException();
        }
        for(int i = 0; i< playerNames.size(); i++)
            scoreTable.get(i).addResult(results[i], getNrOfHands() == 1);
        currentRound++;

        if(isOver()) {
            gameStatus = Status.GAME_OVER;
            // prepare the leaderboard
            Collections.sort(scoreTable);
        }
        else {
            gameStatus = Status.WAITING_FOR_BET;
        }
    }

    public void addBets(int[] bets) {
        if(bets.length != playerNames.size()) {
            Log.e("Game", "addBets() called with incorrect number of bets. This should not happen.");
            throw new RuntimeException();
        }
        for(int i = 0; i< playerNames.size(); i++)
            scoreTable.get(i).addBet(bets[i]);
        gameStatus = Status.WAITING_FOR_RESULT;
    }
}