package com.clark.stepviewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhaodanyang on 2017/8/23.
 */

public class StepView extends LinearLayout implements StepViewIndicator.OnDrawIndicatorListener {

    private StepViewIndicator mStepViewIndicator;
    private RelativeLayout mTextLayout;

    private Context mContext;

    private List<String> mStepList;

    private int mCurrentStep;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_stepsview, this);
        mStepViewIndicator = (StepViewIndicator) rootView.findViewById(R.id.step);
        mTextLayout = (RelativeLayout) rootView.findViewById(R.id.rl_text);
        mStepViewIndicator.setOnDrawIndicatorListener(this);
    }

    public void setStepList(List<String> list, int currentStep) {
        this.mStepList = list;
        this.mCurrentStep = currentStep;
        mStepViewIndicator.setStep(list.size(), currentStep);
    }

    @Override
    public void ondrawIndicator(List<Integer> list) {
        if (mTextLayout != null) {
            mTextLayout.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                TextView mTextView = new TextView(mContext);
                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mTextView.setText(mStepList.get(i));
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

                mTextLayout.addView(mTextView);
            }
        }
    }
}
