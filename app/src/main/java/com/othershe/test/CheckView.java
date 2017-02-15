package com.othershe.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class CheckView extends View {
    private String TAG = CheckView.class.getSimpleName();
    private Paint mPaint;

    private int mTotalWidth;
    private int mTotalHeight;

    private Bitmap mCheckBitmap;
    private int mCheckWidth;
    private int mCheckHeight;

    private boolean isCheck;

    private int ANIM_CHECK = 0;
    private int ANIM_UNCHECK = 1;
    private int ANIM_NONE = 2;
    private int mAnimType = ANIM_NONE;

    private Rect mSrcRect;
    private Rect mDestRect;

    private int mCurrentPage;
    private int PAGE_NUM = 13;
    private int mAllPage = PAGE_NUM;

    private int mDuration = 100;

    public CheckView(Context context) {
        super(context);
        init(context);
    }

    public CheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        // Load attributes
        initPaint();

        mCheckBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.checkmark);
        mCheckWidth = mCheckBitmap.getWidth();
        mCheckHeight = mCheckBitmap.getHeight();

        mSrcRect = new Rect();
        mDestRect = new Rect();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.FILL);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if (mCurrentPage >= 0 && mCurrentPage < mAllPage) {
                invalidate();
                if (mAnimType == ANIM_NONE) {
                    return;
                } else if (mAnimType == ANIM_CHECK) {
                    mCurrentPage++;
                } else {
                    mCurrentPage--;
                }
            } else {
                if (mAnimType == ANIM_CHECK)
                    mCurrentPage = mAllPage - 1;
                else if (mAnimType == ANIM_UNCHECK)
                    mCurrentPage = 0;
                if (mAnimType == ANIM_NONE)
                    return;
                mAnimType = ANIM_NONE;
                invalidate();
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce

        canvas.translate(mTotalWidth / 2, mTotalHeight / 2);
        canvas.drawCircle(0, 0, 300, mPaint);

        mSrcRect.left = mCurrentPage * mCheckHeight;
        mSrcRect.right = (mCurrentPage + 1) * mCheckHeight;
        mSrcRect.top = 0;
        mSrcRect.bottom = mCheckHeight;

        mDestRect.left = -mCheckHeight / 2;
        mDestRect.right = mCheckHeight / 2;
        mDestRect.top = -mCheckHeight / 2;
        mDestRect.bottom = mCheckHeight / 2;

        canvas.drawBitmap(mCheckBitmap, mSrcRect, mDestRect, mPaint);
        mHandler.sendEmptyMessageDelayed(0, mDuration / mAllPage);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalHeight = h;
        mTotalWidth = w;
    }


    public void setCheck() {
        if (ANIM_CHECK == mAnimType)
            return;
        mAnimType = ANIM_CHECK;
        mCurrentPage = 0;
        mHandler.sendEmptyMessageDelayed(0, mDuration / mAllPage);
    }

    public void setUncheck() {
        if (ANIM_UNCHECK == mAnimType)
            return;
        mAnimType = ANIM_UNCHECK;
        mCurrentPage = mAllPage - 1;
        mHandler.sendEmptyMessageDelayed(0, mDuration / mAllPage);
    }
}
