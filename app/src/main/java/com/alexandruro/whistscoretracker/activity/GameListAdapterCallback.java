package com.alexandruro.whistscoretracker.activity;

import com.alexandruro.whistscoretracker.model.Game;

/**
 * Allows activities to be notified of actions triggered by a game list adapter
 */
public interface GameListAdapterCallback {

    void onContinueGame(Game game);

    void onDeleteGame(Game game);
}
