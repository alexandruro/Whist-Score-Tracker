package com.alexandruro.whistscoretracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.model.Game;
import com.google.android.material.transition.MaterialArcMotion;

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
        View gameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_unfinished_game_layout, parent, false);

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
        // make sure recycler view does not try to handle card click
        recyclerViewPlayers.suppressLayout(true);

        card.setOnClickListener(v -> expandCard(game, card, recyclerViewPlayers));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    private void expandCard(Game game, View card, RecyclerView recyclerViewPlayers) {
        // disable layout suppressing so the animation can play
        recyclerViewPlayers.suppressLayout(false);

        card.setOnClickListener(null);
        ViewGroup sceneRoot = (ViewGroup) card;

        // first part of the transition: material arc motion from horizontal to vertical
        ChangeBounds arcTransition = new ChangeBounds();
        arcTransition.setDuration(300);
        arcTransition.setPathMotion(new MaterialArcMotion());
        arcTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(@NonNull Transition transition) {}

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                // second part of the transition: show expanded layout
                Scene expandedCardScene = Scene.getSceneForLayout(sceneRoot, R.layout.view_unfinished_game_expanded, card.getContext());
                TransitionManager.go(expandedCardScene,
                        new TransitionSet()
                                .addTransition(new ChangeBounds().setDuration(100))
                                .addTransition(new Fade(Fade.MODE_OUT))
                                .addTransition(new Fade(Fade.MODE_IN))
                );

                ((TextView) card.findViewById(R.id.textViewRound)).setText(card.getContext().getResources().getString(R.string.game_current_round, game.getCurrentRound(), game.getNrOfRounds()));
                ((ListView) card.findViewById(R.id.listViewPlayersList)).setAdapter(new GenericPlayerListAdapter(card.getContext(), game.getScoreTable()));
            }

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {}

            @Override
            public void onTransitionPause(@NonNull Transition transition) {}

            @Override
            public void onTransitionResume(@NonNull Transition transition) {}
        });

        TransitionManager.beginDelayedTransition(sceneRoot, arcTransition);
        ((LinearLayoutManager) recyclerViewPlayers.getLayoutManager()).setOrientation(RecyclerView.VERTICAL);
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
