package com.alexandruro.whistscoretracker;

import java.util.ArrayList;

/**
 * Keeps a record of a player's bets, results and score
 */
class PlayerRecord {

    private ArrayList<Integer> bets;
    private ArrayList<Integer> results;
    private int score;

    PlayerRecord() {
        score = 0;
        bets = new ArrayList<>();
        results = new ArrayList<>();
    }

    void addBet(int bet) {
        bets.add(bet);
    }

    void addResult(int result) {
        results.add(result);
        int bet = bets.get(bets.size()-1);
        if(bet==result)
            score += 5+result;
        else
            score -= Math.abs(result-bet);
    }

    int getScore() {
        return score;
    }
}
