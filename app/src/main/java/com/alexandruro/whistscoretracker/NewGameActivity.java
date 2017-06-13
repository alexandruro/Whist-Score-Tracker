package com.alexandruro.whistscoretracker;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("New Game options");

        ArrayList<String> names = new ArrayList<>();
        names.add("Alex");
        names.add("Ana");

        EditListAdapter adapter = new EditListAdapter(this, names);

        ListView listView = (ListView)findViewById(R.id.nameListView);
        listView.setAdapter(adapter);

    }

    void startGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
