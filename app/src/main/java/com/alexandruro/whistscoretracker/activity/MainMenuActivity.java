package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.adapter.FinishedGamesAdapter;
import com.alexandruro.whistscoretracker.adapter.UnfinishedGamesAdapter;
import com.alexandruro.whistscoretracker.config.Constants;
import com.alexandruro.whistscoretracker.model.Game;
import com.alexandruro.whistscoretracker.viewmodel.MainMenuViewModel;
import com.google.android.material.appbar.AppBarLayout;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Main menu activity
 */
@AndroidEntryPoint
public class MainMenuActivity extends AppCompatActivity implements GameListAdapterCallback {

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
            RecyclerView recyclerView = findViewById(R.id.recyclerViewUnfinishedGames);
            if (unfinishedGames.isEmpty()) {
                findViewById(R.id.card_empty_unfinished).setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                findViewById(R.id.card_empty_unfinished).setVisibility(View.GONE);
                UnfinishedGamesAdapter unfinishedGamesAdapter = new UnfinishedGamesAdapter(this, unfinishedGames);
                recyclerView.setAdapter(unfinishedGamesAdapter);
                RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(recyclerViewLayoutManager);
            }
        });

        viewModel.getFinishedGames().observe(this, finishedGames -> {
            Log.d(TAG, "observeViewModel: " + finishedGames.size() + " finished games");
            RecyclerView recyclerView = findViewById(R.id.recyclerViewFinishedGames);

            if (finishedGames.isEmpty()) {
                findViewById(R.id.card_empty_finished).setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            else {
                findViewById(R.id.card_empty_finished).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                FinishedGamesAdapter finishedGamesAdapter = new FinishedGamesAdapter(finishedGames);
                recyclerView.setAdapter(finishedGamesAdapter);
                RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(recyclerViewLayoutManager);
            }
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
     * Redirects the user to the "About" screen
     * @param view The view that calls the method
     */
    public void about(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onContinueGame(Game game) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(Constants.INTENT_GAME_ID, game.getUid());
        startActivity(intent);
    }

    @Override
    public void onDeleteGame(Game game) {
        viewModel.deleteGame(game);
    }

    @Override
    public void onClickGame(int position) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewUnfinishedGames);
        NestedScrollView nestedScrollView = (NestedScrollView) recyclerView.getParent().getParent();
        AppBarLayout toolbarLayout = (AppBarLayout) findViewById(R.id.toolbar).getParent().getParent();
        toolbarLayout.setExpanded(false);
        recyclerView.post(() -> {
            float y = recyclerView.getY() + recyclerView.getChildAt(position).getY();
            nestedScrollView.smoothScrollTo(0, (int) y);
        });
    }
}
