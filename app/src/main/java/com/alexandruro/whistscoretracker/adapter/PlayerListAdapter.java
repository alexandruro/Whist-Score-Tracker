package com.alexandruro.whistscoretracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.model.PlayerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Adapter for showing a list of players and their scores
 */
public abstract class PlayerListAdapter extends ArrayAdapter<PlayerRecord> {

    IntFunction<Integer> playerIconFunction;

    PlayerListAdapter(@NonNull Context context, @NonNull List<PlayerRecord> objects, IntFunction<Integer> playerIconFunction) {
        super(context, 0,  new ArrayList<>(objects));
        this.playerIconFunction = playerIconFunction;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate a new view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    getLayout(), parent, false);
        }

        ((TextView)listItemView.findViewById(R.id.playerName)).setText(getItem(position).getName());
        ((TextView)listItemView.findViewById(R.id.playerScore)).setText(String.valueOf(getItem(position).getScore()));
        ((ImageView) listItemView.findViewById(R.id.playerIcon)).setImageResource(playerIconFunction.apply(position));

        return listItemView;
    }

    protected abstract @LayoutRes int getLayout();
}
