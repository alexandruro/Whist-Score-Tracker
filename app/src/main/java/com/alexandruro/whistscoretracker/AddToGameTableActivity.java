package com.alexandruro.whistscoretracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private String text;
    private ArrayList<String> names;
    private ArrayList<Integer> inputs;
    private int index;
    private TextView playerName;
    private int nrOfHands;
    private int handsLeft;
    private int requestCode;

    private GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_row);

        // Get data from intent
        Intent intent = getIntent();
        nrOfHands = intent.getIntExtra("nrOfHands", 8);
        requestCode = intent.getIntExtra("requestCode", -1);
        names = intent.getStringArrayListExtra("names");


        // Initialise layout elements
        grid = (GridLayout)findViewById(R.id.grid);
        playerName = (TextView) findViewById(R.id.playerName);
        TextView prompt = (TextView) findViewById(R.id.prompt);
        if (requestCode==GameActivity.RESULT_REQUEST)
            prompt.setText("Choose your results!");
        else
            prompt.setText("Place your bets!");
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

        index = -1;
        handsLeft = nrOfHands;
        inputs = new ArrayList<>();
        advance(-1);
    }

    /**
     * Confirms an input by the user and either goes to the next player or back to the table
     * @param input The number of hands in the bet/result
     */
    private void advance(int input) {
        if(input!=-1) {
            inputs.add(input);
            handsLeft -= input;
        }
        if(index<names.size()-1) {
            index++;
            playerName.setText(names.get(index));
            for(int i=0;i<=8;i++) {
                if(i>nrOfHands
                        || (requestCode==GameActivity.BET_REQUEST && i==handsLeft && index==names.size()-1)
                        || (requestCode==GameActivity.RESULT_REQUEST &&
                            ((i!=handsLeft && index==names.size()-1) || i>handsLeft)))
                    ((Button)grid.getChildAt(i)).setEnabled(false);
                else
                    ((Button)grid.getChildAt(i)).setEnabled(true);
            }
        }
        else {
            Intent intent = getIntent();
            intent.putIntegerArrayListExtra("inputs", inputs);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
