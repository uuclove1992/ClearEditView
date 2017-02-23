package com.othershe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.othershe.pullToScroll.PullToScrollView;
import com.othershe.test.R;

public class Main4Activity extends AppCompatActivity implements OnClickListener {

    private TableLayout mTableLayout;
    private RelativeLayout root;
    private PullToScrollView mPullToScrollView;
    private ImageView mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        root = (RelativeLayout) findViewById(R.id.root);
        mHeaderView = (ImageView) findViewById(R.id.img);
        mPullToScrollView = (PullToScrollView) findViewById(R.id.pullToScrollView);
        mTableLayout = (TableLayout) findViewById(R.id.tableLayout);
        mPullToScrollView.setHeaderView(mHeaderView);
        showTable();

    }

    private void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.leftMargin = 30;
        layoutParams.topMargin = 10;
        layoutParams.bottomMargin = 10;
        for (int i = 0; i < 20; i++) {
            TextView textView = new TextView(Main4Activity.this);
            textView.setText("123");
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(30, 30, 30, 30);
            TableRow tableRow = new TableRow(Main4Activity.this);
            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }
            tableRow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Main4Activity.this, "yeah", 1000).show();
                }
            });
            mTableLayout.addView(tableRow);
        }
    }


    @Override
    public void onClick(View v) {

    }
}
