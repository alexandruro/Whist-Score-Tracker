package com.alexandruro.whistscoretracker.database;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.alexandruro.whistscoretracker.model.Game;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository for storing and retrieving Game objects.
 * Currently uses a Room database.
 */
@Singleton
public class GameRepository {

    private final GameDao gameDao;

    /**
     * Creates a game repository
     * @param gameDao The Game DAO
     */
    @Inject
    public GameRepository(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    /**
     * Tries to retrieve a game from the database using the game id
     * @param gameId The game id
     * @return The retrieved game, packaged in a ListenableFuture
     */
    public ListenableFuture<Game> getGame(String gameId) {
        return gameDao.getGame(gameId);
    }

    /**
     * Inserts a game into the database. On conflict, it replaces the row found in the database.
     * @param game The game
     */
    public void insert(Game game) {
        new InsertAsyncTask(gameDao).execute(game);
    }

    /**
     * Deletes a game from the database.
     * @param game The game
     */
    public void delete(Game game) {
        new DeleteAsyncTask(gameDao).execute(game);
    }

    /**
     * Gets a list of past finished games, packaged in a LiveData object
     */
    public LiveData<List<Game>> getFinishedGames() {
        return gameDao.getAllFinished();
    }

    /**
     * Gets a list of past unfinished games, packaged in a LiveData object
     */
    public LiveData<List<Game>> getUnfinishedGames() {
        return gameDao.getAllUnfinished();
    }


    /**
     * Async task to insert a game in the database.
     * Only works with one game. If given multiple games, it will insert the first one.
     */
    private static class InsertAsyncTask extends AsyncTask<Game, Void, Void> {

        private final GameDao gameDao;

        InsertAsyncTask(GameDao dao) {
            gameDao = dao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            Game game = games[0];
            Log.d("GameRepository", "Inserting game " + game.getUid() + " into database");
            game.setUpdatedTime(System.currentTimeMillis());
            gameDao.insert(game);
            Log.d("GameRepository", "Inserted game " + game.getUid() + " into database");
            return null;
        }
    }


    /**
     * Async task to delete a game from the database.
     * Only works with one game. If given multiple games, it will delete the first one.
     */
    private static class DeleteAsyncTask extends AsyncTask<Game, Void, Void> {

        private final GameDao gameDao;

        DeleteAsyncTask(GameDao dao) {
            gameDao = dao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            Log.d("GameRepository", "Deleting game " + games[0].getUid() + " from database");
            gameDao.delete(games[0]);
            Log.d("GameRepository", "Deleted game " + games[0].getUid() + " from database");
            return null;
        }
    }
}
