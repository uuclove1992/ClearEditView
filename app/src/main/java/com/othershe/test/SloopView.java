package com.othershe.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class SloopView extends View {
    private String TAG = SloopView.class.getSimpleName();
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;

    public SloopView(Context context) {
        super(context);
        init(null, 0);
    }

    public SloopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SloopView(Context context, AttributeSet attrs, int defStyle) {
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

//    private Bitmap drawableToBitmap() {
//        Drawable drawable = getDrawable();
//        Bitmap bitmap = Bitmap
//                .createBitmap(
//                        drawable.getIntrinsicWidth(),
//                        drawable.getIntrinsicHeight(),
//                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                                : Bitmap.Config.RGB_565);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
//        canvas.translate(mWidth / 2, mHeight / 2);
//        int min = Math.min(mWidth, mHeight);
//        mBitmap = Bitmap.createScaledBitmap(mBitmap, min, min, false);
//        Bitmap bitmap = getBitmap(min);
//        canvas.drawBitmap(bitmap, 0, 0, null);

        canvas.drawColor(Color.RED);
        canvas.save();
//        canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.clipRect(0,0,500,500);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);
    }

    private Bitmap getBitmap(int min) {
        Paint paint = new Paint();
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
//        canvas.setBitmap(target);
        canvas.setBitmap(target);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        return target;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        int srcWidth = mBitmap.getWidth();
        int srcHeight = mBitmap.getHeight();

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int paddingWidth = getPaddingLeft() + getPaddingRight() + srcWidth;
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(widthSize, paddingWidth);
            } else
                width = paddingWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int paddingHeight = getPaddingBottom() + getPaddingTop() + srcHeight;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(heightSize, paddingHeight);
            } else
                height = paddingHeight;
        }

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
        Log.i(TAG, "widthSize: " + width);
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
        Log.i(TAG, "heightSize: " + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
}
