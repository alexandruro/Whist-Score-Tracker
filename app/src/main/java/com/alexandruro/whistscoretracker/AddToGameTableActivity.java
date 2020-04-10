package com.alexandruro.whistscoretracker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.widget.GridLayout.spec;

/**
 * Activity used for adding bets and results to the game table
 */
public class AddToGameTableActivity extends AppCompatActivity {

    private ArrayList<String> playerNames;
    private int[] inputs;
    private int index;
    private TextView playerName;
    private int nrOfHands;
    private int handsLeft;
    private int requestCode;
    private int firstPlayerDelay;
    private int nrOfPlayers;

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
        grid = (GridLayout)findViewById(R.id.grid);
        playerName = (TextView) findViewById(R.id.playerName);
        TextView prompt = (TextView) findViewById(R.id.prompt);
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
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    advance(value);
                }
            });
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
            inputs[(index+firstPlayerDelay)%nrOfPlayers] = input;
            handsLeft -= input;
        }
        if(index< playerNames.size()-1) {
            index++;
            playerName.setText(playerNames.get((index+firstPlayerDelay)%nrOfPlayers));
            for(int i=0;i<=8;i++) {
                if(i>nrOfHands
                        || (requestCode==GameActivity.BET_REQUEST && i==handsLeft && index== playerNames.size()-1)
                        || (requestCode==GameActivity.RESULT_REQUEST &&
                            ((i!=handsLeft && index== playerNames.size()-1) || i>handsLeft)))
                    grid.getChildAt(i).setEnabled(false);
                else
                    grid.getChildAt(i).setEnabled(true);
            }
        }
        else {
            Intent intent = getIntent();
            intent.putExtra("inputs", inputs);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
