package com.alexandruro.whistscoretracker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Custom ListView that always wraps around its content without scrolling
 */
class WrappedListView extends ListView {

    private ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public WrappedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        Log.d("onDraw: ", getCount() + " children");
//        if(getCount() != oldCount){
//            int height = 0;
//            if(getCount()>0)
//                height = getChildAt(getCount()-1).getHeight() + 1;
//            oldCount = getCount();
//            params = getLayoutParams();
//            params.height = getCount() * height;
//            setLayoutParams(params);
//        }
//        super.onDraw(canvas);
//    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
