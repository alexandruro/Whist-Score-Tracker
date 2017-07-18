package com.alexandruro.whistscoretracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for showing the scores at the end of the game
 */
public class EndPlayerListAdapter extends ArrayAdapter<PlayerRecord> {

    public EndPlayerListAdapter(@NonNull Context context, @NonNull List<PlayerRecord> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate a new view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.end_player_list_item, parent, false);
        }

        ((TextView)listItemView.findViewById(R.id.playerName)).setText(getItem(position).getName());
        ((TextView)listItemView.findViewById(R.id.playerScore)).setText(String.valueOf(getItem(position).getScore()));
        switch(position) {
            case 0:
                ((ImageView)listItemView.findViewById(R.id.playerIcon)).setImageResource(R.drawable.ic_firstplace);
                break;
            case 1:
                ((ImageView)listItemView.findViewById(R.id.playerIcon)).setImageResource(R.drawable.ic_secondplace);
                break;
            case 2:
                ((ImageView)listItemView.findViewById(R.id.playerIcon)).setImageResource(R.drawable.ic_thirdplace);
                break;
            default:
                break;
        }

        return listItemView;

    }
}
