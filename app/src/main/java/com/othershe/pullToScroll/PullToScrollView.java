package com.othershe.pullToScroll;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * PullToScrollView
 *
 * @author baidu
 * @since 2017/2/21
 */
public class PullToScrollView extends ScrollView {
    private String TAG = PullToScrollView.class.getSimpleName();
    private View mContentView;
    private View mHeaderView;
    private Rect mHeaderNormalRect;
    private Rect mContentNormalRect;

    private Point mInitPoint;
    private int mCurY;

    public PullToScrollView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    public PullToScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        mContentView = getChildAt(0);
        super.onFinishInflate();
    }

    private boolean isMoved;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent()");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (mContentNormalRect == null)
                mContentNormalRect = new Rect(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
            if (mHeaderNormalRect == null)
                mHeaderNormalRect = new Rect(mHeaderView.getLeft(), mHeaderView.getTop(), mHeaderView.getRight(), mHeaderView.getBottom());
            mInitPoint = new Point((int) ev.getX(), (int) ev.getY());
            isMoved = false;
        }
        if (action == MotionEvent.ACTION_MOVE) {
            mCurY = (int) ev.getY();
            int diff = (mCurY - mInitPoint.y) > mHeaderView.getHeight() ? mHeaderView.getHeight() : (mCurY - mInitPoint.y);
            if (diff > 0 && diff > getScrollY()) {
                onTouchEvent(ev);
                return true;
            }

        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onInterceptTouchEvent()： " + super.onInterceptTouchEvent(ev) + " ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent()： " + super.onInterceptTouchEvent(ev) + " ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onInterceptTouchEvent()： " + super.onInterceptTouchEvent(ev) + " ACTION_UP");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onTouchEvent()");
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mContentNormalRect == null)
                    mContentNormalRect = new Rect(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
                if (mHeaderNormalRect == null)
                    mHeaderNormalRect = new Rect(mHeaderView.getLeft(), mHeaderView.getTop(), mHeaderView.getRight(), mHeaderView.getBottom());
                mInitPoint = new Point((int) ev.getX(), (int) ev.getY());
                isMoved = false;

                Log.i(TAG, "ACTION_DOWN: mContentView" + mContentView.getLeft() + " " + mContentView.getTop() + " " + mContentView.getRight() + " " + mContentView.getBottom());
                Log.i(TAG, "ACTION_DOWN: mHeaderView" + mHeaderView.getLeft() + " " + mHeaderView.getTop() + " " + mHeaderView.getRight() + " " + mHeaderView.getBottom());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                mCurY = (int) ev.getY();
                int diff = (mCurY - mInitPoint.y) > mHeaderView.getHeight() ? mHeaderView.getHeight() : (mCurY - mInitPoint.y);
                if (diff > 0 && diff >= getScrollY()) {
                    int headerDiff = (int) (diff * 0.5);
                    int contentDiff = diff;
                    if (mContentNormalRect.top + contentDiff <= mHeaderNormalRect.bottom + headerDiff) {
                        mHeaderView.layout(mHeaderNormalRect.left, mHeaderNormalRect.top + headerDiff, mHeaderNormalRect.right, mHeaderNormalRect.bottom + headerDiff);
                        mContentView.layout(mContentNormalRect.left, mContentNormalRect.top + contentDiff, mContentNormalRect.right, mContentNormalRect.bottom + contentDiff);
                        isMoved = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");
                if (isMoved) {
                    int contentTop = mContentView.getTop();
                    int headerTop = mHeaderView.getTop();
                    mContentView.layout(mContentNormalRect.left, mContentNormalRect.top, mContentNormalRect.right, mContentNormalRect.bottom);
                    mHeaderView.layout(mHeaderNormalRect.left, mHeaderNormalRect.top, mHeaderNormalRect.right, mHeaderNormalRect.bottom);
                    TranslateAnimation contentAnimation = new TranslateAnimation(0, 0, contentTop - mContentNormalRect.top, 0);
                    contentAnimation.setDuration(200);
                    mContentView.startAnimation(contentAnimation);

                    TranslateAnimation headerAnimation = new TranslateAnimation(0, 0, headerTop - mHeaderNormalRect.top, 0);
                    headerAnimation.setDuration(200);
                    mHeaderView.startAnimation(headerAnimation);
                    isMoved = false;
                }
                break;

        }
        return super.onTouchEvent(ev);

    }

    public void setHeaderView(View view) {
        mHeaderView = view;
    }
}
