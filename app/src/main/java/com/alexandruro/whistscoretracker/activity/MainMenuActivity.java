package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.adapter.UnfinishedGamesAdapter;
import com.alexandruro.whistscoretracker.viewmodel.MainMenuViewModel;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Main menu activity
 */
@AndroidEntryPoint
public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = "MainMenuActivity";

    private MainMenuViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setSupportActionBar(findViewById(R.id.toolbar));

        observeViewModel();
    }

    /**
     * Observe the ViewModel changes to update the UI.
     */
    private void observeViewModel() {
        viewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
        viewModel.getUnfinishedGames().observe(this, unfinishedGames -> {
            Log.d(TAG, "observeViewModel: " + unfinishedGames.size() + " unfinished games");

            UnfinishedGamesAdapter unfinishedGamesAdapter = new UnfinishedGamesAdapter(unfinishedGames);
            RecyclerView recyclerView = findViewById(R.id.recyclerViewUnfinishedGames);
            recyclerView.setAdapter(unfinishedGamesAdapter);
            RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
        });
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
