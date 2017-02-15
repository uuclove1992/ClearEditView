package com.othershe.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class MatrixView1 extends View {
    private String TAG = MatrixView1.class.getSimpleName();
    private Paint mPaint;
    private int width;
    private int height;

    private Path cPath, lPath, rPath, tPath, bPath;
    private Region cRegion, lRegion, rRegion, tRegion, bRegion;
    private Region globalRegion;

    private int defaultColor = 0xFF4E5268;
    private int touchColor = 0xFFDF9C81;

    private int angelDiff = 15;

    private Matrix invertMatrix;

    private Type mCurrentType = null;


    public MatrixView1(Context context) {
        super(context);
        init(null, 0);
    }

    public MatrixView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MatrixView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        initPaint();
        initPath();
        initRegion();
        invertMatrix = new Matrix();
    }

    private void initPath() {
        cPath = new Path();
        rPath = new Path();
        tPath = new Path();
        lPath = new Path();
        bPath = new Path();
    }

    private void initRegion() {
        cRegion = new Region();
        rRegion = new Region();
        lRegion = new Region();
        tRegion = new Region();
        bRegion = new Region();
        globalRegion = new Region();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(defaultColor);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"canvas.getSaveCount() "+canvas.getSaveCount());
        RectF rect=new RectF(0,0,200,200);
        canvas.drawArc(rect,0,90,false,mPaint);
        canvas.translate(width / 2, height / 2);
        canvas.getMatrix().invert(invertMatrix);
        Log.i(TAG,"invertMatrix: "+invertMatrix);
        canvas.drawPath(cPath, mPaint);
        canvas.drawPath(rPath, mPaint);
        canvas.drawPath(bPath, mPaint);
        canvas.drawPath(lPath, mPaint);
        canvas.drawPath(tPath, mPaint);

        if (mCurrentType == null)
            return;
        mPaint.setColor(touchColor);
        switch (mCurrentType) {
            case CENTER:
                canvas.drawPath(cPath, mPaint);
                break;
            case RIGHT:
                canvas.drawPath(rPath, mPaint);
                break;
            case LEFT:
                canvas.drawPath(lPath, mPaint);
                break;
            case TOP:
                canvas.drawPath(tPath, mPaint);
                break;
            case BOTTOM:
                canvas.drawPath(bPath, mPaint);
                break;
        }
        mPaint.setColor(defaultColor);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getRawX();
        float y = event.getRawY();
        float[] arr = {x, y};
        invertMatrix.mapPoints(arr);
        int x1 = (int) arr[0];
        int y1 = (int) arr[1];
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentType = judge(x1, y1);
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentType = judge(x1, y1);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
        }
        invalidate();
        return true;
    }

    private Type judge(int x, int y) {
        if (cRegion.contains(x, y))
            return Type.CENTER;
        else if (rRegion.contains(x, y))
            return Type.RIGHT;
        else if (lRegion.contains(x, y))
            return Type.LEFT;
        else if (tRegion.contains(x, y))
            return Type.TOP;
        else if (bRegion.contains(x, y))
            return Type.BOTTOM;
        return null;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        float minWidth = Math.min(w, h) * 0.8f;
        float bigR = minWidth / 2;
        float smallR = minWidth / 4;
        float centerR = minWidth / 5;

        globalRegion = new Region(-w/2, -h/2, w/2, h/2);

        cPath.addCircle(0, 0, centerR, Path.Direction.CW);
        cRegion.setPath(cPath,globalRegion);

        RectF bRect = new RectF(-bigR, -bigR, bigR, bigR);
        RectF sRect = new RectF(-smallR, -smallR, smallR, smallR);

        rPath.addArc(bRect, -(90 - angelDiff) / 2.0f, 75f);
        rPath.arcTo(sRect, (90 - angelDiff) / 2.0f, -75f);
        rPath.close();
        rRegion.setPath(rPath,globalRegion);


        bPath.addArc(bRect, (90 + angelDiff) / 2.0f, 75f);
        bPath.arcTo(sRect, 90 + (90 - angelDiff) / 2.0f, -75f);
        bPath.close();
        bRegion.setPath(bPath,globalRegion);


        lPath.addArc(bRect, 90 + (90 + angelDiff) / 2.0f, 75f);
        lPath.arcTo(sRect, 180 + (90 - angelDiff) / 2.0f, -75f);
        lPath.close();
        lRegion.setPath(lPath,globalRegion);


        tPath.addArc(bRect, 180 + (90 + angelDiff) / 2.0f, 75f);
        tPath.arcTo(sRect, -(90 + angelDiff) / 2.0f, -75f);
        tPath.close();
        tRegion.setPath(tPath,globalRegion);

    }

    enum Type {
        CENTER, LEFT, RIGHT, TOP, BOTTOM
    }
}
