package com.alexandruro.whistscoretracker.adapter;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.model.Game;

public class TableBodyAdapter extends RecyclerView.Adapter<TableBodyAdapter.ScoreRowViewHolder> {

    private final Game game;

    public TableBodyAdapter(Game game) {
        this.game = game;
    }

    static class ScoreRowViewHolder extends RecyclerView.ViewHolder {

        private final TableRow view;

        public ScoreRowViewHolder(@NonNull TableRow itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public ScoreRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TableRow tableRow = (TableRow) LayoutInflater.from(parent.getContext()).inflate(R.layout.score_row, parent, false);

        for (int i = 0; i < game.getNrOfPlayers(); i++) {
            LayoutInflater.from(parent.getContext()).inflate(R.layout.divider, tableRow, true);
            LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item_short, tableRow, true);
            LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item_long, tableRow, true);
        }

        return new ScoreRowViewHolder(tableRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreRowViewHolder holder, int position) {
        Log.d("TableBodyAdapter", "Called onBindViewHolder. with " + position);
        ((TextView) holder.view.findViewById(R.id.roundNumber)).setText(Integer.toString(game.getNrOfHands(position + 1)));

        for (int i = 0; i < game.getNrOfPlayers(); i++) {
            if (position < game.getCurrentRound() - 1 || game.getGameStatus() != Game.Status.WAITING_FOR_BET) {
                int bet = game.getScoreTable().get(i).getBet(position + 1);
                TextView betTextView = (TextView) holder.view.getChildAt(getViewIndexOfBet(i));
                betTextView.setText(String.valueOf(bet));

                TextView scoreTextView = (TextView) holder.view.getChildAt(getViewIndexOfScore(i));
                if (position < game.getCurrentRound() - 1 || game.isOver()) {
                    int score = game.getScoreTable().get(i).getScore(position + 1);
                    scoreTextView.setText(String.valueOf(score));
                    if (game.getScoreTable().get(i).isPositiveResult(position + 1)) {
                        betTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), R.color.colorPositiveResult));
                        if(game.getScoreTable().get(i).isPrizeRound(position+1)) {
                            scoreTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), R.color.colorPositiveResult));
                        }
                    }
                    else {
                        betTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), R.color.colorNegativeResult));
                        betTextView.setPaintFlags(betTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        if(game.getScoreTable().get(i).isPrizeRound(position+1)) {
                            scoreTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), R.color.colorNegativeResult));
                        }
                    }
                } else {
                    // if there is no score remove it and the formatting on the bet
                    scoreTextView.setText("");
                    betTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), android.R.color.tab_indicator_text));
                    betTextView.setPaintFlags(betTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    scoreTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), android.R.color.tab_indicator_text));
                    scoreTextView.setTypeface(null, Typeface.NORMAL);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (game.getGameStatus() != Game.Status.WAITING_FOR_RESULT)
            return game.getCurrentRound() - 1;
        return game.getCurrentRound();
    }

    /**
     * Gets the index of the child view that has the bet of a player
     *
     * @param playerIndex The index of the player
     * @return The index of the child view
     */
    private int getViewIndexOfBet(int playerIndex) {
        return 3 * playerIndex + 2;
    }

    /**
     * Gets the index of the child view that has the result of a player
     *
     * @param playerIndex The index of the player
     * @return The index of the child view
     */
    private int getViewIndexOfScore(int playerIndex) {
        return 3 * playerIndex + 3;
    }
}
