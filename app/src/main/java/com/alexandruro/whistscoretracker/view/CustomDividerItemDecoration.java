package com.alexandruro.whistscoretracker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class CustomDividerItemDecoration extends DividerItemDecoration {

    public CustomDividerItemDecoration(Context context) {
        super(context, HORIZONTAL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        Drawable mDivider = getDrawable();
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        outRect.set(mDivider.getIntrinsicWidth(), 0, 0, 0);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || getDrawable() == null) {
            return;
        }
        draw(c, parent);
    }

    /**
     * Similar to drawHorizontal in the parent class, but drawing to the left instead of to the right of the item
     * @param canvas Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     */
    private void draw(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        Rect mBounds = new Rect();
        Drawable mDivider = getDrawable();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int left = mBounds.left + parent.getPaddingLeft();
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }
}
