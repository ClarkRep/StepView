package com.clark.stepviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements StepView.OnDrawIndicatorListener {

    private RelativeLayout rlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlText = (RelativeLayout) findViewById(R.id.rl_text);
        StepView stepView = (StepView) findViewById(R.id.step);
        stepView.setOnDrawIndicatorListener(this);

    }

    @Override
    public void ondrawIndicator(List<Integer> list) {
        if (rlText != null) {
            rlText.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                TextView mTextView = new TextView(MainActivity.this);
                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                if (i == 0) {
                    mTextView.setText("测试长度是否超过了" + i);
                } else {
                    mTextView.setText("测试" + i);
                }
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                mTextView.measure(spec, spec);

                // getMeasuredWidth
                int measuredWidth = mTextView.getMeasuredWidth();
                //保证文字过长的话，不会超过左面屏幕的距离
                if (list.get(i) - measuredWidth / 2 < 0) {
                    mTextView.setX(0);
                } else {
                    mTextView.setX(list.get(i) - measuredWidth / 2);
                }
                mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                rlText.addView(mTextView);
            }
        }

    }
}
