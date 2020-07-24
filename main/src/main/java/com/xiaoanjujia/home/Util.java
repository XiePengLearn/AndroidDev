package com.xiaoanjujia.home;

import android.content.Context;
import android.text.TextUtils;

import com.xiaoanjujia.common.util.PrefUtils;

/**
 * @Auther: xp
 * @Date: 2020/7/23 15:28
 * @Description:
 */
public class Util {
    public static boolean isNull(String text) {
        if (text != null && !TextUtils.isEmpty(text)) {
            return false;
        }
        return true;
    }


    public static boolean isLogin(Context mContext) {
        String token = PrefUtils.readSESSION_ID(mContext);
        if (!isNull(token)) {
            return true;
        }
        return false;
    }
}
