package com.alexandruro.whistscoretracker.model;

import com.google.common.base.Objects;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps a record of a player's bets, results and score.
 * Note: this class has a natural ordering that is inconsistent with equals.
 * Ordering is made using the final score.
 */
public class PlayerRecord implements Comparable<PlayerRecord> {

    private final String name;
    private final List<Integer> bets;
    private final List<Integer> results;
    private final List<Integer> scores;
    private final int prize;

    private int winningStreak;
    private int losingStreak;


    /**
     * Creates a blank record
     * @param name The name of the player
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
     * Gets the name of the player
     * @return The name of the player
     */
    public String getName() {
        return name;
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
     * @param roundNumber The round number (starting with 1)
     * @return The amount bet by the player
     */
    public int getBet(int roundNumber) {
        return bets.get(roundNumber-1);
    }

    /**
     * Gets the score of the player in a certain round
     * @param roundNumber The round number (starting with 10
     * @return The number of points
     */
    public int getScore(int roundNumber) {
        return scores.get(roundNumber-1);
    }

    /**
     * Gets the score of the player
     * @return The number of points
     */
    public int getScore() {
        if(scores.isEmpty())
            return 0;
        return scores.get(scores.size()-1);
    }

    /**
     * Undoes the last result in the record.
     * !Important! This call needs to be followed by a series of recalculateRoundScore calls, as it removes the scores.
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
     * @param roundIndex The round index (starting with 1)
     * @param breakStreak True if this round invalidates bonus streaks, false otherwise
     */
    public void recalculateRoundScore(int roundIndex, boolean breakStreak) {
        updateScore(bets.get(roundIndex), results.get(roundIndex), breakStreak);
    }

    @Override
    public int compareTo(@NonNull PlayerRecord o) {
        return getScore() - o.getScore();
    }

    /**
     * Return true if result of the given round was positive, false otherwise
     * @param roundNumber The round number (starting with 1)
     * @return whether the result of the round is positive
     */
    public boolean isPositiveResult(int roundNumber) {
        if(roundNumber == 1) {
            return scores.get(0) > 0;
        }
        return scores.get(roundNumber -1) > scores.get(roundNumber -2);
    }

    /**
     * Return true if the player was awarded a prize (positive or negative) in the given round, false otherwise
     * @param roundNumber The round number (starting with 1)
     * @return whether there was a prize in the round
     */
    public boolean isPrizeRound(int roundNumber) {
        if(roundNumber == 1) {
            return false;
        }
        return (scores.get(roundNumber-1)>scores.get(roundNumber-2)+bets.get(roundNumber-1)+5) ||
                (scores.get(roundNumber-1)<scores.get(roundNumber-2)-Math.abs(bets.get(roundNumber-1)-results.get(roundNumber-1)));

    }

    /**
     * Gets the prize amount (specific to the game)
     * @return the prize
     */
    public int getPrize() {
        return prize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerRecord that = (PlayerRecord) o;
        return prize == that.prize &&
                winningStreak == that.winningStreak &&
                losingStreak == that.losingStreak &&
                name.equals(that.name) &&
                bets.equals(that.bets) &&
                results.equals(that.results) &&
                scores.equals(that.scores);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, bets, results, scores, prize, winningStreak, losingStreak);
    }
}
