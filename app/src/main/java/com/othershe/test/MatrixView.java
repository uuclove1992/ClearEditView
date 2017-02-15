package com.othershe.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

/**
 * TODO: document your custom view class.
 */
public class MatrixView extends View {
    private String TAG = MatrixView.class.getSimpleName();
    private Paint mPaint;

    public MatrixView(Context context) {
        super(context);
        init(null, 0);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        Log.i(TAG, "canvas.getSaveCount(): " + canvas.getSaveCount());
//
//        float[] values = new float[9];
//        int[] location1 = new int[2];
//
        Matrix matrix = canvas.getMatrix();
//        matrix.getValues(values);
//
//        location1[0] = (int) values[2];
//        location1[1] = (int) values[5];
//        Log.i(TAG, "location1 = " + Arrays.toString(location1));
//
//        int[] location2 = new int[2];
//        this.getLocationOnScreen(location2);
//        Log.i(TAG, "location2 = " + Arrays.toString(location2));

        RectF rectF = new RectF(400, 400, 1000, 800);
        Log.i(TAG, "before: " + matrix.toShortString());
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);
//        canvas.setMatrix(matrix);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectF, mPaint);
        Log.i(TAG, "before: " + rectF.toShortString());
        mPaint.setColor(Color.RED);
        matrix.setScale(0.1f, 1);
        canvas.setMatrix(matrix);
        canvas.drawRect(rectF, mPaint);
        float[] src = {0, 0};
        matrix.mapPoints(src);
        matrix.mapRect(rectF);
        canvas.drawCircle(0, 0, 50, mPaint);
        Log.i(TAG, "after: " + rectF.toShortString());
        Log.i(TAG, "after: " + Arrays.toString(src));

    }


}
