package com.xiaoanjujia.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sxjs.common.R;


/**
 * Created on 2020-02-24.<br/>
 * Author: M_DaLing<br/>
 * Email: Null_1024@126.com<br/>
 * Development tools: Android studio<br/>
 * Introduce: 加载圆环控件
 */
public class LoadingView extends View implements Runnable {

    /**
     * 圆环宽度
     */
    private float mRingWidth;
    /**
     * 是否自动开始
     */
    private boolean isAutoStart;

    /**
     * View默认最小宽度
     */
    private static final int DEFAULT_MIN_WIDTH = 100;

    /**
     * 控件宽
     */
    private int mViewWidth;
    /**
     * 控件高
     */
    private int mViewHeight;
    /**
     * 间隔时间
     */
    private static final int INTERVAL_TIME = 15;
    /**
     * 总旋转角度
     */
    private static final int TOTAL_ROTATION_ANGLE = 360;
    /**
     * 当前旋转到的角度
     */
    private int mCurrentAngle = 1;
    /**
     * 半径
     */
    private float mRadius;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 圆环位置
     */
    private RectF mCircleRect;

    /**
     * 环形颜色
     */
    private int ringColor;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        initAttr(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ringColor);
        mPaint.setStrokeWidth(mRingWidth);
        mPaint.setAntiAlias(true);

    }


    /**
     * 初始化自定义属性
     */
    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RingLoadingView, defStyleAttr, 0);
        mRingWidth = array.getDimensionPixelSize(R.styleable.RingLoadingView_rlv_ring_width, dip2px(context, 2f));
        isAutoStart = array.getBoolean(R.styleable.RingLoadingView_rlv_auto_start, true);
        ringColor = array.getColor(R.styleable.RingLoadingView_ringColor, Color.WHITE);
        array.recycle();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        //圆环半径，取宽、高中最小值
        mRadius = (Math.min(mViewWidth, mViewHeight) / 2f) * 0.8f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将画布中心移动到中心点
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        //旋转画布，让圆环旋转起来
        canvas.rotate(mCurrentAngle, 0, 0);
        //画渐变圆环
        if (mCircleRect == null) {
            //圆环位置，因为坐标系移动到控件中心，所以left、top都要为负值
            mCircleRect = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        }
        canvas.drawArc(mCircleRect, 0, 270, false, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(handleMeasure(widthMeasureSpec), handleMeasure(heightMeasureSpec));
    }

    /**
     * 处理MeasureSpec
     */
    private int handleMeasure(int measureSpec) {
        int result = DEFAULT_MIN_WIDTH;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            //处理wrap_content的情况
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //自动开启旋转
        if (isAutoStart) {
            start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    public void setRingColor(int color) {
        this.ringColor = color;
        mPaint.setColor(ringColor);
    }

    @Override
    public void run() {
        if (mCurrentAngle >= TOTAL_ROTATION_ANGLE) {
            mCurrentAngle = mCurrentAngle - TOTAL_ROTATION_ANGLE;
        } else {
            //每次叠加10步长
            mCurrentAngle += 10;
        }
        //通知重绘
        invalidate();
        postDelayed(this, INTERVAL_TIME);
    }

    private void start() {
        postDelayed(this, INTERVAL_TIME);
    }

    private void stop() {
        removeCallbacks(this);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
