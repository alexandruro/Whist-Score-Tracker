package com.alexandruro.whistscoretracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Alex on 13/06/2017.
 */

public class EditListAdapter extends ArrayAdapter<String> {

    public EditListAdapter(Context con, ArrayList<String> list)
    {
        super(con, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.name_list_item, parent, false);
        }

        EditText editText = (EditText) listItemView.findViewById(R.id.name);
        editText.setText(getItem(position));

        if(position==getCount()-1)
            listItemView.findViewById(R.id.remove).setVisibility(View.VISIBLE);

        return listItemView;

    }
}
