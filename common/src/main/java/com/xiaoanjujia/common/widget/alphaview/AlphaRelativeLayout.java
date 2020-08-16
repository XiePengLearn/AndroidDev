package com.xiaoanjujia.common.widget.alphaview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created on 2018/5/29.
 * Author: M_DaLing
 * Email: Null_1024@126.com
 * Development tools: Android studio
 */
public class AlphaRelativeLayout extends RelativeLayout {
    public AlphaRelativeLayout(Context context) {
        super(context);
    }

    public AlphaRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
