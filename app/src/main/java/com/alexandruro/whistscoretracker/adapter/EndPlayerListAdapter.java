package com.alexandruro.whistscoretracker.adapter;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.alexandruro.whistscoretracker.model.PlayerRecord;
import com.alexandruro.whistscoretracker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for showing the scores at the end of the game
 */
public class EndPlayerListAdapter extends PlayerListAdapter {

    public static final List<Integer> PLAYER_ICONS = Arrays.asList(R.drawable.ic_firstplace, R.drawable.ic_secondplace, R.drawable.ic_thirdplace);

    public EndPlayerListAdapter(@NonNull Context context, @NonNull List<PlayerRecord> objects) {
        super(context, new ArrayList<>(objects), position -> {
            if(position < PLAYER_ICONS.size()) {
                return PLAYER_ICONS.get(position);
            }
            return android.R.color.transparent;
        });
        sort(Collections.reverseOrder());
    }

    protected @LayoutRes int getLayout() {
        return R.layout.player_list_item;
    }
}
