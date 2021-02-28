package com.alexandruro.whistscoretracker.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.alexandruro.whistscoretracker.view.EditListItem;
import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.view.EditTextViewHolder;

import java.util.List;

/**
 * Adapter for an EditList (a list of EditText items)
 */
public class EditListAdapter extends ArrayAdapter<EditListItem> {

    private final android.text.TextWatcher textChangedListener;

    public EditListAdapter(Context con, List<EditListItem> list, android.text.TextWatcher textChangedListener) {
        super(con, 0, list);
        this.textChangedListener = textChangedListener;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate a new view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.name_list_item, parent, false);
            EditTextViewHolder viewHolder = new EditTextViewHolder();
            viewHolder.setEditText((EditText) listItemView.findViewById(R.id.name));
            listItemView.setTag(viewHolder);
        }

        EditTextViewHolder holder = (EditTextViewHolder) listItemView.getTag();

        // Remove any existing TextWatcher that will be keyed to the wrong ListItem
        if(holder.getTextWatcher() != null) {
            holder.getEditText().removeTextChangedListener(holder.getTextWatcher());
        }

        holder.getEditText().setText(getItem(position).toString());

        holder.setTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getItem(position).setString(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        holder.getEditText().addTextChangedListener(holder.getTextWatcher());
        holder.getEditText().addTextChangedListener(textChangedListener);

        if(position==getCount()-1)
            listItemView.findViewById(R.id.remove).setVisibility(View.VISIBLE);
        else
            listItemView.findViewById(R.id.remove).setVisibility(View.GONE);

        return listItemView;

    }

}
