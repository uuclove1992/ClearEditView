package com.othershe.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO: document your custom view class.
 */
public class MyView2 extends ViewGroup {
    private String TAG = MyView2.class.getSimpleName();
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public MyView2(Context context) {
        super(context);
//        init(null, 0);
    }

    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(attrs, 0);
    }

    public MyView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init(attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.i(TAG, "childCount=" + childCount);

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            Log.i(TAG, "width=" + width + "， height=" + height);

            int mLeft = (r - width) / 2;
            int mTop = (b - height) / 2;
            Log.i(TAG, "l=" + l + "， t=" + t + ", r=" + r + ", b=" + b);
            Log.i(TAG, "mLeft=" + mLeft + "， mTop=" + mTop + ", mRight=" + (mLeft + width) + ", mBottom=" + (mTop + height));

            child.layout(mLeft, mTop, mLeft + width, mTop + height);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }


}
