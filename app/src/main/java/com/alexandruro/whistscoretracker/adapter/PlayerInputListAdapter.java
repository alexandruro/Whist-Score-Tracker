package com.alexandruro.whistscoretracker.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.model.PlayerInput;

import java.util.List;

/**
 * Adapter for showing the current inputs in AddGameInputActivity
 */
public class PlayerInputListAdapter extends ArrayAdapter<PlayerInput> {

    public PlayerInputListAdapter(@NonNull Context context, @NonNull List<PlayerInput> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate a new view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.player_input_list_item, parent, false);
        }

        PlayerInput playerInput = getItem(position);
        ((TextView) listItemView.findViewById(R.id.textViewName)).setText(playerInput.getPlayerName());
        TextView textViewInput = listItemView.findViewById(R.id.textViewInput);
        if(playerInput.isSet()) {
            textViewInput.setText(String.valueOf(playerInput.getInput()));
            textViewInput.setTypeface(null, Typeface.NORMAL);
        }
        else {
            textViewInput.setText("?");
            textViewInput.setTypeface(null, Typeface.BOLD);
        }

        return listItemView;
    }
}
