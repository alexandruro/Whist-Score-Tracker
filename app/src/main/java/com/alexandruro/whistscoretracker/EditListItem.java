package com.alexandruro.whistscoretracker;

/**
 * Created by Alex on 16/06/2017.
 */

public class EditListItem {

    private String string;

    public EditListItem(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
