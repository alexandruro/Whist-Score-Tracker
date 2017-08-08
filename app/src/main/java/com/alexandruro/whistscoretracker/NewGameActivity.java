package com.alexandruro.whistscoretracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Activity used for configuring a new game
 */
public class NewGameActivity extends AppCompatActivity {

    private ArrayList<EditListItem> names;
    private EditListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("New Game options");

        names = new ArrayList<>();
//        names.add(new EditListItem("Alex"));
//        names.add(new EditListItem("Ana"));

        adapter = new EditListAdapter(this, names);

        WrappedListView listView = (WrappedListView)findViewById(R.id.nameListView);
        listView.setAdapter(adapter);

    }

    /**
     * Starts the game with the current settings
     * @param view The view that calls the method
     */
    public void startGame(View view){

        RadioButton gameType1 = (RadioButton) findViewById(R.id.radioGameType1);
        RadioButton gameType2 = (RadioButton) findViewById(R.id.radioGameType2);

        RadioButton prize0 = (RadioButton) findViewById(R.id.radioPrizeNone);
        RadioButton prize5 = (RadioButton) findViewById(R.id.radioPrize5);
        RadioButton prize10 = (RadioButton) findViewById(R.id.radioPrize10);
        int prize;

        if(prize0.isChecked())
            prize = 0;
        else if(prize5.isChecked())
            prize = 5;
        else prize= 10;

        adapter.notifyDataSetChanged();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("gameType1", gameType1.isChecked());
        intent.putExtra("prize", prize);
        ArrayList<String> stringNames = new ArrayList<>();
        for(EditListItem item: names) {
            stringNames.add(item.toString());
        }
        intent.putStringArrayListExtra("playerNames", stringNames);
        startActivity(intent);
        finish();
    }

    /**
     * Removes the last EditText element in the list of names
     * @param view The view that calls the method
     */
    public void removeLastName(View view){
        names.remove(names.size()-1);
        adapter.notifyDataSetChanged();
    }

    /**
     * Adds an empty EditText element in the list of names
     * @param view The view that calls the method
     */
    public void addName(View view) {
        adapter.add(new EditListItem(""));
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard?");
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NewGameActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("Cancel", null);
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
