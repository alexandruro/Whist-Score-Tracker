package com.alexandruro.whistscoretracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alexandruro.whistscoretracker.model.Game;

/**
 * The main database of the application
 */
@Database(entities = {Game.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "main-db";

    /** The game DAO */
    public abstract GameDao gameDao();

    /**
     * Get the application instance
     * @param context The context for the database
     * @return The application instance
     */
    public static AppDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }
}
