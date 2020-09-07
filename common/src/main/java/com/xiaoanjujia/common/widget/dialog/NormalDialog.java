package com.xiaoanjujia.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sxjs.common.R;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.widget.alphaview.AlphaTextView;


/**
 * Created on 2020-02-29.<br/>
 * Author: M_DaLing<br/>
 * Email: Null_1024@126.com<br/>
 * Development tools: Android studio<br/>
 * Introduce: 使用较多的通用Dialog
 */
public class NormalDialog extends Dialog {
    /**
     * 顶部内容Tv
     */
    public TextView topContentTv;
    /**
     * 第一个按钮Tv 自带75%的透明度
     */
    public AlphaTextView firstAlTv;
    /**
     * 第二个按钮Tv 自带75%的透明度
     */
    public AlphaTextView secondAlTv;
    /**
     * 用于更换dialog底部控件
     */
    private LinearLayout bottomLl;
    /**
     * 底部控件文案
     */
    public TextView bottomTv;

    /**
     * Title
     */
    public TextView dialogTitle;

    private Context mContext;

    private View mView;

    private View.OnClickListener onClickListener;

    public View splitView;

    public NormalDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context, R.style.dialog);
        mContext = context;
        this.onClickListener = onClickListener;
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_normal, null, false);
        setContentView(mView);
        topContentTv = mView.findViewById(R.id.dialog_normal_content_tv);
        firstAlTv = mView.findViewById(R.id.dialog_normal_first_btn_tv);
        secondAlTv = mView.findViewById(R.id.dialog_normal_second_btn_tv);
        bottomLl = mView.findViewById(R.id.dialog_normal_bottom_ll);
        bottomTv = mView.findViewById(R.id.dialog_normal_bottom_tv);
        dialogTitle = mView.findViewById(R.id.dialog_normal_title_tv);
        splitView = mView.findViewById(R.id.dialog_normal_button_split_line);

        firstAlTv.setOnClickListener(onClickListener);
        secondAlTv.setOnClickListener(onClickListener);
        bottomTv.setOnClickListener(onClickListener);
        topContentTv.setOnClickListener(onClickListener);
        setCancelable(false);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager m = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
    }

    public void setDialogTitle(String title) {
        dialogTitle.setVisibility(View.VISIBLE);
        dialogTitle.setText(title);
    }

    /**
     * 设置dialog内容
     *
     * @param content
     */
    public void setDialogContent(String content) {
        if (topContentTv != null) {
            topContentTv.setText(content);
        }
    }

    /**
     * 显示html格式的文案
     * @param html
     */
    public void setDialogContentForHtml(String html) {
        if (topContentTv != null) {
            topContentTv.setText(Html.fromHtml(html));
        }
    }

    /**
     * 设置第一个按钮的文案
     *
     * @param firstAlTvStr
     */
    public void setFirstAlTvStr(String firstAlTvStr) {
        if (firstAlTv != null) {
            firstAlTv.setText(firstAlTvStr);
        }
    }

    /**
     * 设置第一个按钮的文案
     *
     * @param secondAlTvStr
     */
    public void setSecondAlTvStr(String secondAlTvStr) {
        if (secondAlTv != null) {
            secondAlTv.setText(secondAlTvStr);
        }
    }

    /**
     * 更换底部显示dialogView
     */
    public void setBottomView(View bottomView) {
        if (bottomLl != null) {
            if (bottomView != null) {
                bottomLl.removeAllViews();
                bottomLl.addView(bottomView);
                bottomLl.setVisibility(View.VISIBLE);
            } else {
                bottomLl.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 隐藏一个按钮
     */
    public void hideFirstBtn() {
        firstAlTv.setVisibility(View.GONE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) secondAlTv.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.weight = 0;
        secondAlTv.setLayoutParams(layoutParams);
    }

    /**
     * 隐藏一个按钮
     */
    public void hideFirstBtnSetPwd() {
        firstAlTv.setVisibility(View.GONE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) secondAlTv.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        secondAlTv.setLayoutParams(layoutParams);
    }


    /**
     * 隐藏第二个按钮
     */
    public void hideSecondBtn() {
        secondAlTv.setVisibility(View.GONE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) firstAlTv.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.weight = 0;
        layoutParams.setMargins(0, 0, 0, 0);
        firstAlTv.setLayoutParams(layoutParams);
    }

}
