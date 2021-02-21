package com.alexandruro.whistscoretracker.config;

import android.content.Context;

import com.alexandruro.whistscoretracker.database.AppDatabase;
import com.alexandruro.whistscoretracker.database.GameDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Configuration file for Dependency Injection.
 */
@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    public static GameDao provideGameDao(AppDatabase database) {
        return database.gameDao();
    }

    @Provides
    public AppDatabase provideDatabase(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context);
    }
}
