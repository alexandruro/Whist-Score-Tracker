package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.config.Constants;
import com.alexandruro.whistscoretracker.view.CustomDividerItemDecoration;
import com.alexandruro.whistscoretracker.viewmodel.GameViewModel;
import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.adapter.EndPlayerListAdapter;
import com.alexandruro.whistscoretracker.adapter.TableBodyAdapter;
import com.alexandruro.whistscoretracker.adapter.TableRowAdapter;
import com.alexandruro.whistscoretracker.model.Game;
import com.alexandruro.whistscoretracker.model.Game.Status;
import com.alexandruro.whistscoretracker.model.PlayerRecord;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Main game activity, where the game table is displayed
 */
@AndroidEntryPoint
public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

    private GameViewModel gameViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Creating Game Activity");

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        initialiseUI();
        observeViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_undo:
                if (!gameViewModel.isStarted()) {
                    Toast.makeText(this, R.string.no_rounds, Toast.LENGTH_SHORT).show();
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.undo_prompt);
                builder.setPositiveButton(R.string.undo, (dialog, which) -> undo());
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
                return true;

            case R.id.action_restart:
                if (!gameViewModel.isStarted()) {
                    Toast.makeText(this, R.string.no_rounds, Toast.LENGTH_SHORT).show();
                    return true;
                }
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.restart_prompt);
                if (!gameViewModel.isOver())
                    builder.setMessage(R.string.restart_discard);
                builder.setPositiveButton(R.string.restart, (dialog, which) -> restartGame());
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
                return true;

            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.BET_REQUEST && resultCode == RESULT_OK) {
            int[] bets = data.getIntArrayExtra(Constants.INTENT_INPUTS);
            gameViewModel.addBets(bets);
        }
        else if(requestCode == Constants.RESULT_REQUEST && resultCode == RESULT_OK) {
            int[] results = data.getIntArrayExtra(Constants.INTENT_INPUTS);
            gameViewModel.addResults(results);

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_prompt);
        builder.setPositiveButton(R.string.discard, (dialog, which) -> GameActivity.super.onBackPressed());
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called, will persist data.");
        gameViewModel.persistGame();
    }

    /////////////////////////
    // Initialisation methods (should be called only at the beginning of the activity lifecycle)
    /////////////////////////

    /**
     * Initialise the UI after the activity was created.
     * This includes the layout, toolbar, game table, bottom sheet
     */
    private void initialiseUI() {
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(R.string.game_table);
        }
        initialiseGameTable();
        initialiseBottomSheet();
    }

    /**
     * Initialise the bottom sheet after the activity was created
     */
    private void initialiseBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if(!bottomSheetBehavior.isHideable())
                    findViewById(R.id.floatingActionButton).animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
            }
        });
    }

    /**
     * Initialise the game table.
     */
    private void initialiseGameTable() {
        // Initialise the table header
        RecyclerView header = findViewById(R.id.header);
        header.setHasFixedSize(true);
        CustomDividerItemDecoration dividerItemDecoration = new CustomDividerItemDecoration(this);
        header.addItemDecoration(dividerItemDecoration);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        header.setLayoutManager(layoutManager);

        // Initialise the table body
        RecyclerView body = findViewById(R.id.tableBody);
        RecyclerView.LayoutManager bodyLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        body.setLayoutManager(bodyLayoutManager);
    }

    /**
     * Observe the ViewModel changes to update the UI.
     * This must be called only after the UI has been initialised.
     */
    private void observeViewModel() {
        gameViewModel.getGame().observe(this, game -> {
            if (game != null) {
                Log.d(TAG, "observeViewModel: updating UI");
                // game table header
                RecyclerView header = findViewById(R.id.header);
                header.setAdapter(new TableRowAdapter(game.getPlayerNames()));
                ((GridLayoutManager)header.getLayoutManager()).setSpanCount(game.getNrOfPlayers());
                // game table body
                RecyclerView body = findViewById(R.id.tableBody);
                TableBodyAdapter adapter = new TableBodyAdapter(game);
                body.setAdapter(adapter);
                body.scrollToPosition(adapter.getItemCount()-1); // make sure the last update is visible

                updateBottomSheet(game);

                if(game.isOver()) {
                    showGameOverLeaderboard(game.getScoreTable());
                }
            }
        });
    }

    ///////////////////////
    // Game events methods
    ///////////////////////

    /**
     * Undo the last action (last added bets / results)
     */
    private void undo() {
        gameViewModel.undo();

        // Show a snackbar
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        Snackbar snackbar = Snackbar.make(findViewById(R.id.game_coord_layout), R.string.undo_result, Snackbar.LENGTH_SHORT);
        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            snackbar.setAnchorView(fab);
        }
        snackbar.show();
    }

    /**
     * Resets the game to the beginning
     */
    private void restartGame() {
        gameViewModel.restartGame();
    }

    /**
     * Method called by pressing the add button. Redirects the user to the AddGameInputActivity
     * @param view The view that calls the method
     */
    public void addScore(View view) {

        int requestCode;
        if (gameViewModel.getGameStatus() == Status.WAITING_FOR_RESULT) {
            requestCode = Constants.RESULT_REQUEST;
        }
        else requestCode = Constants.BET_REQUEST;

        Intent intent = new Intent(this, AddGameInputActivity.class);
        intent.putExtra(Constants.INTENT_NR_OF_HANDS, gameViewModel.getNrOfHands());
        intent.putStringArrayListExtra(Constants.INTENT_PLAYER_NAMES, new ArrayList<>(gameViewModel.getPlayerNames()));
        intent.putExtra(Constants.INTENT_REQUEST_CODE, requestCode);
        intent.putExtra(Constants.INTENT_FIRST_PLAYER_INDEX, (gameViewModel.getCurrentRound() -1) % gameViewModel.getNrOfPlayers());

        startActivityForResult(intent, requestCode);
    }

    //////////////////////
    // UI-related methods
    //////////////////////

    /**
     * Shows the leaderboard at the end of the game
     */
    private void showGameOverLeaderboard(List<PlayerRecord> scoreTable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.game_over);
        ListView playerScores = new ListView(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (16*scale + 0.5f);
        playerScores.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        EndPlayerListAdapter adapter = new EndPlayerListAdapter(this, scoreTable);
        playerScores.setAdapter(adapter);
        builder.setView(playerScores);
        builder.setPositiveButton(R.string.return_to_menu, (dialog, which) -> finish());
        builder.setNegativeButton(R.string.dismiss, null);
        builder.show();
    }

    /**
     * Updates the bottom sheet by either updating the information and showing it or by hiding it if the game is over
     */
    private void updateBottomSheet(Game game) {
        if(game.isOver())
            hideBottomSheet();
        else {
            showBottomSheet();
            int nrOfHands = game.getNrOfHands();
            String currentRoundText = getResources().getQuantityString(R.plurals.numberOfHands, nrOfHands, nrOfHands);
            ((TextView) findViewById(R.id.currentRound)).setText(currentRoundText);
            ((TextView) findViewById(R.id.firstPlayer)).setText(getResources().getString(R.string.first_player, game.getCurrentFirstPlayer()));
            ((TextView) findViewById(R.id.dealer)).setText(getResources().getString(R.string.dealer, game.getCurrentDealer()));
        }
    }

    /**
     * Hides the bottom sheet. Called when the game is over
     */
    private void hideBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(0);
        findViewById(R.id.floatingActionButton).setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the bottom sheet. Can be called at the beginning of the game or if the last round is undone
     */
    private void showBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(getResources().getDimensionPixelSize(R.dimen.sheet_peek));
        findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
    }
}
