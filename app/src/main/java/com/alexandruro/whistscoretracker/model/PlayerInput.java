package com.alexandruro.whistscoretracker.model;

import java.io.Serializable;

/**
 * Represents an individual input of a player
 */
public class PlayerInput implements Serializable {

    private static final int UNDEFINED = -1;

    private final String playerName;
    private int input;

    public PlayerInput(String playerName) {
        this.playerName = playerName;
        this.input = UNDEFINED;
    }

    public PlayerInput(String playerName, int input) {
        this.playerName = playerName;
        this.input = input;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public void unsetInput() {
        this.input = UNDEFINED;
    }

    public boolean isSet() {
        return input != UNDEFINED;
    }
}
