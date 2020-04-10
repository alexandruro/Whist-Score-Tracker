package com.alexandruro.whistscoretracker;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Main game activity, where the game table is displayed
 */
public class GameActivity extends AppCompatActivity {

    // request codes
    static final int RESULT_REQUEST = 1;
    static final int BET_REQUEST = 2;

    // game status
    private static final int WAITING_FOR_BET = 0;
    private static final int WAITING_FOR_RESULT = 1;
    private static final int GAME_OVER = 2;

    // retained fragment
    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    private RetainedFragment retainedFragment;

    // game state
    private ArrayList<String> playerNames;
    private ArrayList<PlayerRecord> scoreTable;
    private int gameStatus;
    private int currentRound; // starting with 1. if current round ended then it's the next one
    private int nrOfPlayers;
    private boolean gameType1;
    private int prize;

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
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if(!bottomSheetBehavior.isHideable())
                    findViewById(R.id.floatingActionButton).animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
            }
        });

        // find the retained fragment or create it
        FragmentManager fm = getFragmentManager();
        retainedFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);
        if (retainedFragment == null) {
            // Starting a fresh activity
            retainedFragment = new RetainedFragment();
            fm.beginTransaction().add(retainedFragment, TAG_RETAINED_FRAGMENT).commit();
            Toast.makeText(this, "No retained fragment, this must start afresh", Toast.LENGTH_SHORT).show();
        }

        // Init / get back data
        if(savedInstanceState==null) {
            initialiseGameState();
            retainedFragment.setData(scoreTable);
        }
        else {
            scoreTable = retainedFragment.getData();
            if(scoreTable==null) {
                // The app has been killed because there was not enough memory
                Toast.makeText(this, "Sorry, the retained fragment has been deleted somehow", Toast.LENGTH_SHORT).show();
                Log.d("Lifecycle:", "The retained fragment has been deleted somehow");
            }
            playerNames = savedInstanceState.getStringArrayList("playerNames");
            nrOfPlayers = playerNames.size();
            gameStatus = savedInstanceState.getInt("gameStatus");
            currentRound = savedInstanceState.getInt("currentRound");
            gameType1 = savedInstanceState.getBoolean("gameType1");
            prize = savedInstanceState.getInt("prize");
        }

        // Initialise the table header
        TableRow header = findViewById(R.id.header);
        for(int i = 0; i< playerNames.size(); i++) {
            LayoutInflater.from(this).inflate(R.layout.divider, header, true);
            LayoutInflater.from(this).inflate(R.layout.name_header_item, header, true);
            ((TextView)header.getChildAt(getViewIndexOfName(i))).setText(playerNames.get(i));
        }

        // Display old table if not a new game
        if(savedInstanceState!=null) {
            int trueCurrentRound = currentRound;
            for(currentRound=1;currentRound<trueCurrentRound;currentRound++) {
                int[] bets = new int[nrOfPlayers];
                int[] scores = new int[nrOfPlayers];
                for(int i=0;i<nrOfPlayers;i++) {
                    bets[i] = scoreTable.get(i).getBet(currentRound);
                    scores[i] = scoreTable.get(i).getScore(currentRound);
                }
                displayBets(bets);
                displayScores(scores);
            }
            currentRound = trueCurrentRound;

            if(gameStatus==WAITING_FOR_RESULT) {
                int[] bets = new int[nrOfPlayers];
                for(int i=0;i<nrOfPlayers;i++)
                    bets[i] = scoreTable.get(i).getBet(currentRound);
                displayBets(bets);
            }

        }
        if(gameStatus==GAME_OVER)
            hideBottomSheet();
        else
            updateRoundInfo();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("playerNames", playerNames);
        outState.putInt("gameStatus", gameStatus);
        outState.putInt("currentRound", currentRound);
        outState.putBoolean("gameType1", gameType1);
        outState.putInt("prize", prize);
    }

    /**
     * Initialises the game state, getting the players and the options from the intent
     */
    private void initialiseGameState() {
        Intent intent = getIntent();
        playerNames = intent.getStringArrayListExtra("playerNames");
        nrOfPlayers = playerNames.size();
        gameType1 = intent.getBooleanExtra("gameType1", true);
        prize = intent.getIntExtra("prize", 0);

        scoreTable = new ArrayList<>();
        for(int i=0;i<nrOfPlayers;i++)
            scoreTable.add(new PlayerRecord(playerNames.get(i), prize));
        gameStatus = WAITING_FOR_BET;
        currentRound = 1;
    }

    /**
     * Updates the information in the bottom sheet
     */
    private void updateRoundInfo() {
        int nrOfHands = getNrOfHands();
        String currentRoundText = getResources().getQuantityString(R.plurals.numberOfHands, nrOfHands, nrOfHands);
        ((TextView)findViewById(R.id.currentRound)).setText(currentRoundText);
        ((TextView)findViewById(R.id.firstPlayer)).setText(getResources().getString(R.string.first_player, playerNames.get((this.currentRound -1) % nrOfPlayers)));
        ((TextView)findViewById(R.id.dealer)).setText(getResources().getString(R.string.dealer, playerNames.get((this.currentRound +nrOfPlayers-2) % nrOfPlayers)));
    }

    /**
     * Resets the game to the beginning
     */
    private void restartGame() {
        initialiseGameState();
        ((TableLayout) findViewById(R.id.tableBody)).removeAllViews();
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
                if(currentRound==1 && gameStatus==WAITING_FOR_BET) {
                    Toast.makeText(this, R.string.no_rounds, Toast.LENGTH_SHORT).show();
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.undo_prompt);
                builder.setPositiveButton(R.string.undo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TableLayout body = findViewById(R.id.tableBody);
                        TableRow lastRow = (TableRow) body.getChildAt(body.getChildCount()-1);
                        if(gameStatus==WAITING_FOR_RESULT) {
                            for(int i = 0; i< playerNames.size(); i++) {
                                scoreTable.get(i).undoBet();
                                ((TextView) lastRow.getChildAt(getViewIndexOfBet(i))).setText("");
                            }
                            body.removeView(lastRow);
                            gameStatus=WAITING_FOR_BET;
                        }
                        else {
                            for(int i = 0; i< playerNames.size(); i++) {
                                scoreTable.get(i).undoResult();
                                ((TextView) lastRow.getChildAt(getViewIndexOfScore(i))).setText("");
                                TextView bet = (TextView) lastRow.getChildAt(getViewIndexOfBet(i));
                                bet.setTextColor(ContextCompat.getColor(GameActivity.this, android.R.color.primary_text_light));
                                bet.setPaintFlags(bet.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                            }
                            if(gameStatus==GAME_OVER) { // if game was ended, undo that
                                View bottomSheet = findViewById(R.id.bottom_sheet);
                                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                                bottomSheetBehavior.setHideable(false);
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
                            }
                            currentRound--;
                            updateRoundInfo();
                            gameStatus=WAITING_FOR_RESULT;
                        }
                        Snackbar.make(findViewById(R.id.game_coord_layout), R.string.undo_result, Snackbar.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.action_restart:
                if(currentRound ==0) {
                    Toast.makeText(this, R.string.no_rounds, Toast.LENGTH_SHORT).show();
                    return true;
                }
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.restart_prompt);
                if(gameStatus!=GAME_OVER)
                    builder.setMessage(R.string.restart_discard);
                builder.setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                });
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
        if (requestCode == BET_REQUEST) {
            if (resultCode == RESULT_OK) {
                int[] bets = data.getIntArrayExtra("inputs");
                for(int i = 0; i< playerNames.size(); i++)
                    scoreTable.get(i).addBet(bets[i]);
                displayBets(bets);
                gameStatus = WAITING_FOR_RESULT;
            }
        }
        else if(requestCode == RESULT_REQUEST) {
            if (resultCode == RESULT_OK) {
                int[] results = data.getIntArrayExtra("inputs");
                int[] scores = new int[nrOfPlayers];
                for(int i = 0; i< playerNames.size(); i++) {
                    scoreTable.get(i).addResult(results[i]);
                    scores[i] = scoreTable.get(i).getScore();
                }
                displayScores(scores);
                currentRound++;
                if(currentRound >3*nrOfPlayers+12)
                    endGame();
                else {
                    updateRoundInfo();
                    gameStatus = WAITING_FOR_BET;
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();


        if (this.isFinishing ())
        {
            // activity dying
        }
        else
        {
            // activity not dying just stopping
        }

        // this means that this activity will not be recreated now, user is leaving it
        // or the activity is otherwise finishing
        if(isFinishing()) {
            Toast.makeText(this, "The activity is finishing", Toast.LENGTH_LONG).show();
            Log.d("Lifecycle:", "The activity is finishing");
            FragmentManager fm = getFragmentManager();
            // we will not need this fragment anymore
            fm.beginTransaction().remove(retainedFragment).commit();
//            throw new RuntimeException();
        }
    }

    /**
     * Method called by pressing the add button. Redirects the user to the AddToGameTableActivity
     * @param view The view that calls the method
     */
    public void addScore(View view) {

        int requestCode;
        if (gameStatus==WAITING_FOR_RESULT) {
            requestCode = RESULT_REQUEST;
        }
        else requestCode = BET_REQUEST;

        Intent intent = new Intent(this, AddToGameTableActivity.class);
        intent.putExtra("nrOfHands", getNrOfHands());
        intent.putStringArrayListExtra("playerNames", playerNames);
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("firstPlayerDelay", (currentRound -1) % nrOfPlayers);

        startActivityForResult(intent, requestCode);
    }

    /**
     * Adds the bets of the current round to the table
     * @param bets The bets of the players
     */
    private void displayBets(int[] bets) {
        TableLayout body = findViewById(R.id.tableBody);
        TableRow newRow = new TableRow(this);
        LayoutInflater.from(this).inflate(R.layout.score_number, newRow, true);
        ((TextView)newRow.getChildAt(0)).setText(String.valueOf(getNrOfHands()));

        for(int i = 0; i< playerNames.size(); i++) {
            LayoutInflater.from(this).inflate(R.layout.divider, newRow, true);
            LayoutInflater.from(this).inflate(R.layout.score_item_short, newRow, true);
            LayoutInflater.from(this).inflate(R.layout.score_item_long, newRow, true);
            ((TextView)newRow.getChildAt(getViewIndexOfBet(i))).setText(String.valueOf(bets[i]));
            ((TextView)newRow.getChildAt(getViewIndexOfScore(i))).setText("");
        }

        body.addView(newRow);
    }

    /**
     * Adds the scores of the current round to the table
     * @param scores The scores of the players
     */
    private void displayScores(int[] scores) {
        TableLayout body = findViewById(R.id.tableBody);
        TableRow lastRow = (TableRow) body.getChildAt(body.getChildCount()-1);

        for(int i = 0; i< playerNames.size(); i++) {
            ((TextView) lastRow.getChildAt(getViewIndexOfScore(i))).setText(String.valueOf(scores[i]));
            if(scoreTable.get(i).lastResult())
                ((TextView) lastRow.getChildAt(getViewIndexOfBet(i))).setTextColor(ContextCompat.getColor(this, R.color.colorPositiveResult));
            else {
                TextView textView = (TextView) lastRow.getChildAt(3 * i + 2);
                textView.setTextColor(ContextCompat.getColor(this, R.color.colorNegativeResult));
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }

    /**
     * Ends the current game
     */
    private void endGame() {
        gameStatus = GAME_OVER;

        hideBottomSheet();

        // Show leaderboard
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.game_over);
        Collections.sort(scoreTable);
        ListView playerScores = new ListView(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (16*scale + 0.5f);
        playerScores.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        EndPlayerListAdapter adapter = new EndPlayerListAdapter(this, scoreTable);
        playerScores.setAdapter(adapter);
        builder.setView(playerScores);
        builder.setPositiveButton(R.string.return_to_menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.dismiss, null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Hides the bottom sheet. Called when the game is over
     */
    private void hideBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        findViewById(R.id.floatingActionButton).setVisibility(View.INVISIBLE);
    }

    /**
     * Computes the number of hands in the current round
     * @return The number of hands
     */
    private int getNrOfHands() {
        if(currentRound>3*nrOfPlayers+12)
            return -1;
        if(gameType1)
            if(currentRound<=nrOfPlayers)
                return 1;
            else if(currentRound<=nrOfPlayers+6)
                return currentRound-nrOfPlayers+1;
            else if(currentRound<=2*nrOfPlayers+6)
                return 8;
            else if(currentRound<=2*nrOfPlayers+12)
                return 2*nrOfPlayers+14-currentRound;
            else return 1;
        else
        if(currentRound<=nrOfPlayers)
            return 8;
        else if(currentRound<=nrOfPlayers+6)
            return nrOfPlayers+8-currentRound;
        else if(currentRound<=2*nrOfPlayers+6)
            return 1;
        else if(currentRound<=2*nrOfPlayers+12)
            return currentRound-2*nrOfPlayers-5;
        else return 8;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_prompt);
        builder.setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GameActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Gets the index of the child view that has the name of a player
     * @param playerIndex The index of the player
     * @return The index of the child view
     */
    private int getViewIndexOfName(int playerIndex) {
        return 2*playerIndex+2;
    }

    /**
     * Gets the index of the child view that has the bet of a player
     * @param playerIndex The index of the player
     * @return The index of the child view
     */
    private int getViewIndexOfBet(int playerIndex) {
        return 3*playerIndex+2;
    }

    /**
     * Gets the index of the child view that has the result of a player
     * @param playerIndex The index of the player
     * @return The index of the child view
     */
    private int getViewIndexOfScore(int playerIndex) {
        return 3*playerIndex+3;
    }
}
