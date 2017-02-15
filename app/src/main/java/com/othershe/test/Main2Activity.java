package com.othershe.test;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends Activity implements OnClickListener, View.OnTouchListener {
    private TextView tv_hi;
    private ImageView img_icon;
    private Button btn_scrollTo;
    private Button btn_scrollBy;
    private Button btn_reset;
    private TestScrollView mScrollView;
    private LinearLayout root;
    TransformMatrixView matrixView;

    private int mLastX;
    private int mLastY;

    private SeekBar mSeekBar;
    private LeafView leafView;


    private int LEAF_HEIGHT = 61;
    private int LEAF_WIDTH = 302;
    private int mLeafHeight;
    private int mLeafWidth;

    private Button mCheckBtn, mUncheckBtn;
    private CheckView checkView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        root = (LinearLayout) findViewById(R.id.root);
        root.setGravity(LinearLayout.HORIZONTAL);
        RuleView ruleView = new RuleView(Main2Activity.this);
        matrixView = new TransformMatrixView(Main2Activity.this);
        leafView = new LeafView(Main2Activity.this);
//        root.addView(ruleView,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        root.addView(matrixView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLeafWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LEAF_WIDTH, getResources().getDisplayMetrics());
        mLeafHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LEAF_HEIGHT, getResources().getDisplayMetrics());

//        root.addView(leafView, new LinearLayout.LayoutParams(mLeafWidth, mLeafHeight));
//        setContentView(matrixView);
//        matrixView.setOnTouchListener(this);
        mSeekBar = (SeekBar) findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        checkView = new CheckView(Main2Activity.this);
//        root.addView(checkView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mCheckBtn = (Button) findViewById(R.id.btn_check);
        mUncheckBtn = (Button) findViewById(R.id.btn_uncheck);
        mCheckBtn.setOnClickListener(this);
        mUncheckBtn.setOnClickListener(this);
//        SpiderView spiderView = new SpiderView(Main2Activity.this);
//        root.addView(spiderView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        BezierHeartView bezierView = new BezierHeartView(Main2Activity.this);
//         searchView=new SearchView(Main2Activity.this);
//        MatrixView1 matrixView = new MatrixView1(Main2Activity.this);
        BitmapShaderView bitmapShaderView = new BitmapShaderView(Main2Activity.this);
//        SloopView sloopView=new SloopView(Main2Activity.this);
//        root.addView(bitmapShaderView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            leafView.setProgress(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    private void initView() {
        tv_hi = (TextView) findViewById(R.id.tv_hi);
        img_icon = (ImageView) findViewById(R.id.img_icon);
        mScrollView = (TestScrollView) findViewById(R.id.mScrollView);
        btn_scrollTo = (Button) findViewById(R.id.btn_scrollTo);
        btn_scrollBy = (Button) findViewById(R.id.btn_scrollBy);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_scrollTo.setOnClickListener(this);
        btn_scrollBy.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        tv_hi.setOnClickListener(this);
        img_icon.setOnClickListener(this);
        mScrollView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scrollTo:
                img_icon.scrollTo(-20, -20);
                tv_hi.scrollTo(-20, -20);
                int x = tv_hi.getScrollX();
                int y = tv_hi.getScrollY();
                Log.i("123", "x: " + x + " y: " + y);
                mScrollView.scrollTo();
                break;
            case R.id.btn_scrollBy:
                img_icon.scrollBy(-20, -20);
                tv_hi.scrollBy(-20, -20);
                mScrollView.scrollBy();
                break;
            case R.id.btn_reset:
                img_icon.scrollTo(0, 0);
                tv_hi.scrollTo(0, 0);
                mScrollView.scrollReset();
                break;
            case R.id.tv_hi:
                Toast.makeText(Main2Activity.this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_icon:
                Toast.makeText(Main2Activity.this, "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mScrollView:
                Toast.makeText(Main2Activity.this, "3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_check:
                checkView.setCheck();
                searchView.start();
                break;
            case R.id.btn_uncheck:
                checkView.setUncheck();
                searchView.end();
                break;
            default:
                break;
        }
    }

    Matrix matrix = new Matrix();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
//            matrix = setTranslateMatrix(matrix, event);
            setRotate(matrix, event);
//            setScale(matrix);
            printMatrix(matrix);
//            setSymmetryX(matrix);
            matrixView.invalidate();
        }
        return true;
    }


    private void printMatrix(Matrix matrix) {
        float[] array = new float[9];
        matrix.getValues(array);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                float value = array[i * 3 + j];
                Log.i(TransformMatrixView.class.getSimpleName(), "value: " + value);
            }
        }
    }

    private Matrix setTranslateMatrix(Matrix matrix, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        Log.i(TransformMatrixView.class.getSimpleName(), "x: " + x + " y: " + y);
        matrix.reset();
        float[] array = {1, 0, 0, 0, 1, 0, 0, 0, 1};
        matrix.setValues(array);
        matrix.setTranslate(x, y);
        matrixView.setImageMatrix(matrix);
        return matrix;
    }

    private int i = 0;

    private Matrix setRotate(Matrix matrix, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        matrix.reset();
        matrix.postTranslate(x - matrixView.getBitmapWidth() / 2, y - matrixView.getBitmapHeight() / 2);
        matrix.postRotate(15f * (i++), x, y);
        matrixView.setImageMatrix(matrix);
        Log.i("TransformMatrixView", "setRotate x: " + x + " y: " + y);
        return matrix;
    }


    private Matrix setScale(Matrix matrix) {
        matrix.setScale(2f, 2f);
        matrixView.setImageMatrix(matrix);
        return matrix;
    }

    private void setSymmetryX(Matrix matrix) {
        float[] array = {0, -1, 0, -1, 0, 0, 0, 0, 1};
        matrix.setValues(array);
//        matrix.postTranslate(0, matrixView.getBitmapHeight() * 1);
        matrixView.setImageMatrix(matrix);
    }
}
