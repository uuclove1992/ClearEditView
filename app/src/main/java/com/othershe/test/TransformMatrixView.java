package com.othershe.test;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

/**
 * TODO: document your custom view class.
 */
public class TransformMatrixView extends ImageView {
    private String TAG = TransformMatrixView.class.getSimpleName();
    private Paint mPaint;
    private Bitmap mBitmap;
    private Resources mResources;
    private int mWidth, mHeight;
    private Rect mSrcRect, mDestRect;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private int mStartX;
    private int mStartY;
    private Matrix matrix;
    private Matrix matrix1;


    public TransformMatrixView(Context context) {
        super(context);
        init(context);
    }

    public TransformMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TransformMatrixView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {
        // Load attributes
        initPaint();
        matrix = new Matrix();
        matrix1 = new Matrix();
        mResources = ctx.getResources();
        initBitmap();
    }

    private void initBitmap() {
        mBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.photo)).getBitmap();
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // TODO: consider storing these as member variables to reduce
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        if (mSrcRect == null) {
            mSrcRect = new Rect(0, 0, mBitmapWidth, mBitmapHeight);
        }
        if (mDestRect == null) {
            mDestRect = new Rect(0, 0, mBitmapWidth, mBitmapHeight);
        }
        canvas.drawBitmap(mBitmap, matrix, mPaint);
//        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        Log.i(TAG, "onSizeChanged " + " width: " + w + " height: " + h);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "onMeasure: " + " width: " + width + " height: " + height);

    }

    @Override
    public void setImageMatrix(Matrix matrix) {
        Log.i(TAG, "setMatrix before");
        this.matrix.set(matrix);
        Log.i(TAG, "setMatrix middle");
        super.setImageMatrix(matrix);
        Log.i(TAG, "setMatrix after");

    }

    public void startTranslate(int destX, int destY, int duration) {
        Log.i(TAG, "startTranslate(int destX, int destY, int duration)");

        final int startX = mStartX;
        final int startY = mStartY;
        final int deltaX = destX - startX;
        final int deltaY = destY - startY;
        Log.i(TAG, "startX: " + startX + " startY: " + startY);

        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                int currentX = (int) (deltaX * fraction + startX);
                int currentY = (int) (deltaY * fraction + startY);
                if (mDestRect == null)
                    mDestRect = new Rect(currentX, currentY, currentX + mBitmapWidth, currentY + mBitmapHeight);
                else {
                    mDestRect.left = startX;
                    mDestRect.top = startY;
                    mDestRect.right = (int) (startX + (1 - fraction) * mBitmapWidth);
                    mDestRect.bottom = startY + mBitmapHeight;
                }
                postInvalidate();
                Log.i(TAG, "currentX: " + currentX + " currentY: " + currentY);
            }

        });
        valueAnimator.start();
        mStartX = destX;
        mStartY = destY;
    }

    public void startTranslate(int startX, int startY) {
        mStartX = startX;
        mStartY = startY;
        startTranslate(200, 200, 1000);
    }

    public void startTranslate() {
        startTranslate(200, 200, 1000);
    }

    public int getBitmapWidth() {
        return mBitmapWidth;
    }

    public int getBitmapHeight() {
        return mBitmapHeight;
    }
}
