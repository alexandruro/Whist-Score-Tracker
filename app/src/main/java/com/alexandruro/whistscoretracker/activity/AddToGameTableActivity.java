package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alexandruro.whistscoretracker.R;

import java.util.ArrayList;

import static android.widget.GridLayout.spec;

/**
 * Activity used for adding bets and results to the game table
 */
public class AddToGameTableActivity extends AppCompatActivity {

    private ArrayList<String> playerNames;
    private int[] inputs;
    private int index;
    private int nrOfHands;
    private int handsLeft;
    private int requestCode;
    private int firstPlayerDelay;
    private int nrOfPlayers;

    private TextView playerName;
    private GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_row);

        // Get data from intent
        Intent intent = getIntent();
        nrOfHands = intent.getIntExtra("nrOfHands", 8);
        requestCode = intent.getIntExtra("requestCode", -1);
        playerNames = intent.getStringArrayListExtra("playerNames");
        firstPlayerDelay = intent.getIntExtra("firstPlayerDelay", 0);


        // Initialise layout elements
        grid = findViewById(R.id.grid);
        playerName = findViewById(R.id.playerName);
        TextView prompt = findViewById(R.id.prompt);
        if (requestCode==GameActivity.RESULT_REQUEST)
            prompt.setText(R.string.choose_results);
        else
            prompt.setText(R.string.place_bets);
        for(int i=0;i<=8;i++){
            final Button button = new Button(this);
            button.setX(i/3);
            button.setY(i%3);
            button.setText(String.valueOf(i));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(spec(i/3),spec(i%3));
            final int value = i;
            button.setOnClickListener(view -> advance(value));
            grid.addView(button, params);
        }

        // Initialise the rest
        index = -1;
        handsLeft = nrOfHands;
        nrOfPlayers = playerNames.size();
        inputs = new int[nrOfPlayers];
        advance(-1);
    }

    /**
     * Confirms an input by the user and either goes to the next player or back to the table
     * @param input The number of hands in the bet/result
     */
    private void advance(int input) {
        if(input!=-1) {
            inputs[index] = input;
            handsLeft -= input;
        }
        if(index< playerNames.size()-1) {
            index++;
            render();
        }
        else {
            Intent intent = getIntent();
            intent.putExtra("inputs", inputs);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * Undoes the last input
     */
    private void undo() {
        if(index == 0) {
            Log.e("AddToGameActivity", "Tried to undo input with index=0. This should not happen.");
            return;
        }

        index--;
        handsLeft += inputs[index];
        render();
    }

    /**
     * Refreshes the UI (changes player name and enables/disables buttons)
     */
    private void render() {
        resetPlayerName();
        for(int i=0;i<=8;i++) {
            if(i>nrOfHands
                    || (requestCode== GameActivity.BET_REQUEST && i==handsLeft && index== playerNames.size()-1)
                    || (requestCode==GameActivity.RESULT_REQUEST &&
                        ((i!=handsLeft && index== playerNames.size()-1) || i>handsLeft)))
                grid.getChildAt(i).setEnabled(false);
            else
                grid.getChildAt(i).setEnabled(true);
        }
    }

    /**
     * Changes the player name to the current one
     */
    private void resetPlayerName() {
        playerName.setText(playerNames.get((index+firstPlayerDelay)%nrOfPlayers));
    }

    @Override
    public void onBackPressed() {
        if(index > 0) {
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
}
