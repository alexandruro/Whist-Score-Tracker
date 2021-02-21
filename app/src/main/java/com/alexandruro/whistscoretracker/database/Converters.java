package com.alexandruro.whistscoretracker.database;

import androidx.room.TypeConverter;

import com.alexandruro.whistscoretracker.model.Game;
import com.alexandruro.whistscoretracker.model.PlayerRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Converters for storing the Game object in the database
 */
public class Converters {

    private Converters() {
        throw new IllegalStateException("Utility class");
    }

    @TypeConverter
    public static List<PlayerRecord> playerRecordsFromString(String value) {
        Type listType = new TypeToken<List<PlayerRecord>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPlayerRecords(List<PlayerRecord> playerRecords) {
        Gson gson = new Gson();
        return gson.toJson(playerRecords);
    }

    @TypeConverter
    public static Game.Type gameTypeFromString(String value) {
        return Game.Type.valueOf(value);
    }

    @TypeConverter
    public static String fromGameType(Game.Type type) {
        return type.toString();
    }

    @TypeConverter
    public static Game.Status gameStatusFromString(String value) {
        return Game.Status.valueOf(value);
    }

    @TypeConverter
    public static String fromGameStatus(Game.Status status) {
        return status.toString();
    }
}
