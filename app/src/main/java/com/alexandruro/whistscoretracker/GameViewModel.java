package com.alexandruro.whistscoretracker;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.alexandruro.whistscoretracker.model.Game;

import java.util.ArrayList;

public class GameViewModel extends ViewModel {

    private Game game;

    public Game getGame() {
        if(game == null) {
            Log.w("ViewModel", "getGame() called when game is null");
        }
        return game;
    }

    public void initialiseNewGame(ArrayList<String> playerNames, Game.Type type, int prize) {
        if(game != null) {
            Log.w("ViewModel", "initialiseNewGame() called when game is not null");
        }
        game = new Game(playerNames, type, prize);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // This is where the game can be stored
    }

}
