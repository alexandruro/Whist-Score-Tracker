package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.config.Constants;
import com.alexandruro.whistscoretracker.model.GameInput;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static android.widget.GridLayout.spec;

/**
 * Activity used for adding bets and results to the game table
 */
public class AddGameInputActivity extends AppCompatActivity {

    private static final String TAG = "AddGameInputActivity";
    private static final String GAME_INPUT_BUNDLE_KEY = "gameInput";

    private GameInput gameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_row);

        Intent intent = getIntent();
        int requestCode = intent.getIntExtra(Constants.INTENT_REQUEST_CODE, -1);

        // Create / retrieve GameInput object
        if(savedInstanceState == null) {
            // Get data from intent
            int nrOfHands = intent.getIntExtra(Constants.INTENT_NR_OF_HANDS, 8);
            ArrayList<String> playerNames = intent.getStringArrayListExtra(Constants.INTENT_PLAYER_NAMES);
            int firstPlayerIndex = intent.getIntExtra(Constants.INTENT_FIRST_PLAYER_INDEX, 0);
            gameInput = new GameInput(playerNames, nrOfHands, requestCode, firstPlayerIndex);
        }
        else {
            Log.d(TAG, "onCreate: Retrieving existing GameInput.");
            gameInput = (GameInput) savedInstanceState.getSerializable(GAME_INPUT_BUNDLE_KEY);
        }

        initialiseUI(requestCode);
        refreshUI();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putSerializable(GAME_INPUT_BUNDLE_KEY, gameInput);
    }

    @Override
    public void onBackPressed() {
        if(gameInput.getIndex() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.undo_prompt);
            builder.setPositiveButton(R.string.undo, (dialog, which) -> undo());
            builder.setNegativeButton(R.string.cancel, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            super.onBackPressed();
        }
    }

    /**
     * Confirms an input by the user and either goes to the next player or back to the table
     * @param input The number of hands in the bet/result
     */
    public void advance(int input) {
        gameInput.addInput(input);
        if(!gameInput.isDone()) {
            refreshUI();
        }
        else {
            int[] results = gameInput.getInputs();
            Intent intent = getIntent();
            intent.putExtra(Constants.INTENT_INPUTS, results);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * Initialise the UI at the beginning of the activity lifecycle
     * @param requestCode The request code from the intent
     */
    private void initialiseUI(int requestCode) {
        // Initialise layout elements
        GridLayout grid = findViewById(R.id.grid);
        TextView prompt = findViewById(R.id.prompt);
        if (requestCode == Constants.RESULT_REQUEST)
            prompt.setText(R.string.choose_results);
        else
            prompt.setText(R.string.place_bets);
        int gridNrOfColumns;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridNrOfColumns = 3;
        }
        else {
            gridNrOfColumns = 9;
        }
        for(int i=0;i<=8;i++){
            final Button button = new MaterialButton(this, null, R.attr.numpadButtonStyle);
            // Needs both of these
            button.setMinimumWidth(0);
            button.setMinWidth(0);
            button.setText(String.valueOf(i));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(spec(i/gridNrOfColumns, GridLayout.FILL, 1),spec(i%gridNrOfColumns, GridLayout.FILL, 1));
            button.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(button, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            params.leftMargin = 30;
            params.rightMargin = 30;
            final int value = i;
            button.setOnClickListener(view -> advance(value));
            grid.addView(button, params);
        }
    }

    /**
     * Undoes the last input
     */
    private void undo() {
        gameInput.undo();
        refreshUI();
    }

    /**
     * Refreshes the UI (changes player name and enables/disables buttons)
     */
    private void refreshUI() {
        resetPlayerName();
        GridLayout grid = findViewById(R.id.grid);
        for(int i=0;i<=8;i++) {
            grid.getChildAt(i).setEnabled(gameInput.isValidInput(i));
        }
    }

    /**
     * Changes the player name to the current one
     */
    private void resetPlayerName() {
        TextView playerName = findViewById(R.id.playerName);
        playerName.setText(gameInput.getNextPlayerName());
    }
}
