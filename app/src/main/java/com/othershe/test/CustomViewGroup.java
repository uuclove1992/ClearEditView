package com.othershe.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO: document your custom view class.
 */
public class CustomViewGroup extends ViewGroup {
    private String TAG = CustomViewGroup.class.getSimpleName();

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomViewGroup(Context context, int columns) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(l, t, r, b);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "****************************** onMeasure()");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "widthMode=EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "widthMode=AT_MOST");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "widthMode=UNSPECIFIED");
                break;
        }
        Log.i(TAG, "widthSize=" + widthSize);
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "heightMode=EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "heightMode=AT_MOST");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "heightMode=UNSPECIFIED");
                break;
        }
        Log.i(TAG, "heightSize=" + heightSize);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (widthMode == MeasureSpec.AT_MOST)
                widthSize = getChildAt(i).getMeasuredWidth();
            if (heightMode == MeasureSpec.AT_MOST)
                heightSize = getChildAt(i).getMeasuredHeight();
            Log.i(TAG, "!!!widthSize=" + widthSize+" heightSize=" + heightSize);
        }

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }


}
