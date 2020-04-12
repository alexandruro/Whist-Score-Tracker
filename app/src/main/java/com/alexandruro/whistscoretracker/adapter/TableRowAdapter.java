package com.alexandruro.whistscoretracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alexandruro.whistscoretracker.R;

import java.util.ArrayList;

public class TableRowAdapter extends RecyclerView.Adapter<TableRowAdapter.MyViewHolder> {

    private ArrayList<String> mDataset;

    public TableRowAdapter(ArrayList<String> mDataset) {
        this.mDataset = mDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TableRowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.name_header_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


//    @NonNull
//    @Override
//    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
//
//        // Check if the existing view is being reused, otherwise inflate a new view
//        TextView listItemView = (TextView)convertView;
//        if(listItemView == null) {
//            listItemView = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.name_header_item, null, true);
//            listItemView.setText(getItem(position));
//        }
//
//        return listItemView;
//    }

//    @Override
//    public int getItemCount() {
//        return 0;
//    }
}
