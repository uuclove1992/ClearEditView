package com.othershe.test;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TODO: document your custom view class.
 */
public class LeafView extends View {
    private String TAG = LeafView.class.getSimpleName();
    private Resources mResources;
    private int TOTAL_PROGRESS = 100;
    private int mProgress;
    private int mProgressWidth;
    private int mCurrentPosition;
    private int mArcRadius;

    private int mTotalWidth;
    private int mTotalHeight;

    private int LEFT_MARGIN = 9;
    private int RIGHT_MARGIN = 25;
    private int mLeftMargin;
    private int mRightMargin;

    private Paint mWhitePaint;
    private Paint mOrangePaint;

    private RectF mArcRect;
    private RectF mWhiteRect;
    private RectF mOrangeRect;

    private int MIDDLE_AMPLITUDE = 13;
    private int DIFF_AMPLITUDE = 5;
    private int middleAmplitue;
    private int diffAmplitude;

    private int LEAF_ROTATE_TIME = 2000;
    private int mLeafRotateTime = LEAF_ROTATE_TIME;
    private int LEAF_FLOAT_TIME = 3000;
    private int mLeafFloatTime = LEAF_FLOAT_TIME;

    private Random random;
    private LeafFactory mLeafFactory;

    private Bitmap mLeafBitmap;
    private int mLeafWidth;
    private int mLeafHeight;
    private Paint mLeafPaint;

    private Bitmap mOutBitmap;
    private int mOutBitmapWidth;
    private int mOutBitmapHeight;
    private Paint mOutPaint;
    private Rect mOuterSrcRect;
    private Rect mOuterDestRect;

    private Bitmap mFanBitmap;
    private int mFanWidth;
    private int mFanHeight;
    private Paint mFanPaint;
    private Rect mFanSrc;
    private Rect mFanDest;

    private List<Leaf> mLeafInfos;

    public LeafView(Context context) {
        super(context);
        init(context);
    }

    public LeafView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LeafView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        // Load attributes
        mResources = context.getResources();

        initPaint();

        initBitmap();

        random = new Random();
        mLeafFactory = new LeafFactory();
        mLeafInfos = mLeafFactory.generateLeafs();

    }

    private void initBitmap() {
        mLeafBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf)).getBitmap();
        mLeafWidth = mLeafBitmap.getWidth();
        mLeafHeight = mLeafBitmap.getHeight();

        mOutBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf_kuang)).getBitmap();
        mOutBitmapWidth = mOutBitmap.getWidth();
        mOutBitmapHeight = mOutBitmap.getHeight();


        mFanBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.fengshan)).getBitmap();
        mFanWidth = mFanBitmap.getWidth();
        mFanHeight = mFanBitmap.getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "mTotalWidth: " + w + " mTotalHeight: " + h);
        mTotalWidth = w;
        mTotalHeight = h;

        mLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LEFT_MARGIN, mResources.getDisplayMetrics());
        mRightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, RIGHT_MARGIN, mResources.getDisplayMetrics());

        mProgressWidth = mTotalWidth - mLeftMargin - mRightMargin;
        mArcRadius = (mTotalHeight - 2 * mLeftMargin) / 2;

        mArcRect = new RectF(mLeftMargin, mLeftMargin, mLeftMargin + 2 * mArcRadius, mTotalHeight - mLeftMargin);
        mWhiteRect = new RectF(mArcRadius + mLeftMargin, mLeftMargin, mTotalWidth - mRightMargin, mTotalHeight - mLeftMargin);
        mOrangeRect = new RectF(mArcRadius + mLeftMargin, mLeftMargin, mTotalWidth - mRightMargin, mTotalHeight - mLeftMargin);

//        middleAmplitue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MIDDLE_AMPLITUDE, mResources.getDisplayMetrics());
//        diffAmplitude = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DIFF_AMPLITUDE, mResources.getDisplayMetrics());
        middleAmplitue = MIDDLE_AMPLITUDE;
        diffAmplitude = DIFF_AMPLITUDE;
        mOuterSrcRect = new Rect(0, 0, mOutBitmapWidth, mOutBitmapHeight);
        mOuterDestRect = new Rect(0, 0, mTotalWidth, mTotalHeight);

        int halfHeight = mTotalHeight / 2;
        int halfFanHeight = mFanHeight / 2;
        mFanSrc = new Rect(0, 0, mFanWidth, mFanHeight);
        mFanDest = new Rect(mTotalWidth - mFanWidth - mLeftMargin * 2 / 3, (halfHeight - halfFanHeight), mTotalWidth - mLeftMargin * 2 / 3, mFanHeight + (halfHeight - halfFanHeight));
    }

    private static final int ORANGE_COLOR = 0xffffa800;
    private static final int WHITE_COLOR = 0xfffde399;

    private void initPaint() {
        mWhitePaint = new Paint();
        mWhitePaint.setColor(WHITE_COLOR);
        mWhitePaint.setAntiAlias(true);

        mOrangePaint = new Paint();
        mOrangePaint.setColor(ORANGE_COLOR);
        mWhitePaint.setAntiAlias(true);

        mLeafPaint = new Paint();
        mLeafPaint.setAntiAlias(true);
        mLeafPaint.setDither(true);
        mLeafPaint.setFilterBitmap(true);

        mOutPaint = mOrangePaint;
        mFanPaint = mOrangePaint;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        drawProgress(canvas);
        drawOuter(canvas);
        drawFan(canvas);
    }


    private void drawProgress(Canvas canvas) {
        mCurrentPosition = (mProgressWidth + mArcRadius) * mProgress / TOTAL_PROGRESS;
        if (mCurrentPosition < mArcRadius) {
            //1.绘制白色椭圆
            canvas.drawArc(mArcRect, 90, 180, true, mWhitePaint);
            //3.绘制白色矩形
            canvas.drawRect(mWhiteRect, mWhitePaint);
            drawLeafs(canvas);
            //2.绘制橘色椭圆
            int angel = (int) Math.toDegrees(Math.acos((mArcRadius - mCurrentPosition) / (float) mArcRadius));
            canvas.drawArc(mArcRect, 180 - angel, 2 * angel, false, mOrangePaint);

        } else {
            //2.绘制白色矩形
            canvas.drawRect(mWhiteRect, mWhitePaint);
            drawLeafs(canvas);
            //1.绘制橘色椭圆
            canvas.drawArc(mArcRect, 90, 180, true, mOrangePaint);
            //3.绘制橘色矩形
            mOrangeRect.right = mCurrentPosition;
            canvas.drawRect(mOrangeRect, mOrangePaint);
        }
    }


    private void drawOuter(Canvas canvas) {
        canvas.drawBitmap(mOutBitmap, mOuterSrcRect, mOuterDestRect, mOutPaint);
    }

    private void drawFan(Canvas canvas) {
        canvas.drawBitmap(mFanBitmap, mFanSrc, mFanDest, mFanPaint);
    }


    private void drawLeafs(Canvas canvas, int z) {
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < mLeafInfos.size(); i++) {
            Leaf leaf = mLeafInfos.get(i);
            if (currentTime > leaf.startTime && leaf.startTime != 0) {
                // 绘制叶子－－根据叶子的类型和当前时间得出叶子的（x，y）
                getLeafLocation(leaf, currentTime);
                // 根据时间计算旋转角度
                canvas.save();
                // 通过Matrix控制叶子旋转
                Matrix matrix = new Matrix();
                float transX = leaf.x;
                float transY = leaf.y + mTotalHeight / 2;
                Log.i(TAG, "left.x = " + leaf.x + "--leaf.y=" + leaf.y);
                matrix.postTranslate(transX, transY);
                // 通过时间关联旋转角度，则可以直接通过修改LEAF_ROTATE_TIME调节叶子旋转快慢
                float rotateFraction = ((currentTime - leaf.startTime) % LEAF_ROTATE_TIME)
                        / (float) LEAF_ROTATE_TIME;
                int angle = (int) (rotateFraction * 360);
                // 根据叶子旋转方向确定叶子旋转角度
                int rotate = leaf.direction == 0 ? angle + leaf.startAngel : -angle
                        + leaf.startAngel;
                matrix.postRotate(rotate, transX
                        + mLeafWidth / 2, transY + mLeafHeight / 2);
                canvas.drawBitmap(mLeafBitmap, matrix, mLeafPaint);
                canvas.restore();
            } else {
                continue;
            }
        }
    }


    private void drawLeafs(Canvas canvas) {
        int leafAmount = mLeafInfos.size();
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < leafAmount; i++) {
            Leaf leaf = mLeafInfos.get(i);
            long startTime = leaf.startTime;
            if (currentTime > startTime) {
                getLeafLocation(leaf, currentTime);
                float x = leaf.x + mLeftMargin;
                float y = leaf.y + mLeftMargin;
                Matrix matrix = new Matrix();
                matrix.postTranslate(x, y);
                float rotateFraction = (float) ((currentTime - leaf.startTime) % mLeafRotateTime) / (float) mLeafRotateTime;
                int rotate = (int) (rotateFraction * 360);
                int direction = leaf.direction;
                Log.i(TAG, "direction: " + direction + " rotate: " + rotate);
                if (direction == 0)
                    matrix.postRotate(leaf.startAngel + rotate, x + mLeafWidth / 2, y + mLeafHeight / 2);
                else
                    matrix.postRotate(leaf.startAngel - rotate, x + mLeafWidth / 2, y + mLeafHeight / 2);
                canvas.drawBitmap(mLeafBitmap, matrix, mLeafPaint);
            }
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    private float getLeafY(Leaf leaf) {
        float x = leaf.x;
        StartType type = leaf.startType;
        int amplitude = middleAmplitue;
        switch (type) {
            case Little:
                amplitude = middleAmplitue - diffAmplitude;
                break;
            case Middle:
                break;
            case High:
                amplitude = middleAmplitue + diffAmplitude;
                break;
        }
        float w = (float) (2 * Math.PI / mProgressWidth);
        return (float) (amplitude * Math.sin(w * x) + mArcRadius * 2 / 3);

    }

    private void getLeafLocation(Leaf leaf, long currentTime) {
        long startTime = leaf.startTime;
        long interval = currentTime - startTime;
        if (interval < 0)
            return;
        if (interval < mLeafFloatTime) {
            float fraction = interval / (float) mLeafFloatTime;
            float x = (mProgressWidth) * (1 - fraction);
            leaf.x = x;
            leaf.y = getLeafY(leaf);
        } else {
            leaf.startTime = System.currentTimeMillis() + random.nextInt(mLeafFloatTime);
        }

    }

    class LeafFactory {
        private final int MAX_LEAFS = 10;
        private Random random = new Random();
        private int mAddTime;

        public Leaf generateLeaf() {
            Leaf leaf = new Leaf();
            StartType type = StartType.Middle;
            int randomType = random.nextInt(3);
            switch (randomType) {
                case 0:
                    break;
                case 1:
                    type = StartType.Little;
                    break;
                case 2:
                    type = StartType.High;
                    break;
            }
            leaf.startType = type;
            leaf.startAngel = random.nextInt(360);
            leaf.direction = random.nextInt(2);
            long currentTime = System.currentTimeMillis();
            mAddTime += random.nextInt((int) (1.5 * mLeafFloatTime));
            leaf.startTime = currentTime + mAddTime;
            return leaf;
        }

        public List<Leaf> generateLeafs() {
            return generateLeafs(MAX_LEAFS);
        }

        public List<Leaf> generateLeafs(int count) {
            List<Leaf> leafs = new ArrayList<>();
            for (int i = 0; i < count; i++)
                leafs.add(generateLeaf());
            return leafs;
        }
    }

}


class Leaf {
    float x, y;//位置
    StartType startType;//振幅
    int startAngel;//起始角度
    int rotateAngel;//旋转角度
    int direction;//旋转方向
    long startTime;//起始时间

}


enum StartType {
    Little, Middle, High;
}


