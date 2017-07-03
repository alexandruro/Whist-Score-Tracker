package com.alexandruro.whistscoretracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Main menu activity
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GameActivity.isRunning)
            findViewById(R.id.buttonContinue).setVisibility(View.VISIBLE);
    }

    /**
     * Initiates a new game, going to the NewGameActivity
     * @param view The view that calls the method
     */
    void newGame(View view) {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    /**
     * Continues a previously initiated game
     * @param view The view that calls the method
     */
    void continueGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * Redirects the user to the settings
     * @param view The view that calls the method
     */
    void settings(View view) {
        Toast.makeText(getApplicationContext(), "Not implemented yet \uD83D\uDE1E", Toast.LENGTH_SHORT).show();
    }

    /**
     * Exits the app
     * @param view The view that calls the method
     */
    void quit(View view) {
        finish();
        System.exit(0);
    }

}