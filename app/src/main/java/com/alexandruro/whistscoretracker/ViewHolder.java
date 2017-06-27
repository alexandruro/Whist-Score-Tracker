package com.alexandruro.whistscoretracker;

import android.text.TextWatcher;
import android.widget.EditText;

/**
 * ViewHolder which also tracks the TextWatcher for an EditText
 * Created by Alex on 27/06/2017.
 */
class ViewHolder {
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
