package com.alexandruro.whistscoretracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexandruro.whistscoretracker.database.GameRepository;
import com.alexandruro.whistscoretracker.model.Game;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * View model for the MainMenuActivity
 */
@HiltViewModel
public class MainMenuViewModel extends ViewModel {

    private final GameRepository gameRepository;

    private final LiveData<List<Game>> finishedGames;

    private final LiveData<List<Game>> unfinishedGames;

    @Inject
    public MainMenuViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.finishedGames = gameRepository.getFinishedGames();
        this.unfinishedGames = gameRepository.getUnfinishedGames();
    }

    public LiveData<List<Game>> getFinishedGames() {
        return finishedGames;
    }

    public LiveData<List<Game>> getUnfinishedGames() {
        return unfinishedGames;
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }
}
