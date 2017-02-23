package com.othershe.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class CustomView extends View {
    private String TAG = CustomView.class.getSimpleName();
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;

    public CustomView(Context context) {
        super(context);
        init(null, 0);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        initPaint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        canvas.clipRect(0, 0, mWidth, mHeight);
        canvas.drawColor(Color.YELLOW);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "widthMode: AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "widthMode: EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "widthMode: UNSPECIFIED");
                break;
        }
        Log.i(TAG, "widthSize: " + widthSize);
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "heightMode: AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "heightMode: EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "heightMode: UNSPECIFIED");
                break;
        }
        Log.i(TAG, "heightSize: " + heightSize);
//        setMeasuredDimension(width, height);
        mWidth = widthSize;
        mHeight = heightSize;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
}
