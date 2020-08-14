package com.xiaoanjujia.common.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sxjs.common.R;
import com.xiaoanjujia.common.BaseApplication;


/**
 * Created on 2020-05-04.<br/>
 * Author: M_DaLing<br/>
 * Email: Null_1024@126.com<br/>
 * Development tools: Android studio<br/>
 * Introduce:
 */
public class ConfirmDialog extends Dialog {

    private Context mContext;

    private View mView;


    TextView titleTv;
    TextView contentTv;

    private boolean needClose = false;

    public ConfirmDialog(@NonNull Context context) {
        super(context);

        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_deposit, null, false);
        setContentView(mView);

        setCancelable(false);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getWindow().setGravity(Gravity.CENTER);
            WindowManager m = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
            Display d = m.getDefaultDisplay();
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.width = d.getWidth();
            getWindow().setAttributes(p);
        }
        titleTv = mView.findViewById(R.id.dialog_confirm_title_tv);
        contentTv = mView.findViewById(R.id.dialog_confirm_content_tv);
        mView.findViewById(R.id.dialog_confirm_ok_atv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                if (needClose) {
                    ((Activity) mContext).finish();
                }
            }
        });

    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitleStr(String title) {
        contentTv.setText(title);
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setContentStr(String content) {
        contentTv.setText(content);
    }

    public void okToClose(boolean needClose) {
        this.needClose = needClose;
    }

    /**
     * 更换底部显示dialogView
     */
    public void setButtonColor(int res) {
        if (mView != null) {
            mView.findViewById(R.id.dialog_confirm_ok_atv).setBackgroundResource(res);
        }
    }
}
