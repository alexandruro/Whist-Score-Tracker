package com.alexandruro.whistscoretracker.model;

import java.util.ArrayList;

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
}