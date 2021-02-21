package com.alexandruro.whistscoretracker.view;

import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Holds information for a View about an EditText in the list of names.
 * Tracks the EditText itself and its corresponding TextWatcher.
 */
public class EditTextViewHolder {
    private EditText editText;
    private TextWatcher textWatcher;

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public TextWatcher getTextWatcher() {
        return textWatcher;
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }
}
