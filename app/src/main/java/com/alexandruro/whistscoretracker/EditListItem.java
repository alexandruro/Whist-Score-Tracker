package com.alexandruro.whistscoretracker;

/**
 * An element in an EditList, represented by its corresponding string input by the user
 */
class EditListItem {

    private String string;

    EditListItem(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    void setString(String string) {
        this.string = string;
    }
}
