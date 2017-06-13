package com.alexandruro.whistscoretracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

import static android.widget.GridLayout.spec;

public class AddRowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_row);

        GridLayout grid = (GridLayout)findViewById(R.id.grid);

        for(int i=0;i<=8;i++){
            Button button = new Button(this);
            button.setX(i/3);
            button.setY(i%3);
            button.setText(String.valueOf(i));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(spec(i/3),spec(i%3));
            grid.addView(button, params);
        }
    }
}
