package com.alexandruro.whistscoretracker;

import java.util.ArrayList;

/**
 * Keeps a record of a player's bets, results and score
 */
class PlayerRecord {

    private ArrayList<Integer> bets;
    private ArrayList<Integer> results;
    private int score;
    private int prize;
    private int consecResult;
    private boolean consecWin;

    /**
     * Creates a blank record
     * @param prize The prize applied when winning/losing in a row
     */
    PlayerRecord(int prize) {
        this.prize = prize;
        consecResult = 0;
        score = 0;
        bets = new ArrayList<>();
        results = new ArrayList<>();
    }

    /**
     * Adds a new round's bet
     * @param bet The number of hands bet
     */
    void addBet(int bet) {
        bets.add(bet);
    }

    /**
     * Adds current round's result
     * @param result The number of hands gotten
     */
    void addResult(int result) {
        results.add(result);
        int bet = bets.get(bets.size()-1);
        updateScore(bet, result);
    }

    /**
     * Updates the score, given a new pair of bet and result
     * @param bet The bet
     * @param result The result
     */
    private void updateScore(int bet, int result) {
        if(bet==result) {
            score += 5 + result;
            if(consecWin)
                if(++consecResult %5==0)
                    score+=prize;
            if(consecResult==0 || !consecWin) {
                    consecResult = 1;
                    consecWin = true;
                }
        }
        else {
            score -= Math.abs(result - bet);
            if(!consecWin)
                if(++consecResult%5==0)
                    score-=prize;
            if(consecResult==0 || consecWin) {
                    consecResult = 1;
                    consecWin = false;
                }
        }
    }

    /**
     * Gets the score of the player
     * @return The number of points
     */
    int getScore() {
        return score;
    }

    /**
     * Undoes the last result in the record
     */
    void undoResult() {
        results.remove(results.size()-1);
        score = 0;
        consecResult = 0;
        for(int i=0;i<results.size();i++) {
            updateScore(bets.get(i), results.get(i));
        }
    }

    /**
     * Undoes the last bet in the record
     */
    void undoBet() {
        bets.remove(bets.size()-1);
    }
}
