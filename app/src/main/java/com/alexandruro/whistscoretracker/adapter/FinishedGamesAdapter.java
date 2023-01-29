package com.alexandruro.whistscoretracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.model.Game;
import com.alexandruro.whistscoretracker.view.WrappedListView;

import java.util.List;

/**
 * Adapter for showing the finished game cards in the main menu
 */
public class FinishedGamesAdapter extends RecyclerView.Adapter<FinishedGamesAdapter.ViewHolder> {

    private final List<Game> gameList;

    public FinishedGamesAdapter(List<Game> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View gameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_finished_game, parent, false);

        return new FinishedGamesAdapter.ViewHolder(gameView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = gameList.get(position);
        View card = holder.getView();

        WrappedListView listViewPlayers = card.findViewById(R.id.listViewPlayers);
        EndPlayerListAdapter finishedGamePlayerListAdapter = new EndPlayerListAdapter(card.getContext(), game.getScoreTable());
        listViewPlayers.setAdapter(finishedGamePlayerListAdapter);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
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
