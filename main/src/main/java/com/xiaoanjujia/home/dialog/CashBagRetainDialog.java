package com.xiaoanjujia.home.dialog;

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

import com.sxjs.jd.R;
import com.xiaoanjujia.common.BaseApplication;


public class CashBagRetainDialog extends Dialog {


    public CashBagRetainDialog(@NonNull Context context, View.OnClickListener listener) {
        super(context);
        //        mContext = context;
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_cash_bag_retain, null, false);
        setContentView(mView);

        TextView cashBagRetainBonusKeNg = mView.findViewById(R.id.cash_bag_retain_bonus_ke_ng);
        TextView getCashBagRetainBonusKeNg = mView.findViewById(R.id.get_cash_bag_retain_bonus_ke_ng);
        getCashBagRetainBonusKeNg.setOnClickListener(listener);
        mView.findViewById(R.id.dialog_close_iv).setOnClickListener(listener);
        getCashBagRetainBonusKeNg.setOnClickListener(listener);
        setCancelable(true);
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
    }
}
