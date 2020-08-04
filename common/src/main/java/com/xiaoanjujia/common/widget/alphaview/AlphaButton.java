package com.xiaoanjujia.common.widget.alphaview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

public class AlphaButton extends AppCompatButton {
    public AlphaButton(Context context) {
        super(context);
    }

    public AlphaButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("ClickableViewAccessibility")
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
