package com.alexandruro.whistscoretracker.config;

public class Constants {

    private Constants() {}

    // Game rules
    public static final int MIN_PLAYERS_COUNT = 3;
    public static final int MAX_PLAYERS_COUNT = 6;


    // Intent extras
    // MainMenuActivity -> GameActivity
    public static final String INTENT_GAME_ID = "gameId";

    // NewGameActivity -> GameActivity
    public static final String INTENT_PRIZE = "prize";
    public static final String INTENT_TYPE = "type";

    // GameActivity -> AddGameInputActivity
    public static final String INTENT_REQUEST_CODE = "requestCode";
    public static final String INTENT_NR_OF_HANDS = "nrOfHands";
    public static final String INTENT_PLAYER_NAMES = "playerNames";
    public static final String INTENT_FIRST_PLAYER_INDEX = "firstPlayerIndex";
    // AddGameInputActivity -> GameActivity
    public static final String INTENT_INPUTS = "inputs";


    // Request codes
    // GameActivity -> AddGameInputActivity
    public static final int RESULT_REQUEST = 1;
    public static final int BET_REQUEST = 2;

}
