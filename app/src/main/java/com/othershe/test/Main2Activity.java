package com.othershe.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends Activity implements OnClickListener {
    private TextView tv_hi;
    private ImageView img_icon;
    private Button btn_scrollTo;
    private Button btn_scrollBy;
    private Button btn_reset;
    private TestScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
//        initView();
    }

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
            default:
                break;
        }
    }
}
