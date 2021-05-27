package com.alexandruro.whistscoretracker.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utils {

    private Utils() {}

    /**
     * Adjust a listview height to fit all the items. This is needed if
     * the listview is in a scrollview but you don't want the listview itself to scroll
     * @param listView
     */
    public static void justifyListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
