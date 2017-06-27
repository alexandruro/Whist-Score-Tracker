package com.alexandruro.whistscoretracker;

import java.util.ArrayList;

/**
 * Created by Alex on 27/06/2017.
 */

public class PlayerRecord {

    private ArrayList<Integer> bets;
    private ArrayList<Integer> results;
    private int score;

    public PlayerRecord() {
        score = 0;
        bets = new ArrayList<>();
        results = new ArrayList<>();
    }

    public void addBet(int bet) {
        bets.add(bet);
    }

    public void addResult(int result) {
        results.add(result);
        int bet = bets.get(bets.size()-1);
        if(bet==result)
            score += 5+result;
        else
            score -= Math.abs(result-bet);
    }

    public int getScore() {
        return score;
    }
}
