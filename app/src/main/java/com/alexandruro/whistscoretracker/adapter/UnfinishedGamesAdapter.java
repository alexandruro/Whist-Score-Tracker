package com.alexandruro.whistscoretracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.model.Game;

import java.util.List;

/**
 * Adapter for showing the unfinished game cards in the main menu
 */
public class UnfinishedGamesAdapter extends RecyclerView.Adapter<UnfinishedGamesAdapter.ViewHolder> {

    private final List<Game> gameList;

    public UnfinishedGamesAdapter(List<Game> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View gameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_unfinished_game, parent, false);

        RecyclerView recyclerViewPlayers = gameView.findViewById(R.id.recyclerViewPlayers);
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(gameView.getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewPlayers.setLayoutManager(recyclerViewLayoutManager);

        return new UnfinishedGamesAdapter.ViewHolder(gameView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = gameList.get(position);
        View card = holder.getView();
        ((TextView)card.findViewById(R.id.textViewRound)).setText(card.getContext().getResources().getString(R.string.game_current_round, game.getCurrentRound(), game.getNrOfRounds()));

        RecyclerView recyclerViewPlayers = card.findViewById(R.id.recyclerViewPlayers);
        PlayerBubblesAdapter playerBubblesAdapter = new PlayerBubblesAdapter(game.getPlayerNames());
        recyclerViewPlayers.setAdapter(playerBubblesAdapter);
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
