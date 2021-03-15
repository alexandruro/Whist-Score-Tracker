package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.alexandruro.whistscoretracker.R;

/**
 * Main menu activity
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    /**
     * Initiates a new game, going to the NewGameActivity
     * @param view The view that calls the method
     */
    public void newGame(View view) {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    /**
     * Continues a previously initiated game
     * @param view The view that calls the method
     */
    public void continueGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * Redirects the user to the "About" screen
     * @param view The view that calls the method
     */
    public void about(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    /**
     * Exits the app
     * @param view The view that calls the method
     */
    public void quit(View view) {
        finish();
        System.exit(0);
    }

}
