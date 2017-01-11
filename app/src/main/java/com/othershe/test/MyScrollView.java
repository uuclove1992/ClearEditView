package com.othershe.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * TODO: document your custom view class.
 */
public class MyScrollView extends ScrollView {
    private String TAG = MyScrollView.class.getSimpleName();
    private int columns;

    public MyScrollView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyScrollView, defStyle, 0);

        columns = typedArray.getInteger(R.styleable.MyScrollView_columns, 1);

        typedArray.recycle();
        initViews(columns);
    }


    private void initViews(int columns) {
        LinearLayout linearLayout=new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(new AutoCardLayout(getContext(),columns));
        addView(linearLayout,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


}
