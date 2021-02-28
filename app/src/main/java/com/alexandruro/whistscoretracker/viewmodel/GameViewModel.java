package com.alexandruro.whistscoretracker.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.alexandruro.whistscoretracker.config.DevelopmentFlags;
import com.alexandruro.whistscoretracker.exception.DatabaseOperationException;
import com.alexandruro.whistscoretracker.model.Game;
import com.alexandruro.whistscoretracker.database.GameRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * View model for the GameActivity.
 */
@HiltViewModel
public class GameViewModel extends ViewModel {

    private static final String TAG = "GameViewModel";
    private static final String GAME_ID_KEY = "gameId";

    @Inject
    GameRepository gameRepository;

    private final MutableLiveData<Game> game;

    private final SavedStateHandle savedStateHandle;

    @Inject
    public GameViewModel(SavedStateHandle savedStateHandle, GameRepository gameRepository) {
        game = new MutableLiveData<>();
        this.gameRepository = gameRepository;
        this.savedStateHandle = savedStateHandle;
        if(savedStateHandle.contains(GAME_ID_KEY)) {
            retrievePersistedGame();
        }
        else {
            initialiseNewGame();
        }
    }

    public LiveData<Game> getGame() {
        if(game.getValue() == null) {
            Log.w("ViewModel", "getGame() called when game is null");
        }
        return game;
    }

    /**
     * Persist the game in the database
     */
    public void persistGame() {
        gameRepository.insert(game.getValue());
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

    public List<String> getPlayerNames() {
        return game.getValue().getPlayerNames();
    }

    public int getCurrentRound() {
        return game.getValue().getCurrentRound();
    }

    public int getNrOfPlayers() {
        return game.getValue().getNrOfPlayers();
    }


    // Private methods

    /**
     * Initialises a new game using the options from the intent, which are put in the savedStateHandle
     */
    private void initialiseNewGame() {
        Log.d(TAG, "GameViewModel: Did not find saved game id. This is a new game.");

        List<String> playerNames = savedStateHandle.get("playerNames");
        int prize = savedStateHandle.get("prize");
        Game.Type type = (Game.Type) savedStateHandle.get("type");

        game.setValue(new Game(playerNames, type, prize));
        if(DevelopmentFlags.PREPOPULATE_GAME_TABLE) {
            for(int i=0; i<20; i++) {
                addBets(new int[]{0, 0, 0});
                int[] results = new int[]{0, 0, 0};
                results[0] = game.getValue().getNrOfHands();
                addResults(results);
            }
        }
        String gameId = game.getValue().getUid();
        savedStateHandle.set(GAME_ID_KEY, gameId);
        Log.d(TAG, "initialiseNewGame: saved gameId");
    }

    /**
     * Retrieve the persisted game from the repository
     */
    private void retrievePersistedGame() {
        String gameId = savedStateHandle.get(GAME_ID_KEY);
        Log.d(TAG, "GameViewModel: Found saved game id: " + gameId);

        try {
            game.setValue(gameRepository.getGame(gameId).get());
        } catch (ExecutionException e) {
            throw new DatabaseOperationException("Database threw an ExecutionException while retrieving a saved game.", e);
        } catch (InterruptedException e) {
            Log.e(TAG, "retrievePersistedGame: Got InterruptedException executing database query.", e);
            Thread.currentThread().interrupt();
        }
    }
}
