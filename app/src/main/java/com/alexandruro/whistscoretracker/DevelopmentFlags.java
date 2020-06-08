package com.alexandruro.whistscoretracker;

public abstract class DevelopmentFlags {

    private DevelopmentFlags() {
        throw new IllegalStateException("Utility class");
    }

    // Add some player names to the new game form
    public static final boolean PREPOPULATE_PLAYER_NAMES = true;

    // Add some results to the game table when starting a new game
    public static final boolean PREPOPULATE_GAME_TABLE = true;
}
