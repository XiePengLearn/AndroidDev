package com.xiaoanjujia.common.util;

/**
 * @Auther: xp
 * @Date: 2020/4/15 16:56
 * @Description: 防止按钮2连续多次点击
 */
public class NoDoubleClickUtils {
    private final static int SPACE_TIME = 3000;//2次点击的间隔时间，单位ms
    private static long lastClickTime;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClick = false;
        } else {
            isClick = true;
        }
        lastClickTime = currentTime;
        return isClick;
    }
}
