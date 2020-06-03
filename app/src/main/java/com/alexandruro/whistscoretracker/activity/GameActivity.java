package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.alexandruro.whistscoretracker.CustomDividerItemDecoration;
import com.alexandruro.whistscoretracker.GameViewModel;
import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.adapter.TableBodyAdapter;
import com.alexandruro.whistscoretracker.adapter.TableRowAdapter;
import com.alexandruro.whistscoretracker.model.Game;
import com.alexandruro.whistscoretracker.model.Game.Status;
import com.alexandruro.whistscoretracker.model.Game.Type;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * Main game activity, where the game table is displayed
 */
public class GameActivity extends AppCompatActivity {

    // development flag to pre-populate some rounds
    private static final boolean POPULATE_ROUNDS = true;

    // request codes
    static final int RESULT_REQUEST = 1;
    static final int BET_REQUEST = 2;

    private GameViewModel gameViewModel;

    // TODO: temporary, get rid of this
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialise the base UI
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(R.string.game_table);
        }
        // Initialise the bottom sheet
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

        // viewmodel
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        // Init / get back data
        if(savedInstanceState==null) {
            initialiseGameState();
        }

        // gameViewModel should have a game already. Call getGame to log if game is null.
        game = gameViewModel.getGame();

        // Initialise the table header
        RecyclerView header = findViewById(R.id.header);
        header.setHasFixedSize(true);
        header.setAdapter(new TableRowAdapter(game.getPlayerNames()));
        GridLayoutManager layoutManager = new GridLayoutManager(this, game.getNrOfPlayers());
        header.setLayoutManager(layoutManager);
        CustomDividerItemDecoration dividerItemDecoration = new CustomDividerItemDecoration(this);
        header.addItemDecoration(dividerItemDecoration);

        // Initialise the table body
        RecyclerView body = findViewById(R.id.tableBody);
        body.setAdapter(new TableBodyAdapter(game));
        RecyclerView.LayoutManager bodyLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        body.setLayoutManager(bodyLayoutManager);

        // Refresh bottom sheet
        if(game.isOver())
            hideBottomSheet();
        else
            updateRoundInfo();

        if(POPULATE_ROUNDS) {
            for(int i=0; i<16; i++) {
                game.addBets(new int[]{1, 1});
                game.addResults(new int[]{1, 1});
            }
        }
    }

    /**
     * Initialises the game state, getting the players and the options from the intent
     */
    private void initialiseGameState() {
        Intent intent = getIntent();
        ArrayList<String> playerNames = intent.getStringArrayListExtra("playerNames");
        int prize = intent.getIntExtra("prize", 0);
        Type type = (Type) intent.getSerializableExtra("type");

        gameViewModel.initialiseNewGame(playerNames, type, prize);
    }

    /**
     * Updates the information in the bottom sheet
     */
    private void updateRoundInfo() {
        int nrOfHands = game.getNrOfHands();
        String currentRoundText = getResources().getQuantityString(R.plurals.numberOfHands, nrOfHands, nrOfHands);
        ((TextView)findViewById(R.id.currentRound)).setText(currentRoundText);
        ((TextView)findViewById(R.id.firstPlayer)).setText(getResources().getString(R.string.first_player, game.getPlayerNames().get((this.game.getCurrentRound() -1) % game.getNrOfPlayers())));
        ((TextView)findViewById(R.id.dealer)).setText(getResources().getString(R.string.dealer, game.getPlayerNames().get((this.game.getCurrentRound() + game.getNrOfPlayers() -2) % game.getNrOfPlayers())));
    }

    /**
     * Resets the game to the beginning
     */
    private void restartGame() {
        initialiseGameState();
        recreate();
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
            case R.id.action_undo: {
                if (!game.isStarted()) {
                    Toast.makeText(this, R.string.no_rounds, Toast.LENGTH_SHORT).show();
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.undo_prompt);
                builder.setPositiveButton(R.string.undo, (dialog, which) -> {
                    if (game.isOver()) {
                        showBottomSheet();
                    }
                    game.undo();
                    updateRoundInfo();
                    notifyTableBodyAdapter();
                    Snackbar.make(findViewById(R.id.game_coord_layout), R.string.undo_result, Snackbar.LENGTH_SHORT).show();
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
                return true;
            }

            case R.id.action_restart: {
                if (!game.isStarted()) {
                    Toast.makeText(this, R.string.no_rounds, Toast.LENGTH_SHORT).show();
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.restart_prompt);
                if (!game.isOver())
                    builder.setMessage(R.string.restart_discard);
                builder.setPositiveButton(R.string.restart, (dialog, which) -> restartGame());
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
                return true;
            }

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

    private void notifyTableBodyAdapter() {
        ((RecyclerView) findViewById(R.id.tableBody)).getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BET_REQUEST) {
            if (resultCode == RESULT_OK) {
                int[] bets = data.getIntArrayExtra("inputs");
                game.addBets(bets);
                notifyTableBodyAdapter();
            }
        }
        else if(requestCode == RESULT_REQUEST) {
            if (resultCode == RESULT_OK) {
                int[] results = data.getIntArrayExtra("inputs");
                game.addResults(results);
                notifyTableBodyAdapter();
                if(game.isOver())
                    endGame();
                else {
                    updateRoundInfo();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

//        if (this.isFinishing ())
//        {
//            // activity dying
//        }
//        else
//        {
//            // activity not dying just stopping
//        }
    }

    /**
     * Method called by pressing the add button. Redirects the user to the AddToGameTableActivity
     * @param view The view that calls the method
     */
    public void addScore(View view) {

        int requestCode;
        if (game.getGameStatus() == Status.WAITING_FOR_RESULT) {
            requestCode = RESULT_REQUEST;
        }
        else requestCode = BET_REQUEST;

        Intent intent = new Intent(this, AddToGameTableActivity.class);
        intent.putExtra("nrOfHands", game.getNrOfHands());
        intent.putStringArrayListExtra("playerNames", game.getPlayerNames());
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("firstPlayerDelay", (game.getCurrentRound() -1) % game.getNrOfPlayers());

        startActivityForResult(intent, requestCode);
    }

    /**
     * Ends the current game
     */
    private void endGame() {
        hideBottomSheet();

        // Show leaderboard
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.game_over);
        ListView playerScores = new ListView(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (16*scale + 0.5f);
        playerScores.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        EndPlayerListAdapter adapter = new EndPlayerListAdapter(this, game.getScoreTable());
        playerScores.setAdapter(adapter);
        builder.setView(playerScores);
        builder.setPositiveButton(R.string.return_to_menu, (dialog, which) -> finish());
        builder.setNegativeButton(R.string.dismiss, null);
        builder.show();
    }

    /**
     * Hides the bottom sheet. Called when the game is over
     */
    private void hideBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        findViewById(R.id.floatingActionButton).setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the bottom sheet. Can be called at the beginning of the game or if the last round is undone
     */
    private void showBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
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
}
