package com.alexandruro.whistscoretracker.view;

/**
 * An element in an EditList, represented by its corresponding string input by the user
 */
public class EditListItem {

    private String string;

    public EditListItem() {
        string = "";
    }

    public EditListItem(String string) {
        this.string = string.trim();
    }

    @Override
    public String toString() {
        return string;
    }

    public void setString(String string) {
        this.string = string.trim();
    }
}
