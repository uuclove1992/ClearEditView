package com.othershe.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Main3Activity extends Activity implements OnClickListener {

    private TextView tv;
    private Button bt_scrollLeft;
    private Button bt_scrollRight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main3);

        tv = (TextView) findViewById(R.id.tv_scroll);

        bt_scrollLeft = (Button) findViewById(R.id.bt_scrollLeft);
        bt_scrollRight = (Button) findViewById(R.id.bt_scrollRight);

        bt_scrollLeft.setOnClickListener(this);
        bt_scrollRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        ViewGroup.MarginLayoutParams params=(ViewGroup.MarginLayoutParams) tv.getLayoutParams();
        switch (v.getId()) {
            case R.id.bt_scrollLeft:
//                tv.scrollBy(20, 0);
                params.width+=20;
                params.leftMargin-=20;
                tv.requestLayout();
                int tvscrllX = tv.getScrollX();
                int tvscrllY = tv.getScrollY();
                System.out.println( " tvscrllX ---> " + tvscrllX + " --- tvscrllY ---> "+tvscrllY);
                bt_scrollLeft.scrollBy(20, 0);
                break;
            case R.id.bt_scrollRight:
//                tv.scrollTo(-100, 0);
                params.width+=20;
                params.leftMargin+=20;
                tv.requestLayout();
                int tvscrllXx = tv.getScrollX();
                int tvscrllYx = tv.getScrollY();
                System.out.println( " tvscrllX ---> " + tvscrllXx + " --- tvscrllY ---> "+tvscrllYx);
                break;
        }

    }

}
