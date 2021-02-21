package com.alexandruro.whistscoretracker.database;

import com.alexandruro.whistscoretracker.model.Game;
import com.alexandruro.whistscoretracker.model.PlayerRecord;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ConvertersTest {

    @Test
    public void playerRecordsFromString() {
        PlayerRecord playerRecord1 = new PlayerRecord("mockName1", 5);
        playerRecord1.addBet(0);
        playerRecord1.addResult(1, false);
        playerRecord1.addBet(1);
        PlayerRecord playerRecord2 = new PlayerRecord("mockName2", 0);
        playerRecord2.addBet(1);
        playerRecord2.addResult(1, true);
        List<PlayerRecord> playerRecords = new ArrayList<>(Arrays.asList(playerRecord1, playerRecord2));

        assertEquals(playerRecords, Converters.playerRecordsFromString(Converters.fromPlayerRecords(playerRecords)));
    }

    @Test
    public void gameTypeFromString() {
        assertEquals(Game.Type.EIGHT_ONE_EIGHT, Converters.gameTypeFromString(Converters.fromGameType(Game.Type.EIGHT_ONE_EIGHT)));
        assertEquals(Game.Type.ONE_EIGHT_ONE, Converters.gameTypeFromString(Converters.fromGameType(Game.Type.ONE_EIGHT_ONE)));
    }

    @Test
    public void gameStatusFromString() {
        assertEquals(Game.Status.WAITING_FOR_RESULT, Converters.gameStatusFromString(Converters.fromGameStatus(Game.Status.WAITING_FOR_RESULT)));
        assertEquals(Game.Status.WAITING_FOR_BET, Converters.gameStatusFromString(Converters.fromGameStatus(Game.Status.WAITING_FOR_BET)));
        assertEquals(Game.Status.GAME_OVER, Converters.gameStatusFromString(Converters.fromGameStatus(Game.Status.GAME_OVER)));
    }
}