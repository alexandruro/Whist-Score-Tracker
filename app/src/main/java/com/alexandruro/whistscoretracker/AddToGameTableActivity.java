package com.alexandruro.whistscoretracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_row);

        GridLayout grid = (GridLayout)findViewById(R.id.grid);

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
                    Log.d("", "onClick: should call with " + value);
                    advance(value);
                }
            });
            grid.addView(button, params);
        }

        Intent intent = getIntent();
        boolean betsPlaced = intent.getBooleanExtra("betsPlaced", false);
        names = intent.getStringArrayListExtra("names");
        index = 0;

        TextView prompt = (TextView) findViewById(R.id.prompt);
        if(!betsPlaced)
            prompt.setText("Place your bets!");
        else
            prompt.setText("Choose your results!");

        playerName = (TextView) findViewById(R.id.playerName);
        inputs = new ArrayList<>();
    }

    /**
     * Confirms an input by the user and either goes to the next player or back to the table
     * @param input The number of hands in the bet/result
     */
    private void advance(int input) {
        inputs.add(input);
        Log.d("", "advance: input " + input);
        if(index<names.size()-1) {
            index++;
            playerName.setText(names.get(index));
        }
        else {
            Intent intent = new Intent();
            intent.putIntegerArrayListExtra("inputs", inputs);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
