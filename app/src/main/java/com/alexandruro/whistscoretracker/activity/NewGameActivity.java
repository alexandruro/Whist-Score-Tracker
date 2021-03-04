package com.alexandruro.whistscoretracker.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.alexandruro.whistscoretracker.config.Constants;
import com.alexandruro.whistscoretracker.config.DevelopmentFlags;
import com.alexandruro.whistscoretracker.adapter.EditListAdapter;
import com.alexandruro.whistscoretracker.view.EditListItem;
import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.view.WrappedListView;
import com.alexandruro.whistscoretracker.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity used for configuring a new game
 */
public class NewGameActivity extends AppCompatActivity {

    private static final String TAG = "NewGameActivity";

    private List<EditListItem> names;
    private EditListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(R.string.title_new_game);
        }

        names = new ArrayList<>();

        if(DevelopmentFlags.PREPOPULATE_PLAYER_NAMES) {
            names.add(new EditListItem("Alex"));
            names.add(new EditListItem("Ana"));
            names.add(new EditListItem("Alin"));
        }
        else {
            names.add(new EditListItem());
        }

        adapter = new EditListAdapter(this, names, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkConstraints();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        WrappedListView listView = findViewById(R.id.nameListView);
        listView.setAdapter(adapter);
        checkConstraints();
    }

    /**
     * Starts the game with the current settings
     * @param view The view that calls the method
     */
    public void startGame(View view){

        RadioButton gameTypeOneEightOne = findViewById(R.id.radioGameTypeOneEightOne);
        Game.Type type;
        if(gameTypeOneEightOne.isChecked())
            type = Game.Type.ONE_EIGHT_ONE;
        else
            type = Game.Type.EIGHT_ONE_EIGHT;

        RadioButton prize0 = findViewById(R.id.radioPrizeNone);
        RadioButton prize5 = findViewById(R.id.radioPrize5);
        int prize;
        if(prize0.isChecked())
            prize = 0;
        else if(prize5.isChecked())
            prize = 5;
        else prize= 10;

        adapter.notifyDataSetChanged();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(Constants.INTENT_TYPE, type);
        intent.putExtra(Constants.INTENT_PRIZE, prize);
        ArrayList<String> stringNames = new ArrayList<>();
        for(EditListItem item: names) {
            stringNames.add(item.toString());
        }
        intent.putStringArrayListExtra(Constants.INTENT_PLAYER_NAMES, stringNames);
        startActivity(intent);
        finish();
    }

    /**
     * Removes the last EditText element in the list of names
     * @param view The view that calls the method
     */
    public void removeLastName(View view){
        Log.d(TAG, "removeLastName");
        names.remove(names.size()-1);
        adapter.notifyDataSetChanged();
        checkConstraints();
    }

    /**
     * Adds an empty EditText element in the list of names
     * @param view The view that calls the method
     */
    public void addName(View view) {
        adapter.add(new EditListItem());
        checkConstraints();
    }

    /**
     * Check the constraints regarding the number of players
     * and disable the "Start game" or "Add player" buttons accordingly
     */
    private void checkConstraints() {
        // Check for "Start game" button
        boolean constraintsMet = true;
        if(names.size() < Constants.MIN_PLAYERS_COUNT || names.size() > Constants.MAX_PLAYERS_COUNT) {
            constraintsMet = false;
        }
        for(EditListItem name: names) {
            if(name.toString().isEmpty()) {
                constraintsMet = false;
            }
        }
        ((Button)findViewById(R.id.buttonStart)).setEnabled(constraintsMet);

        // Check for "Add player" button
        boolean enableAddButton = names.size() < Constants.MAX_PLAYERS_COUNT;
        findViewById(R.id.buttonAdd).setEnabled(enableAddButton);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_prompt);
        builder.setPositiveButton(R.string.discard, (dialog, which) -> NewGameActivity.super.onBackPressed());
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
