package com.clark.stepviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaodanyang on 2017/6/7.
 */

public class StepViewIndicator extends View {

    private int width;
    private int height;

    private Paint mUnCompletedPaint;//未完成Paint  definition mUnCompletedPaint
    private Paint mCompletedPaint;//完成paint      definition mCompletedPaint

    private int unCompletedRadius = 15; //未完成的图标的半径
    private int completedRadius = 20; //已完成的图标的半径

    private Drawable mCompleteIcon;//完成的默认图片    definition default completed icon
    private Drawable mUnCompleteIcon;//未完成的默认图片     definition default underway icon

    private int centerY;

    private List<Integer> poiList;

    //总共有多少步骤
    private int mStepSum = 4;
    //当前进行到哪一步了，从0开始
    private int mCurrentStep = 2;

    private OnDrawIndicatorListener onDrawIndicatorListener;

    public StepViewIndicator(Context context) {
        super(context);
        init();
    }

    public StepViewIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepViewIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 设置步数
     *
     * @param stepSum     总共有多少步
     * @param currentStep 当前进行到的步数
     */
    public void setStep(int stepSum, int currentStep) {
        this.mStepSum = stepSum;
        this.mCurrentStep = currentStep;
        invalidate();
    }

    private void init() {
        poiList = new ArrayList<>();

        //初始化未完成步骤的画笔，用来画未完成的步骤的矩形
        mUnCompletedPaint = new Paint();
        //设置抗锯齿
        mUnCompletedPaint.setAntiAlias(true);
        //设置画笔样式：画出来的矩形是实心的
        mUnCompletedPaint.setStyle(Paint.Style.FILL);
        //设置画笔样式：画出来的矩形是空心的
//        mUnCompletedPaint.setStyle(Paint.Style.STROKE);
//        mUnCompletedPaint.setStrokeWidth(1);
        //设置画笔的颜色
        mUnCompletedPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark));

        //初始化已完成步骤的画笔，用来画已完成的步骤的矩形
        mCompletedPaint = new Paint();
        //设置抗锯齿
        mCompletedPaint.setAntiAlias(true);
        //设置画笔样式：画出来的矩形是实心的
        mCompletedPaint.setStyle(Paint.Style.FILL);
        //设置画笔样式：画出来的矩形是空心的
//        mCompletedPaint.setStyle(Paint.Style.STROKE);
//        mCompletedPaint.setStrokeWidth(1);
        //设置画笔的颜色
        mCompletedPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light));


        //设置未完成的步骤的icon
        mUnCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.progress_uncompleted);
        //设置已完成的步骤的icon
        mCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.progress_completed);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //测量StepView的宽度
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }

        //测量StepView的高度
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            //高度取已完成icon和未完成icon的高度的最大值
            height = (int) Math.max(unCompletedRadius * 2, completedRadius * 2);
        }
        //将测量的宽高设置给控件
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("haha", "onSizeChanged()：----------");
        centerY = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //用来记录点的坐标圆心，用来确定每一步骤的下方的文字描述的位置
        poiList.clear();

        //根据步骤的数量，将StepView的宽度切分为8段。
        int sectionWidth = width / (mStepSum * 2);

        //根据步数进行画线（其实是矩形），所要画的线的数量比总共的步数要少1。
        for (int i = 0; i <= mStepSum - 1; i++) {

            //当前步骤的坐标圆心
            int circleCenter = sectionWidth + i * sectionWidth * 2;

            //添加到中心的坐标，这个是用来给绘制底部文字用的坐标位置
            poiList.add(circleCenter);

            //下个步骤的坐标圆心
            int nextCircleCenter = circleCenter + sectionWidth * 2;

            //线顶部的位置
            int top = centerY - 1;
            //线底部的位置
            int bottom = centerY + 1;

            if (i < mCurrentStep) {
                //所要画的步骤小于当前步骤，画已完成的icon
                Rect rect = new Rect(circleCenter - completedRadius, centerY - completedRadius, circleCenter + completedRadius, centerY + completedRadius);
                mCompleteIcon.setBounds(rect);
                mCompleteIcon.draw(canvas);
                //如果当前所要画的步骤小于当前处于的步骤，表示是已完成的步骤
                canvas.drawRect(circleCenter + completedRadius, top, nextCircleCenter - completedRadius, bottom, mCompletedPaint);
            } else if (i == mCurrentStep) {
                //所要画的步骤正好是当前步骤，画已完成的icon
                Rect rect = new Rect(circleCenter - completedRadius, centerY - completedRadius, circleCenter + completedRadius, centerY + completedRadius);
                mCompleteIcon.setBounds(rect);
                mCompleteIcon.draw(canvas);
                //当前所要画的步骤正好是当前处于的步骤，表示是进行中的状态
                canvas.drawRect(circleCenter + completedRadius, top, nextCircleCenter - unCompletedRadius, bottom, mCompletedPaint);
            } else {
                //所要画的步骤大于当前步骤，画未完成的icon。
                Rect rect = new Rect(circleCenter - unCompletedRadius, centerY - unCompletedRadius, circleCenter + unCompletedRadius, centerY + unCompletedRadius);
                mUnCompleteIcon.setBounds(rect);
                mUnCompleteIcon.draw(canvas);
                //如果是最后一步了，则不需要画右边的线了
                if (i != mStepSum - 1) {
                    //当前所要画的步骤是未完成的步骤
                    canvas.drawRect(circleCenter + unCompletedRadius, top, nextCircleCenter - unCompletedRadius, bottom, mUnCompletedPaint);
                }
            }
        }

        //绘制完线和icon之后，通知需要绘制底部文字表述了
        if (onDrawIndicatorListener != null) {
            onDrawIndicatorListener.ondrawIndicator(poiList);
        }
    }


    /**
     * 设置对view监听
     */
    public interface OnDrawIndicatorListener {
        void ondrawIndicator(List<Integer> list);
    }

    public void setOnDrawIndicatorListener(OnDrawIndicatorListener onDrawIndicatorListener) {
        this.onDrawIndicatorListener = onDrawIndicatorListener;
    }
}
