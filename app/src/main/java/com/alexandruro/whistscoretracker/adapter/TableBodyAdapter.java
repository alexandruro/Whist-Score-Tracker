package com.alexandruro.whistscoretracker.adapter;

import android.graphics.Paint;
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

    private Game game;

    public TableBodyAdapter(Game game) {
        this.game = game;
    }

    static class ScoreRowViewHolder extends RecyclerView.ViewHolder {

        TableRow view;

        public ScoreRowViewHolder(@NonNull TableRow itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public ScoreRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TableRow tableRow = (TableRow) LayoutInflater.from(parent.getContext()).inflate(R.layout.score_row, parent, false);

        for(int i = 0; i< game.getPlayerNames().size(); i++) {
            LayoutInflater.from(parent.getContext()).inflate(R.layout.divider, tableRow, true);
            LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item_short, tableRow, true);
            LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item_long, tableRow, true);
        }

        ScoreRowViewHolder viewHolder = new ScoreRowViewHolder(tableRow);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreRowViewHolder holder, int position) {
        Log.d("TableBodyAdapter", "Called onBindViewHolder. with " + position);
        ((TextView) holder.view.findViewById(R.id.roundNumber)).setText(Integer.toString(game.getNrOfHands(position+1)));

        for (int i = 0; i < game.getPlayerNames().size(); i++) {
            if (position < game.getCurrentRound() - 1 || game.getGameStatus() != Game.Status.WAITING_FOR_BET) {
                int bet = game.getScoreTable().get(i).getBet(position + 1);
                TextView betTextView = (TextView)holder.view.getChildAt(getViewIndexOfBet(i));
                betTextView.setText(String.valueOf(bet));

                if (position < game.getCurrentRound() - 1 || game.getGameStatus() == Game.Status.GAME_OVER) {
                    int score = game.getScoreTable().get(i).getScore(position + 1);
                    ((TextView) holder.view.getChildAt(getViewIndexOfScore(i))).setText(String.valueOf(score));
                    if (game.getScoreTable().get(i).lastResult(position + 1))
                        betTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), R.color.colorPositiveResult));
                    else {
                        betTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), R.color.colorNegativeResult));
                        betTextView.setPaintFlags(betTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
                else {
                    // if there is no score remove it and the formatting on the bet
                    ((TextView) holder.view.getChildAt(getViewIndexOfScore(i))).setText("");
                    betTextView.setTextColor(ContextCompat.getColor(betTextView.getContext(), android.R.color.tab_indicator_text));
                    betTextView.setPaintFlags(betTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        }
    }

        @Override
        public int getItemCount() {
            if(game.getGameStatus() == Game.Status.WAITING_FOR_BET)
                return game.getCurrentRound()-1;
            return game.getCurrentRound();
        }

        /**
         * Gets the index of the child view that has the bet of a player
         * @param playerIndex The index of the player
         * @return The index of the child view
         */
        private int getViewIndexOfBet(int playerIndex) {
            return 3*playerIndex+2;
        }

        /**
         * Gets the index of the child view that has the result of a player
         * @param playerIndex The index of the player
         * @return The index of the child view
         */
        private int getViewIndexOfScore(int playerIndex) {
            return 3*playerIndex+3;
        }
    }