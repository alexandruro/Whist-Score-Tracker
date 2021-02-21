package com.alexandruro.whistscoretracker.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alexandruro.whistscoretracker.model.Game;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    List<Game> getAll();

    @Query("SELECT * FROM game WHERE uid = :gameId")
    ListenableFuture<Game> getGame(String gameId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Game games);

    @Delete
    void delete(Game game);
}
