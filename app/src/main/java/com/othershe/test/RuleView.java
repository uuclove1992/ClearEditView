package com.othershe.test;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class RuleView extends View {
    private String TAG = RuleView.class.getSimpleName();
    private Paint mPaint;
    private int RULE_HEIGHT = 70;
    private int RULE_OUT_MARGIN = 10;
    private int RULE_IN_MARGIN = 10;
    private int RULE_TOP = 200;
    private int DEFAULT_COUNT = 9;
    private int mTotalWidth = 0;
    private int mTotalHeight = 0;
    private int mRuleHeight;
    private int mHalfHeight;
    private int mOutMargin;
    private int mInMargin;
    private float mLineInterval;
    private Resources mResources;

    public RuleView(Context context) {
        super(context);
        mResources = context.getResources();
        init(null, 0);
    }

    public RuleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResources = context.getResources();
        init(attrs, 0);
    }

    public RuleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mResources = context.getResources();
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        initPaint();
//        initData();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        Log.i(TAG, "mTotalWidth: " + mTotalWidth + " mTotalHeight: " + mTotalHeight);
        initData();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private void initData() {
        mRuleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, RULE_HEIGHT, mResources.getDisplayMetrics());
        mHalfHeight = mRuleHeight / 2;
        mOutMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, RULE_OUT_MARGIN, mResources.getDisplayMetrics());
        mInMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, RULE_IN_MARGIN, mResources.getDisplayMetrics());
        Log.i(TAG, "mOutMargin: " + mOutMargin + " mInMargin: " + mInMargin);
        mLineInterval = (float) ((mTotalWidth - 2 * mOutMargin - 2 * mInMargin) / (DEFAULT_COUNT * 10.0));
        Log.i(TAG, "mLineInterval: " + mLineInterval);
        mTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, RULE_TOP, mResources.getDisplayMetrics());
        mBottom = mTop + mRuleHeight;
        mMaxLine = mHalfHeight;
        mMiddleLine = mMaxLine * 3 / 4;
        mLowLine = mHalfHeight / 2;
        mStartX = mOutMargin + mInMargin;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: consider storing these as member variables to reduce
//        drawOuter(canvas);
//        canvas.save();
//        Log.i(TAG, "canvas.getSaveCount(): " + canvas.getSaveCount());
//        drawLines(canvas);
//        canvas.restore();
//        canvas.translate(100, 800);
//        Log.i(TAG, "canvas.getSaveCount(): " + canvas.getSaveCount());
//        drawOuter(canvas);
//        canvas.drawCircle(0, 0, 100, mPaint);

//        drawSquare(canvas);
        drawWatch(canvas);
    }

    private void drawOuter(Canvas canvas) {
        Rect rect = new Rect(mOutMargin, mTop, mTotalWidth - mOutMargin, mBottom);
        canvas.drawRect(rect, mPaint);
    }

    private int mMaxLine = 0;
    private int mMiddleLine = 0;
    private int mLowLine = 0;


    private int mBottom;
    private int mTop;

    private int mStartX;

    private void drawLines(Canvas canvas) {
        int count = DEFAULT_COUNT * 10;
        int height;
        canvas.translate(mStartX, 0);
        for (int i = 0; i <= count; i++) {
            if (i % 10 == 0)
                height = mMaxLine;
            else if (i % 5 == 0)
                height = mMiddleLine;
            else
                height = mLowLine;
            canvas.drawLine(0, mBottom, 0, mTop + mRuleHeight - height, mPaint);
            canvas.translate(mLineInterval, 0);
        }

    }

    private void drawSquare(Canvas canvas) {
        float count = 20.0f;
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, mResources.getDisplayMetrics());
        float x = width / 3.0f;
        float y = x;
        Rect rectSquare = new Rect(0, 0, width, width);
//        canvas.translate(x,y);
        for (int i = 0; i <= count; i++) {
            float fraction = i / count;
            Log.i(TAG, "fraction: " + fraction);

            canvas.save();
            canvas.scale(fraction, fraction, x, y);
            canvas.drawRect(rectSquare, mPaint);
            Log.i(TAG, "canvas.getSaveCount(): " + canvas.getSaveCount());
            canvas.restore();
        }
    }


    private void drawWatch(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10f);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, mResources.getDisplayMetrics());
        int x = width / 2;
        int y = x;
        canvas.drawCircle(x, y, 200, mPaint);
        int angel=360/60;
//        canvas.translate(x,y);
        for (int i = 0; i < 60; i++) {
            canvas.rotate(angel,x,y);
//            if (i % 5 == 0) {
//                canvas.drawLine(0, 160, 0, 200, mPaint);
//            } else {
                canvas.drawLine(x, 180, x, 200, mPaint);
//            }
        }
    }

}
