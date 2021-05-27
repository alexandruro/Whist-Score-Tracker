package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.adapter.PlayerInputListAdapter;
import com.alexandruro.whistscoretracker.config.Constants;
import com.alexandruro.whistscoretracker.model.GameInput;
import com.alexandruro.whistscoretracker.utils.Utils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import static android.view.View.FOCUS_DOWN;
import static android.widget.GridLayout.spec;

/**
 * Activity used for adding bets and results to the game table
 */
public class AddGameInputActivity extends AppCompatActivity {

    private static final String TAG = "AddGameInputActivity";
    private static final String GAME_INPUT_BUNDLE_KEY = "gameInput";

    private GameInput gameInput;

    private PlayerInputListAdapter playerInputListAdapter;

    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_row);

        Intent intent = getIntent();
        requestCode = intent.getIntExtra(Constants.INTENT_REQUEST_CODE, -1);

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

        initialiseUI();
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
        Log.d(TAG, "advance: " + input);
        gameInput.addInput(input);
        if(!gameInput.isDone()) {
            refreshUI();
        }
        else {
            int[] results = gameInput.getInputsArray();
            Intent intent = getIntent();
            intent.putExtra(Constants.INTENT_INPUTS, results);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * Initialise the UI at the beginning of the activity lifecycle
     */
    private void initialiseUI() {
        // Prompt
        TextView prompt = findViewById(R.id.prompt);
        if (requestCode == Constants.RESULT_REQUEST)
            prompt.setText(R.string.choose_results);
        else
            prompt.setText(R.string.place_bets);

        // Player input list
        playerInputListAdapter = new PlayerInputListAdapter(this, gameInput.getPlayerInputs());
        ListView playerInputList = findViewById(R.id.playerInputList);
        playerInputList.setAdapter(playerInputListAdapter);
        ((TextView)findViewById(R.id.textViewNrOfHands)).setText(getResources().getString(R.string.out_of_number, gameInput.getNrOfHands()));

        // Grid
        GridLayout grid = findViewById(R.id.grid);
        int gridNrOfColumns = getResources().getInteger(R.integer.integerNumpadColumns);
        for(int i=0;i<=8;i++){
            final Button button = new MaterialButton(this, null, R.attr.numpadButtonStyle);
            // Needs both of these
            button.setMinimumWidth(0);
            button.setMinWidth(0);
            button.setPadding(0,0,0,0);
            button.setText(String.valueOf(i), TextView.BufferType.SPANNABLE);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(spec(i/gridNrOfColumns, GridLayout.FILL, 1),spec(i%gridNrOfColumns, GridLayout.FILL, 1));
            params.width = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            TextViewCompat.setAutoSizeTextTypeWithDefaults(button, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
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
        GridLayout grid = findViewById(R.id.grid);
        for (int i = 0; i <= 8; i++) {
            grid.getChildAt(i).setEnabled(gameInput.isValidInput(i));
        }
        playerInputListAdapter.notifyDataSetChanged();
        Utils.justifyListViewHeightBasedOnChildren(findViewById(R.id.playerInputList));
        ScrollView playersCardScrollView = findViewById(R.id.playersCardScrollView);
        playersCardScrollView.post(() ->
                playersCardScrollView.fullScroll(FOCUS_DOWN)); // make sure the last update is visible
        TextView totalInput = findViewById(R.id.textViewTotalInput);
        totalInput.setText(String.valueOf(gameInput.getInputTotal()));
        if(requestCode == Constants.BET_REQUEST) {
            if (gameInput.getInputTotal() < gameInput.getNrOfHands()) {
                totalInput.setTextColor(ContextCompat.getColor(totalInput.getContext(), R.color.colorPositiveResult));
            } else if (gameInput.getInputTotal() > gameInput.getNrOfHands()) {
                totalInput.setTextColor(ContextCompat.getColor(totalInput.getContext(), R.color.colorNegativeResult));
            } else {
                totalInput.setTextColor(ContextCompat.getColor(totalInput.getContext(), android.R.color.tab_indicator_text));
            }
        }
    }
}
