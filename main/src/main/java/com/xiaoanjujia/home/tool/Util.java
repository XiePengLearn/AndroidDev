package com.xiaoanjujia.home.tool;

import android.text.TextUtils;

import com.xiaoanjujia.common.BaseApplication;
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


    public static boolean isLogin() {
        String token = PrefUtils.readSESSION_ID(BaseApplication.getInstance());
        if (!isNull(token)) {
            return true;
        }
        return false;
    }
}
