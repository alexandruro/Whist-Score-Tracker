package com.alexandruro.whistscoretracker.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Keeps a record of a player's bets, results and score
 */
public class PlayerRecord implements Comparable<PlayerRecord> {

    private String name;
    private ArrayList<Integer> bets;
    private ArrayList<Integer> results;
    private ArrayList<Integer> scores;
    private int prize;
    private int consecResult;
    private boolean consecWin;

    /**
     * Gets the name of the player
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a blank record
     * @param prize The prize applied when winning/losing in a row
     */
    public PlayerRecord(String name, int prize) {
        this.name = name;
        this.prize = prize;

        consecResult = 0;

        bets = new ArrayList<>();
        results = new ArrayList<>();
        scores = new ArrayList<>();
    }

    /**
     * Adds a new round's bet
     * @param bet The number of hands bet
     */
    public void addBet(int bet) {
        bets.add(bet);
    }

    /**
     * Adds current round's result
     * @param result The number of hands gotten
     */
    public void addResult(int result) {
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
        int score = 0;
        if(!scores.isEmpty())
            score = scores.get(scores.size()-1);
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
        scores.add(score);
    }

    /**
     * Gets the bet in a certain round
     * @param round The round
     * @return The amount bet by the player
     */
    public int getBet(int round) {
        return bets.get(round-1);
    }

    /**
     * Gets the score of the player in a certain round
     * @param round The round
     * @return The number of points
     */
    public int getScore(int round) {
        return scores.get(round-1);
    }

    /**
     * Gets the score of the player
     * @return The number of points
     */
    public int getScore() {
        return scores.get(scores.size()-1);
    }

    /**
     * Undoes the last result in the record
     */
    public void undoResult() {
        results.remove(results.size()-1);
        scores.clear();
        consecResult = 0;
        for(int i=0;i<results.size();i++) {
            updateScore(bets.get(i), results.get(i));
        }
    }

    /**
     * Undoes the last bet in the record
     */
    public void undoBet() {
        bets.remove(bets.size()-1);
    }

    @Override
    public int compareTo(@NonNull PlayerRecord o) {
        return o.getScore()-getScore();
    }

    /**
     * Checks whether the last result was positive or not
     * @return True, if the last result was positive, false if not
     */
    public boolean lastResult() {
        return consecWin;
    }

    public boolean lastResult(int round) {
        if(round == 1) {
            return scores.get(0) > 0;
        }
        return scores.get(round-1) > scores.get(round-2);
    }
}
