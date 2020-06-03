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

    private int winningStreak;
    private int losingStreak;


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

        winningStreak = 0;
        losingStreak = 0;
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
     * @param breakStreak True if this round invalidates bonus streaks, false otherwise
     */
    public void addResult(int result, boolean breakStreak) {
        results.add(result);
        int bet = bets.get(bets.size()-1);
        updateScore(bet, result, breakStreak);
    }

    /**
     * Adds a new score entry, given a new pair of bet and result
     * @param bet The bet
     * @param result The result
     * @param breakStreak True if this round invalidates bonus streaks, false otherwise
     */
    private void updateScore(int bet, int result, boolean breakStreak) {

        int score = 0;
        if(!scores.isEmpty())
            score = scores.get(scores.size()-1);

        if(bet == result) {
            score += bet + 5;
            losingStreak = 0;
            if(breakStreak) {
                winningStreak = 0;
            }
            else {
                winningStreak++;
                if(winningStreak % 5 == 0) {
                    score += prize;
                }
            }
        }
        else {
            score -= Math.abs(bet - result);
            winningStreak = 0;
            if(breakStreak) {
                losingStreak = 0;
            }
            else {
                losingStreak++;
                if(losingStreak % 5 == 0) {
                    score -= prize;
                }
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
     * Undoes the last result in the record.
     * This calls needs to be followed by a series of recalculation calls, as it removes the scores.
     */
    public void undoResult() {
        results.remove(results.size()-1);
        scores.clear();
        winningStreak = 0;
        losingStreak = 0;
    }

    /**
     * Undoes the last bet in the record
     */
    public void undoBet() {
        bets.remove(bets.size()-1);
    }

    /**
     * Recalculates the score in a given round
     * @param roundIndex The round index
     * @param breakStreak True if this round invalidates bonus streaks, false otherwise
     */
    public void recalculateRoundScore(int roundIndex, boolean breakStreak) {
        updateScore(bets.get(roundIndex), results.get(roundIndex), breakStreak);
    }

    @Override
    public int compareTo(@NonNull PlayerRecord o) {
        return o.getScore()-getScore();
    }

    /**
     * Return true if result of the given round was positive, false otherwise
     * @param roundNumber The round number
     * @return whether the result of the round is positive
     */
    public boolean lastResult(int roundNumber) {
        if(roundNumber == 1) {
            return scores.get(0) > 0;
        }
        return scores.get(roundNumber -1) > scores.get(roundNumber -2);
    }
}
