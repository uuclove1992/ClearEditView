package com.othershe.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class SpiderView extends View {
    private String TAG = SpiderView.class.getSimpleName();
    private Paint mSpiderPaint;
    private Paint mValuePaint;
    private Paint mTextPaint;
    private int[] data = {100, 90, 50, 80, 70, 80};
    private String[] text = {"GggA", "GggB", "GggC", "GggD", "GggE", "GggF"};
    private int maxValue = 100;
    private int mWidth;
    private int mHeight;
    private float mRadius;
    private int mCenterX;
    private int mCenterY;
    private int mCount = 6;
    private float mAngel = (float) Math.PI * 2 / mCount;

    public SpiderView(Context context) {
        super(context);
        init(null, 0);
    }

    public SpiderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SpiderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        initPaint();

    }

    private void initPaint() {
        mSpiderPaint = new Paint();
        mSpiderPaint.setColor(Color.BLACK);
        mSpiderPaint.setStrokeWidth(3f);
        mSpiderPaint.setStyle(Paint.Style.STROKE);

        mValuePaint = new Paint();
        mValuePaint.setColor(Color.BLUE);
        mValuePaint.setStrokeWidth(3f);
        mValuePaint.setStyle(Paint.Style.FILL_AND_STROKE);


        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(60);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSpider(canvas);
        drawLine(canvas);
        drawValue(canvas);
        drawText(canvas);
        // TODO: consider storing these as member variables to reduce

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(w, h) * 0.9 / 2);
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;

    }

    private void drawSpider(Canvas canvas) {
        float diff = mRadius / (mCount - 1);
//        canvas.translate(mCenterX, mCenterY);
        Path path = new Path();
        for (int j = 1; j < mCount; j++) {
            float length = diff * j;
            for (int i = 0; i < mCount; i++) {
                if (i == 0) {
                    path.moveTo(mCenterX + length, mCenterY);
                }
                path.lineTo((float) (mCenterX + length * Math.cos(mAngel * i)), (float) (mCenterY + length * Math.sin(mAngel * i)));
            }
            path.close();
            canvas.drawPath(path, mSpiderPaint);
            path.reset();
        }
    }

    private void drawValue(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mCount; i++) {
            int value = data[i];
            float percent = value / (float) maxValue;
            float x = (float) (mCenterX + mRadius * percent * Math.cos(mAngel * i));
            float y = (float) (mCenterY + mRadius * percent * Math.sin(mAngel * i));
            if (i == 0)
                path.moveTo(x, y);
            path.lineTo(x, y);
            canvas.drawCircle(x, y, 5, mValuePaint);
        }
        mValuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mValuePaint);
        mValuePaint.setAlpha(127);
        mValuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, mValuePaint);
    }

    private void drawLine(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mCount; i++) {
            path.moveTo(mCenterX, mCenterY);
            path.lineTo((float) (mCenterX + mRadius * Math.cos(mAngel * i)), (float) (mCenterY + mRadius * Math.sin(mAngel * i)));
            canvas.drawPath(path, mSpiderPaint);
            path.reset();
        }
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < mCount; i++) {
            float angel = mAngel * i;
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float ascend = fontMetrics.ascent;
            float descend = fontMetrics.descent;
            float fontHeight = descend - ascend;
            Log.i(TAG, "ascend: " + ascend + " descend: " + descend);
            String name = text[i];
            float x = (float) (mCenterX + (mRadius + fontHeight / 2) * Math.cos(mAngel * i));
            float y = (float) (mCenterY + (mRadius + fontHeight / 2) * Math.sin(mAngel * i));
            float length = mTextPaint.measureText(name);
            //第四象限
            if (angel >= 0 && angel <= Math.PI / 2) {
            }
            //第三象限
            else if (angel >= Math.PI / 2 && angel <= Math.PI) {
                x = x - length;
            }//第二象限
            else if (angel > Math.PI && angel <= Math.PI * 3 / 2) {
                x = x - length;
            }//第一象限
            else if (angel >= Math.PI * 3 / 2 && angel <= Math.PI * 2) {

            }
            canvas.drawText(name, x, y, mTextPaint);
        }
    }


}
