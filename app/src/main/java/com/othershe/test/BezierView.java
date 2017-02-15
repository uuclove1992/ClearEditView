package com.othershe.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class BezierView extends View {
    private String TAG = BezierView.class.getSimpleName();
    private Paint mPaint;
    private Point mStart, mEnd, mControl;
    private int mCenterX, mCenterY;

    public BezierView(Context context) {
        super(context);
        init(null, 0);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        initPaint();
        mStart = new Point();
        mEnd = new Point();
        mControl = new Point();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        mPaint.setColor(Color.BLACK);
        canvas.drawPoint(mStart.x, mStart.y, mPaint);
        canvas.drawPoint(mEnd.x, mEnd.y, mPaint);
        canvas.drawPoint(mControl.x, mControl.y, mPaint);

        canvas.drawLine(mStart.x, mStart.y, mControl.x, mControl.y, mPaint);
        canvas.drawLine(mEnd.x, mEnd.y, mControl.x, mControl.y, mPaint);

        Path path = new Path();
        path.moveTo(mStart.x,mStart.y);
        path.quadTo(mControl.x, mControl.y, mEnd.x, mEnd.y);
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(path, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mStart.x = mCenterX - 200;
        mStart.y = mCenterY;
        mEnd.x = mCenterX + 200;
        mEnd.y = mCenterY;
        mControl.x = mCenterX;
        mControl.y = mCenterY - 100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        mControl.x = (int) x;
        mControl.y = (int) y;
        invalidate();
        return true;
    }
}
