package com.alexandruro.whistscoretracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.R;

import java.util.List;

public class TableRowAdapter extends RecyclerView.Adapter<TableRowAdapter.MyViewHolder> {

    private final List<String> dataset;

    public TableRowAdapter(List<String> dataset) {
        this.dataset = dataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final TextView textView;
        MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public TableRowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.name_header_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
