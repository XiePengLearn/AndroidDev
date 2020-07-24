package com.xiaoanjujia.common.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @Auther: xp
 * @Date: 2019/9/13 21:53
 * @Description:
 */
public class ToastUtil {


    private static Toast mToast;

    /**
     * Toast快速切换
     */
    public static void showToast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.cancel();
            mToast = Toast.makeText(context, msg, duration);
            mToast.setText(msg);

        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }
    /**
     * Toast快速切换
     */
    public static void showToast(Context mContext,String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.cancel();
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
            mToast.setText(msg);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }



    /**
     * 立即取消Toast
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
