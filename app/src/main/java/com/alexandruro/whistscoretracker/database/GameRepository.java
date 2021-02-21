package com.alexandruro.whistscoretracker.database;

import android.os.AsyncTask;
import android.util.Log;

import com.alexandruro.whistscoretracker.model.Game;
import com.google.common.util.concurrent.ListenableFuture;

import javax.inject.Inject;

/**
 * Repository for storing and retrieving Game objects.
 * Currently uses a Room database.
 */
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
            Log.d("GameRepository", "Inserting game " + games[0].getUid() + " into database");
            gameDao.insert(games[0]);
            Log.d("GameRepository", "Inserted game " + games[0].getUid() + " into database");
            return null;
        }
    }
}
