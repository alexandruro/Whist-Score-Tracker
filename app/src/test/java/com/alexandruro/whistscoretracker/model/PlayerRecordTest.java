package com.alexandruro.whistscoretracker.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerRecordTest {

    @Test
    public void testGame() {

        PlayerRecord record = new PlayerRecord("mockName", 5);

        // basic
        addRound(record, 0, 1, false);
        assertEquals(-1, record.getScore());

        addRound(record, 2, 2, false);
        assertEquals(6, record.getScore());

        addRound(record, 2, 0, false);
        assertEquals(2, record.getBet(2));
        assertEquals(4, record.getScore());

        addRound(record, 0, 5, false);
        assertEquals(-1, record.getScore());

        addRound(record, 0, 1, false);
        assertEquals(-2, record.getScore());

        // positive bonus / promotion series
        addRound(record, 1, 1, false);
        assertEquals(4, record.getScore());

        addRound(record, 0, 0, false);
        assertEquals(9, record.getScore());

        addRound(record, 0, 0, false);
        assertEquals(14, record.getScore());

        addRound(record, 5, 5, false);
        assertEquals(24, record.getScore());

        addRound(record, 2, 2, false);
        assertEquals(36, record.getScore());

        // positive bonus / promotion series
        addRound(record, 2, 2, false);
        assertEquals(43, record.getScore());

        addRound(record, 0, 0, false);
        assertEquals(48, record.getScore());

        addRound(record, 2, 2, false);
        assertEquals(55, record.getScore());

        addRound(record, 1, 1, false);
        assertEquals(61, record.getScore());

        addRound(record, 3, 3, false);
        assertEquals(74, record.getScore());

        // negative bonus / promotion series
        addRound(record, 2, 0, false);
        assertEquals(72, record.getScore());

        addRound(record, 0, 2, false);
        assertEquals(70, record.getScore());

        addRound(record, 2, 5, false);
        assertEquals(67, record.getScore());

        addRound(record, 1, 8, false);
        assertEquals(60, record.getScore());

        addRound(record, 3, 2, false);
        assertEquals(54, record.getScore());

        // negative bonus / promotion series
        addRound(record, 7, 8, false);
        assertEquals(53, record.getScore());

        addRound(record, 0, 5, false);
        assertEquals(48, record.getScore());

        addRound(record, 2, 4, false);
        assertEquals(46, record.getScore());

        addRound(record, 1, 0, false);
        assertEquals(45, record.getScore());

        addRound(record, 3, 7, false);
        assertEquals(36, record.getScore());
    }

    @Test
    public void testGameBreakStreak() {

        PlayerRecord record = new PlayerRecord("mockName", 5);

        addRound(record, 0, 0, true);
        assertEquals(5, record.getScore());

        // positive bonus / promotion series
        addRound(record, 2, 2, false);
        assertEquals(12, record.getScore());

        addRound(record, 1, 1, false);
        assertEquals(18, record.getScore());

        addRound(record, 0, 0, false);
        assertEquals(23, record.getScore());

        addRound(record, 2, 2, false);
        assertEquals(30, record.getScore());

        addRound(record, 0, 0, false);
        assertEquals(40, record.getScore());

        // positive bonus / promotion series to be broken
        addRound(record, 0, 0, false);
        assertEquals(45, record.getScore());

        addRound(record, 0, 0, false);
        assertEquals(50, record.getScore());

        addRound(record, 5, 5, false);
        assertEquals(60, record.getScore());

        addRound(record, 2, 2, false);
        assertEquals(67, record.getScore());

        addRound(record, 2, 2, true);
        assertEquals(74, record.getScore());

        // negative bonus / promotion series to be broken
        addRound(record, 0, 1, false);
        assertEquals(73, record.getScore());

        addRound(record, 2, 1, false);
        assertEquals(72, record.getScore());

        addRound(record, 1, 4, true);
        assertEquals(69, record.getScore());

        // negative bonus / promotion series
        addRound(record, 3, 6, false);
        assertEquals(66, record.getScore());

        addRound(record, 2, 0, false);
        assertEquals(64, record.getScore());

        addRound(record, 0, 2, false);
        assertEquals(62, record.getScore());

        addRound(record, 2, 5, false);
        assertEquals(59, record.getScore());

        addRound(record, 1, 8, false);
        assertEquals(47, record.getScore());

        //
        addRound(record, 3, 3, false);
        assertEquals(55, record.getScore());
    }

    /**
     * Test the beginning of a game of 4 players
     */
    @Test
    public void testTrueGame() {
        PlayerRecord record = new PlayerRecord("mockName", 5);

        addRound(record, 0, 0, true);
        assertEquals(5, record.getScore());

        addRound(record, 1, 1, true);
        assertEquals(11, record.getScore());

        addRound(record, 1, 1, true);
        assertEquals(17, record.getScore());

        addRound(record, 0, 0, true);
        assertEquals(22, record.getScore());

        addRound(record, 1, 1, false);
        assertEquals(28, record.getScore());

        addRound(record, 3, 3, false);
        assertEquals(36, record.getScore());

        addRound(record, 2, 2, false);
        assertEquals(43, record.getScore());

        addRound(record, 0, 0, false);
        assertEquals(48, record.getScore());

        addRound(record, 1, 1, false);
        assertEquals(59, record.getScore());
    }

    /**
     * Helper function to add a round to the player record
     */
    private static void addRound(PlayerRecord record, int bet, int result, boolean breakStreak) {
        record.addBet(bet);
        record.addResult(result, breakStreak);
    }

}