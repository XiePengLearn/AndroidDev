package com.xiaoanjujia.common.widget.alphaview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created on 2020-05-08.<br/>
 * Author: M_DaLing<br/>
 * Email: Null_1024@126.com<br/>
 * Development tools: Android studio<br/>
 * Introduce:
 */
public class AlphaLinearLayout extends LinearLayout {


    public AlphaLinearLayout(Context context) {
        super(context);
    }

    public AlphaLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.setAlpha(0.75f);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            this.setAlpha(1f);
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            this.setAlpha(1f);
        }
        return super.onTouchEvent(event);
    }
}
