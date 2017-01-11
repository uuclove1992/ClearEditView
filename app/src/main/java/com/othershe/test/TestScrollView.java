package com.othershe.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * TODO: document your custom view class.
 */
public class TestScrollView extends RelativeLayout {
    private static final String TAG = TestScrollView.class.getSimpleName();
    private ImageView mImageView;
    int x;
    int y;

    public TestScrollView(Context context) {
        super(context);
        init(context);
    }

    public TestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {
        // Load attributes
        mImageView = new ImageView(ctx);
        mImageView.setImageResource(R.drawable.phone);
        ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);

        mp.setMargins(100, 100, 0,0);
        mImageView.setScaleType(ImageView.ScaleType.CENTER);
//        addView(mImageView, params);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mp);
        mImageView.setLayoutParams(params);
        addView(mImageView);
        x = mImageView.getScrollX();
        y = mImageView.getScrollY();
        Log.i(TAG, "x: " + x + " y: " + y);
    }

    public void scrollTo() {
//        mImageView.scrollTo(-20,-20);
        mImageView.scrollTo(-20, -20);
        x = mImageView.getScrollX();
        y = mImageView.getScrollY();
        Log.i(TAG, "x: " + x + " y: " + y);

    }

    public void scrollBy() {
//        mImageView.scrollBy(-20,-20);
        mImageView.scrollBy(-20, -20);
        x = mImageView.getScrollX();
        y = mImageView.getScrollY();
        Log.i(TAG, "x: " + x + " y: " + y);


    }


    public void scrollReset() {
//        mImageView.scrollTo(0,0);
        mImageView.scrollTo(0, 0);
        x = mImageView.getScrollX();
        y = mImageView.getScrollY();
        Log.i(TAG, "x: " + x + " y: " + y);

    }

//    private int mLastX;
//    private int mLastY;
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getRawX();
//        int y = (int) event.getRawY();
//        Log.i(TAG, "X->" + x + " " + "Y->" + y);
//        int x1 = (int) event.getX();
//        int y1 = (int) event.getY();
//        Log.i(TAG, "X1->" + x1 + " " + "Y1->" + y1);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.i(TAG, "*****************************MotionEvent.ACTION_DOWN");
//
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.i(TAG, "*****************************MotionEvent.ACTION_UP");
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i(TAG, "*****************************MotionEvent.ACTION_MOVE");
//
//                int deltaX = x - mLastX;
//                int deltaY = y - mLastY;
//                Log.i(TAG, "deltaX->" + deltaX + " " + "deltaY->" + deltaY);
//
//                int translationX = (int) ViewHelper.getTranslationX(mImageView) + deltaX;
//                int translationY = (int) ViewHelper.getTranslationY(mImageView) + deltaY;
//                Log.i(TAG, "translationX->" + translationX + " " + "translationY->" + translationY);
//
//                ViewHelper.setTranslationX(mImageView, translationX);
//                ViewHelper.setTranslationY(mImageView, translationY);
//                break;
//        }
//        mLastX = x;
//        mLastY = y;
//        Log.i(TAG, "mLastX->" + mLastX + " " + "mLastY->" + mLastY);
//
//        //qe4rfasdgbhfdsbhsbd
//        return true;
//    }
}

