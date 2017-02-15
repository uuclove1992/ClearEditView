package com.othershe.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class BezierHeartView extends View {
    private String TAG = BezierHeartView.class.getSimpleName();
    private Paint mPaint;
    private final float C = 0.551915024494f;
    private float mRadius = 200;
    private float diff = mRadius * C;
    private float[] mData = new float[8];
    private float[] mCtrl = new float[16];
    private int mCenterX, mCenterY;

    private int mDuration = 5000;
    private int mCurrent = 0;
    private int mPiece = 100;
    private int mCount=mDuration/mPiece;

    public BezierHeartView(Context context) {
        super(context);
        init(null, 0);
    }

    public BezierHeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BezierHeartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        initPaint();
        initData();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private void initData() {

        mData[0] = 0;
        mData[1] = mRadius;
        mData[2] = mRadius;
        mData[3] = 0;
        mData[4] = 0;
        mData[5] = -mRadius;
        mData[6] = -mRadius;
        mData[7] = 0;


        mCtrl[0] = diff;
        mCtrl[1] = mRadius;
        mCtrl[2] = mRadius;
        mCtrl[3] = diff;
        mCtrl[4] = mRadius;
        mCtrl[5] = -diff;
        mCtrl[6] = diff;
        mCtrl[7] = -mRadius;
        mCtrl[8] = -diff;
        mCtrl[9] = -mRadius;
        mCtrl[10] = -mRadius;
        mCtrl[11] = -diff;
        mCtrl[12] = -mRadius;
        mCtrl[13] = diff;
        mCtrl[14] = -diff;
        mCtrl[15] = mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinate(canvas);
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        canvas.scale(1, -1);
//        canvas.drawCircle(0, 0, mRadius, mPaint);
        drawAuxiliary(canvas);

        Path path = new Path();
        path.moveTo(mData[0], mData[1]);

        path.cubicTo(mCtrl[0], mCtrl[1], mCtrl[2], mCtrl[3], mData[2], mData[3]);
        path.cubicTo(mCtrl[4], mCtrl[5], mCtrl[6], mCtrl[7], mData[4], mData[5]);
        path.cubicTo(mCtrl[8], mCtrl[9], mCtrl[10], mCtrl[11], mData[6], mData[7]);
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15], mData[0], mData[1]);

        canvas.drawPath(path, mPaint);


        mCurrent += mPiece;
        if (mCurrent < mDuration) {

            mData[1] -= 120 / mCount;
            mCtrl[7] += 80 / mCount;
            mCtrl[9] += 80 / mCount;

            mCtrl[4] -= 20 / mCount;
            mCtrl[10] += 20 / mCount;

            postInvalidateDelayed((long) mPiece);
        }

        // TODO: consider storing these as member variables to reduce

    }

    private void drawAuxiliary(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.GRAY);
        canvas.drawPoints(mCtrl, paint);
        paint.setStrokeWidth(5f);
        for (int i = 0; i < mData.length; i += 2) {
            canvas.drawLine(mData[i], mData[i + 1], mCtrl[(2 * i - 2 + mCtrl.length) % mCtrl.length], mCtrl[(2 * i - 1 + mCtrl.length) % mCtrl.length], paint);
            canvas.drawLine(mData[i], mData[i + 1], mCtrl[(2 * i + mCtrl.length) % mCtrl.length], mCtrl[(2 * i + 1 + mCtrl.length) % mCtrl.length], paint);
        }
        paint.setStrokeWidth(10f);
        paint.setColor(Color.BLUE);
        canvas.drawPoints(mData, paint);
        canvas.restore();
    }

    private void drawCoordinate(Canvas canvas) {
        canvas.save();
        canvas.translate(mCenterX, mCenterY);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);

        canvas.drawLine(-2000, 0, 2000, 0, paint);
        canvas.drawLine(0, -2000, 0, 2000, paint);
        canvas.restore();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
