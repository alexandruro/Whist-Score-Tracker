package com.alexandruro.whistscoretracker;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alexandruro.whistscoretracker.model.Game;

import java.util.ArrayList;

public class GameViewModel extends ViewModel {

    private MutableLiveData<Game> game = new MutableLiveData<>();

    public LiveData<Game> getGame() {
        if(game.getValue() == null) {
            Log.w("ViewModel", "getGame() called when game is null");
        }
        return game;
    }

    public void initialiseNewGame(ArrayList<String> playerNames, Game.Type type, int prize) {
        if(game.getValue() != null) {
            Log.w("ViewModel", "initialiseNewGame() called when game is not null");
        }
        game.setValue(new Game(playerNames, type, prize));
        if(DevelopmentFlags.PREPOPULATE_GAME_TABLE) {
            for(int i=0; i<16; i++) {
                addBets(new int[]{1, 1});
                addResults(new int[]{1, 1});
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // This is where the game can be stored
    }

    // Actions for game

    public void undo() {
        game.getValue().undo();
        game.setValue(game.getValue());
    }

    public void addBets(int[] bets) {
        game.getValue().addBets(bets);
        game.setValue(game.getValue());
    }

    public void addResults(int[] results) {
        game.getValue().addResults(results);
        game.setValue(game.getValue());
    }

    public void restartGame() {
        game.getValue().initialiseNewGame();
        game.setValue(game.getValue());
    }

    // Getters for Game

    public Game.Status getGameStatus() {
        return game.getValue().getGameStatus();
    }

    public boolean isStarted() {
        return game.getValue().isStarted();
    }

    public boolean isOver() {
        return game.getValue().isOver();
    }

    public int getNrOfHands() {
        return game.getValue().getNrOfHands();
    }

    public ArrayList<String> getPlayerNames() {
        return game.getValue().getPlayerNames();
    }

    public int getCurrentRound() {
        return game.getValue().getCurrentRound();
    }

    public int getNrOfPlayers() {
        return game.getValue().getNrOfPlayers();
    }
}
