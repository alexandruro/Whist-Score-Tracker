package com.alexandruro.whistscoretracker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Custom ListView that always wraps around its content without scrolling
 */
public class WrappedListView extends ListView {

    public WrappedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
