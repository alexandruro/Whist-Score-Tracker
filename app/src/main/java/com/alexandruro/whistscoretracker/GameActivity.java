package com.alexandruro.whistscoretracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TableLayout tableBody = (TableLayout)findViewById(R.id.tableBody);
        TableRow exampleRow = (TableRow)(tableBody.getChildAt(0));


    }

    void addScore(View view) {
        Toast.makeText(getApplicationContext(), "Not implemented yet \uD83D\uDE1E", Toast.LENGTH_SHORT).show();

    }
}
