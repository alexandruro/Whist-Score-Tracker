package com.alexandruro.whistscoretracker;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Alex on 14/06/2017.
 */

public class MyListView extends ListView {

    private ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public MyListView(Context context, AttributeSet attrs) {
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
