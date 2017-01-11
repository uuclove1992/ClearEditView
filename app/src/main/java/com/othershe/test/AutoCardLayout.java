package com.othershe.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * TODO: document your custom view class.
 */
public class AutoCardLayout extends ViewGroup {
    private int columns;
    private int margin = 10;
    private String TAG = AutoCardLayout.class.getSimpleName();

    public AutoCardLayout(Context context) {
        super(context);
        init(context, 0);
    }

    public AutoCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(attrs, 0);
    }

    public AutoCardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init(attrs, defStyle);
    }

    public AutoCardLayout(Context context, int columns) {
        super(context);
        init(context, columns);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int[] columnHeight = new int[columns];
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();

            int column = i % columns + 1;
            int row = i / column + 1;

            int mLeft = (column - 1) * (margin + width);
            int mTop = (row - 1) * margin + columnHeight[column-1];
            columnHeight[column-1] += height;
            Log.i(TAG, "mLeft=" + mLeft + ", mTop=" + mTop + ", width=" + width + ", height=" + height + ", columnHeight=" + columnHeight[0]);
            view.layout(mLeft, mTop, mLeft + width, mTop + height);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
        int childWidth = (widthSize - margin * (columns - 1)) / columns;
        int childCount = getChildCount();
        Log.i(TAG, "childWidth=" + childWidth);

        int index = 0;
        int[] columnHeight = new int[columns];
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int widthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            child.measure(widthSpec, heightSpec);
            if (columns > 1)
                index = i % columns;
            columnHeight[index] += child.getMeasuredHeight();
        }
        int maxHeight = 0;
        for (int height : columnHeight) {
            maxHeight = maxHeight > height ? maxHeight : height;
        }
        Log.i(TAG, "maxHeight=" + maxHeight);

        setMeasuredDimension(widthSize, maxHeight);
    }

    private void init(Context ctx, int columns) {
        // Load attributes
        this.columns = columns;
        View v1 = LayoutInflater.from(ctx).inflate(R.layout.card_layout1, null);
        View v2 = LayoutInflater.from(ctx).inflate(R.layout.card_layout2, null);
        View v3 = LayoutInflater.from(ctx).inflate(R.layout.card_layout3, null);
        View v4 = LayoutInflater.from(ctx).inflate(R.layout.card_layout4, null);
        View v5 = LayoutInflater.from(ctx).inflate(R.layout.card_layout5, null);
        View v11 = LayoutInflater.from(ctx).inflate(R.layout.card_layout1, null);
        View v21 = LayoutInflater.from(ctx).inflate(R.layout.card_layout2, null);
        View v31 = LayoutInflater.from(ctx).inflate(R.layout.card_layout3, null);
        View v41 = LayoutInflater.from(ctx).inflate(R.layout.card_layout4, null);
        View v51 = LayoutInflater.from(ctx).inflate(R.layout.card_layout5, null);
        addView(v1, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v2, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v3, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v4, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v5, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v11, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v21, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v31, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v41, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v51, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    }


}
