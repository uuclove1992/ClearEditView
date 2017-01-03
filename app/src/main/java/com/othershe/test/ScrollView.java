package com.othershe.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * TODO: document your custom view class.
 */
public class ScrollView extends RelativeLayout {
    private static final String TAG = ScrollView.class.getSimpleName();
    private ImageView mImageView;

    public ScrollView(Context context) {
        super(context);
        init(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {
        // Load attributes
        mImageView = new ImageView(ctx);
        mImageView.setImageResource(R.drawable.phone);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mImageView.setScaleType(ImageView.ScaleType.CENTER);
        addView(mImageView, params);
    }

    public void scrollTo() {
//        mImageView.scrollTo(-20,-20);
        scrollTo(-20, -20);
    }

    public void scrollBy() {
//        mImageView.scrollBy(-20,-20);
        scrollBy(-20, -20);

    }


    public void scrollReset() {
//        mImageView.scrollTo(0,0);
        scrollTo(0, 0);
    }

    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        Log.i(TAG,"X->"+x+" "+"Y->"+y);
        int x1 = (int) event.getX();
        int y1 = (int) event.getY();
        Log.i(TAG,"X1->"+x1+" "+"Y1->"+y1);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"*****************************MotionEvent.ACTION_DOWN");

                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG,"*****************************MotionEvent.ACTION_UP");

                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,"*****************************MotionEvent.ACTION_MOVE");

                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.i(TAG,"deltaX->"+deltaX+" "+"deltaY->"+deltaY);

                int translationX = (int) ViewHelper.getTranslationX(mImageView) + deltaX;
                int translationY = (int) ViewHelper.getTranslationY(mImageView) + deltaY;
                Log.i(TAG,"translationX->"+translationX+" "+"translationY->"+translationY);

                ViewHelper.setTranslationX(mImageView, translationX);
                ViewHelper.setTranslationY(mImageView, translationY);
                break;
        }
        mLastX = x;
        mLastY = y;
        Log.i(TAG,"mLastX->"+mLastX+" "+"mLastY->"+mLastY);

        return true;
    }
}
