package com.alexandruro.whistscoretracker.model;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerInput that = (PlayerInput) o;
        return input == that.input &&
                playerName.equals(that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, input);
    }
}
