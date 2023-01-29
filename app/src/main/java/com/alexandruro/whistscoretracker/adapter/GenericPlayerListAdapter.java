package com.alexandruro.whistscoretracker.adapter;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.model.PlayerRecord;

import java.util.List;

public class GenericPlayerListAdapter extends PlayerListAdapter {

    public static final int PLAYER_ICON = R.drawable.ic_baseline_person_24;

    GenericPlayerListAdapter(@NonNull Context context, @NonNull List<PlayerRecord> objects) {
        super(context, objects, position -> PLAYER_ICON);
    }

    protected @LayoutRes int getLayout() {
        return R.layout.player_list_item;
    }
}
