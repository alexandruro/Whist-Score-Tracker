package com.alexandruro.whistscoretracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.R;

import java.util.List;

/**
 * Adapter for showing the players in an unfinished game in the main menu
 */
public class PlayerBubblesAdapter extends RecyclerView.Adapter<PlayerBubblesAdapter.ViewHolder> {

    private final List<String> playerNames;

    public PlayerBubblesAdapter(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bubble = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_player_bubble, parent, false);
        return new ViewHolder(bubble);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String playerName = playerNames.get(position);
        String truncatedPlayerName = playerName.length() >= 2 ? playerName.substring(0, 2) : playerName;
        ((TextView)holder.getView().findViewById(R.id.textViewPlayerName)).setText(truncatedPlayerName);
    }

    @Override
    public int getItemCount() {
        return playerNames.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public View getView() {
            return itemView;
        }
    }
}
