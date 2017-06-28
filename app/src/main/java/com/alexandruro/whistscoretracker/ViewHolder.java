package com.alexandruro.whistscoretracker;

import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Holds information for a View about an EditText in the list of names.
 * Tracks the EditText itself and its corresponding TextWatcher.
 */
class ViewHolder {
    private EditText editText;
    private TextWatcher textWatcher;

    EditText getEditText() {
        return editText;
    }

    void setEditText(EditText editText) {
        this.editText = editText;
    }

    TextWatcher getTextWatcher() {
        return textWatcher;
    }

    void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }
}
